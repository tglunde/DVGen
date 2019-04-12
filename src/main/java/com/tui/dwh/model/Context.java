package com.tui.dwh.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the "CONTEXT" database table.
 * 
 */
@Entity
@Table(name="\"CONTEXT\"")
@NamedQuery(name="Context.findAll", query="SELECT c FROM Context c")
public class Context implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String contextname;

	private String contextdesctiption;

	//bi-directional many-to-one association to Attribute
	@OneToMany(mappedBy="context")
	private List<Attribute> attributes = new ArrayList<>();

	//bi-directional many-to-one association to Cbc
	@ManyToOne
	@JoinColumn(name="CBC")
	private Cbc cbcBean;

	public Context() {
	}

	public Context(String name) {
		this.contextname = name;
	}

	public String getContextname() {
		return this.contextname;
	}

	public void setContextname(String contextname) {
		this.contextname = contextname;
	}

	public String getContextdesctiption() {
		return this.contextdesctiption;
	}

	public void setContextdesctiption(String contextdesctiption) {
		this.contextdesctiption = contextdesctiption;
	}

	public List<Attribute> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public Attribute addAttribute(Attribute attribute) {
		getAttributes().add(attribute);
		attribute.setContext(this);

		return attribute;
	}

	public Attribute removeAttribute(Attribute attribute) {
		getAttributes().remove(attribute);
		attribute.setContext(null);

		return attribute;
	}

	public Cbc getCbcBean() {
		return this.cbcBean;
	}

	public void setCbcBean(Cbc cbcBean) {
		this.cbcBean = cbcBean;
	}

	@Override
	public String toString() {
		return "Context{" +
				"contextname='" + contextname + '\'' +
				", contextdesctiption='" + contextdesctiption + '\'' +
				", attributes=" + attributes +
				'}';
	}
}