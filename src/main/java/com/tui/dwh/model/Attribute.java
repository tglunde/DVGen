package com.tui.dwh.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the "ATTRIBUTE" database table.
 * 
 */
@Entity
@Table(name="\"ATTRIBUTE\"")
@NamedQuery(name="Attribute.findAll", query="SELECT a FROM Attribute a")
public class Attribute implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	private int id;

	@Transient
	private boolean isUnique = false;

	@Transient
	private org.apache.ddlutils.model.Column column = new org.apache.ddlutils.model.Column();

	@Id
	private String attributename;

	private String attributedescription;

	private String attributeprefix;

	//bi-directional many-to-one association to Context
	@ManyToOne
	@JoinColumn(name="CONTEXTNAME")
	private Context context;

	//bi-directional many-to-one association to Attributemapping
	@OneToMany(mappedBy="attribute")
	private List<Attributemapping> attributemappings;

	public Attribute() {
		column.setType("VARCHAR");
		column.setSize("50");

	}

	public Attribute(Integer id, String name) {
		this.id = id;
		this.attributename = name;
		column.setType("VARCHAR");
		column.setSize("50");
	}

	public org.apache.ddlutils.model.Column getColumn() {
		return column;
	}

	public void setColumn(org.apache.ddlutils.model.Column column) {
		this.column = column;
	}

	public String getAttributename() {
		return this.attributename;
	}

	public void setAttributename(String attributename) {
		this.attributename = attributename;
	}

	public String getAttributedescription() {
		return this.attributedescription;
	}

	public void setAttributedescription(String attributedescription) {
		this.attributedescription = attributedescription;
	}

	public String getAttributeprefix() {
		return attributeprefix;
	}

	public void setAttributeprefix(String attributeprefix) {
		this.attributeprefix = attributeprefix;
	}

	public Context getContext() {
		return this.context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public List<Attributemapping> getAttributemappings() {
		return this.attributemappings;
	}

	public void setAttributemappings(List<Attributemapping> attributemappings) {
		this.attributemappings = attributemappings;
	}

	public Attributemapping addAttributemapping(Attributemapping attributemapping) {
		getAttributemappings().add(attributemapping);
		attributemapping.setAttribute(this);

		return attributemapping;
	}

	public Attributemapping removeAttributemapping(Attributemapping attributemapping) {
		getAttributemappings().remove(attributemapping);
		attributemapping.setAttribute(null);

		return attributemapping;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isUnique() {
		return isUnique;
	}

	public void setUnique(boolean unique) {
		isUnique = unique;
	}

	@Override
	public String toString() {
		return "Attribute{" +
				"id=" + id +
				", isUnique=" + isUnique +
				", column=" + column +
				", attributename='" + attributename + '\'' +
				", attributedescription='" + attributedescription + '\'' +
				", attributemappings=" + attributemappings +
				'}';
	}
}