package com.tui.dwh;

import com.tui.dwh.erdplus.Connector;
import com.tui.dwh.erdplus.Model;
import com.tui.dwh.erdplus.Shape;
import com.tui.dwh.erdplus.Slot;
import com.tui.dwh.model.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ELMModelFactory {

    private ModelParser modelParser = new ModelParser();

    public Subjectarea createModel(Path path) throws IOException {
        Model model = modelParser.parse(path);
        Map<Integer, Shape> shapes = new HashMap<Integer,Shape>();
        Map<Integer, Shape> entityMap = new HashMap<Integer,Shape>();
        Map<Integer, Shape> attributeMap = new HashMap<Integer, Shape>();
        Map<Integer, Shape> relationMap = new HashMap<Integer, Shape>();
        for(Shape shape : model.getShapes()) {
            if(shape.getType().equals("Entity") && !"associative".equals(shape.getDetail().getType())) {
                entityMap.put(shape.getDetail().getId(), shape);
            } else if (shape.getType().equals("Entity") && "weak".equals(shape.getDetail().getType())) {
                throw new RuntimeException("Associative entity not yet implemented.");
            } else if (shape.getType().equals("Attribute")) {
                attributeMap.put(shape.getDetail().getId(), shape);
            } else if (shape.getType().equals("Relationship")) {
                relationMap.put(shape.getDetail().getId(), shape);
            }
            shapes.put(shape.getDetail().getId(), shape);
        }
        Map<Integer, Connector> connectorMap = new HashMap<Integer, Connector>();
        for(Connector connector : model.getConnectors()) {
            connectorMap.put(connector.getDetail().getId(), connector);
        }

        Subjectarea sa = new Subjectarea();
        sa.setSubjectareaname("CAM");

        Map<Integer,Cbc> cbcs = new HashMap();
        for(Shape entity : entityMap.values()) {
            Cbc cbc = createCBC(entity);
            cbcs.put(cbc.getId(), cbc);
            sa.addCbc(cbc);
        }
        for(Shape relation : relationMap.values()) {
            Nbr nbr = this.createNBR(relation, cbcs);
            sa.addNbr(nbr);
            sa.addCbc(nbr.getCbc());
        }
        Map<Integer, Attribute> attributes = new HashMap();
        for(Shape attribute : attributeMap.values()) {
            attributes.put(attribute.getDetail().getId(), createAttribute(attribute));
        }
        Map<Attribute, List<Attribute>> attr2Attribute = new HashMap();
        Map<Attribute, Cbc> attr2CBC = new HashMap();
        for(Connector connector : connectorMap.values()) {
            if("Connector".equals(connector.getType())) {
                Cbc cbcSrc = cbcs.get(connector.getSource());
                Cbc cbcDest = cbcs.get(connector.getDestination());
                Attribute attrSrc = attributes.get(connector.getSource());
                Attribute attrDest = attributes.get(connector.getDestination());
                if(cbcSrc != null && attrDest != null) {
                    attr2CBC.put(attrDest, cbcSrc);
                } else if (cbcDest != null && attrSrc != null) {
                    attr2CBC.put(attrSrc, cbcDest);
                } else if (attrSrc != null && attrDest != null) {
                    Shape dest = shapes.get(connector.getDestination());
                    Shape src  = shapes.get(connector.getSource());
                    if(dest.getDetail().isComposite()) {
                        List list = attr2Attribute.get(attrDest);
                        if(list == null) {
                            list = new ArrayList();
                        }
                        list.add(attrSrc);
                        attr2Attribute.put(attrDest,list);
                    } else if (src.getDetail().isComposite()) {
                        List list = attr2Attribute.get(attrSrc);
                        if(list == null) {
                            list = new ArrayList();
                        }
                        list.add(attrDest);
                        attr2Attribute.put(attrSrc, list);
                    }
                }
            }
        }
        //composites
        for(Attribute attribute : attr2Attribute.keySet()) {
            List<Attribute> attrList = attr2Attribute.get(attribute);
            Cbc cbc = attr2CBC.get(attribute);
            if(cbc != null) {
                if (attribute.isUnique()) {
                    for (Attribute bk : attrList) {
                        cbc.getBusinessKey().addAttribute(bk);
                    }
                } else {
                    Context context = new Context();
                    context.setContextname(attribute.getAttributename());
                    context.setContextdesctiption(attribute.getAttributedescription());
                    cbc.addContext(context);
                    context.getAttributes().addAll(attrList);
                }
            }
        }
        //non-composites
        for(Attribute attribute : attr2CBC.keySet()) {
            Cbc cbc = attr2CBC.get(attribute);
            Shape shape = attributeMap.get(attribute.getId());
            if(!shape.getDetail().isComposite()) {
                if(!attribute.isUnique()) {
                    cbc.getDefaultContext().addAttribute(attribute);
                } else {
                    cbc.getBusinessKey().getAttributes().add(attribute);
                }
            }
        }
        //setting nbr KI attributes
        for(Nbr nbr : sa.getNbrs()) {
            Cbc keyedInstance = nbr.getCbc();
            for(Cbc cbc :   nbr.getCbcs()) {
                for(Attribute bkAttribute : cbc.getBusinessKey().getAttributes()) {
                    Attribute kiAttribute = new Attribute();
                    kiAttribute.setAttributename(bkAttribute.getAttributename());
                    kiAttribute.setAttributedescription(bkAttribute.getAttributedescription());
                    kiAttribute.setUnique(bkAttribute.isUnique());
                    kiAttribute.setAttributeprefix(sa.getSubjectareaname().toUpperCase() + "_" + cbc.getCbc().toUpperCase());
                    keyedInstance.getBusinessKey().addAttribute(kiAttribute);
                }
            }
            //searching for attributes of relation
            for(Connector connector : connectorMap.values()) {
                Attribute attribute = null;
                if(nbr.getId()==connector.getDestination()) {
                    attribute = attributes.get(connector.getSource());
                } else if (nbr.getId()==connector.getSource()) {
                    attribute = attributes.get(connector.getDestination());
                }
                if(attribute!=null) {
                    if(attributeMap.get(attribute.getId()).getDetail().isComposite()) {
                        Context context = new Context(attribute.getAttributename());
                        context.getAttributes().addAll(attr2Attribute.get(attribute));
                    } else {
                        keyedInstance.getDefaultContext().addAttribute(attribute);
                    }
                }
            }
        }
        return sa;
    }

    private Attribute createAttribute(Shape attribute) {
        if(!"Attribute".equals(attribute.getType())) throw new RuntimeException("attribute needs to be created from Attribute");
        Attribute attr = new Attribute(attribute.getDetail().getId(), attribute.getDetail().getName());
        if(attribute.getDetail().isUnique()) {
            attr.setUnique(true);
        }
        return attr;
    }

    private Cbc createCBC(Shape entity) {
        if(!"Entity".equals(entity.getType())) throw new RuntimeException("cbc needs to be created from entity");
        Cbc cbc = new Cbc();
        cbc.setCbc(entity.getDetail().getName());
        cbc.setId(entity.getDetail().getId());
        return cbc;
    }

    private Nbr createNBR(Shape relation, Map<Integer, Cbc> entities) {
        if(!"Relationship".equals(relation.getType())) throw new RuntimeException("NBR must be of type Relation: " + relation);
        List<Cbc> connectedCBC = new ArrayList<>();
        Cbc keyedInstance = new Cbc(relation.getDetail().getId(), relation.getDetail().getName() + "#KI");
        for(Slot slot : relation.getDetail().getSlots()) {
            connectedCBC.add(entities.get(slot.getEntityId()));
        }
        Nbr nbr = new Nbr(relation.getDetail().getId(), relation.getDetail().getName(), keyedInstance);
        nbr.getCbcs().addAll(connectedCBC);
        return nbr;
    }
}
