package com.tui.dwh.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Column;


/**
 * The persistent class for the "SOURCE" database table.
 * 
 */
@Entity
@Table(name="\"SOURCE\"")
@NamedQuery(name="Source.findAll", query="SELECT s FROM Source s")
public class Source implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SourcePK id;

	@javax.persistence.Column(name="\"FUNCTION\"")
	private String function;

	//bi-directional many-to-one association to Attributemapping
	@ManyToOne
	@JoinColumn(name="MAPPINGID")
	private Attributemapping attributemapping;

	//bi-directional many-to-one association to Column
	@ManyToOne
	@JoinColumn(name="RELATIONNAME", referencedColumnName="RELATIONNAME")
	private javax.persistence.Column columnBean;

	public Source() {
	}

	public SourcePK getId() {
		return this.id;
	}

	public void setId(SourcePK id) {
		this.id = id;
	}

	public String getFunction() {
		return this.function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public Attributemapping getAttributemapping() {
		return this.attributemapping;
	}

	public void setAttributemapping(Attributemapping attributemapping) {
		this.attributemapping = attributemapping;
	}

	public javax.persistence.Column getColumnBean() {
		return this.columnBean;
	}

	public void setColumnBean(Column columnBean) {
		this.columnBean = columnBean;
	}

}