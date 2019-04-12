package com.tui.dwh.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ATTRIBUTEMAPPING database table.
 * 
 */
@Entity
@NamedQuery(name="Attributemapping.findAll", query="SELECT a FROM Attributemapping a")
public class Attributemapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int mappingid;

	private String hardrule;

	//bi-directional many-to-one association to Attribute
	@ManyToOne
	@JoinColumn(name="ATTRIBUTENAME")
	private Attribute attribute;

	//uni-directional many-to-one association to Datatype
	@ManyToOne
	@JoinColumn(name="TYPENAME")
	private Datatype datatype;

	//bi-directional many-to-one association to Source
	@OneToMany(mappedBy="attributemapping")
	private List<Source> sources;

	public Attributemapping() {
	}

	public int getMappingid() {
		return this.mappingid;
	}

	public void setMappingid(int mappingid) {
		this.mappingid = mappingid;
	}

	public String getHardrule() {
		return this.hardrule;
	}

	public void setHardrule(String hardrule) {
		this.hardrule = hardrule;
	}

	public Attribute getAttribute() {
		return this.attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public Datatype getDatatype() {
		return this.datatype;
	}

	public void setDatatype(Datatype datatype) {
		this.datatype = datatype;
	}

	public List<Source> getSources() {
		return this.sources;
	}

	public void setSources(List<Source> sources) {
		this.sources = sources;
	}

	public Source addSource(Source source) {
		getSources().add(source);
		source.setAttributemapping(this);

		return source;
	}

	public Source removeSource(Source source) {
		getSources().remove(source);
		source.setAttributemapping(null);

		return source;
	}

}