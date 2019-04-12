package com.tui.dwh.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "COLUMN" database table.
 * 
 */
@Entity
@Table(name="\"COLUMN\"")
@NamedQuery(name="Column.findAll", query="SELECT c FROM Column c")
public class Column implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ColumnPK id;

	private String columncomment;

	//bi-directional many-to-one association to File
	@ManyToOne
	@JoinColumn(name="FILENAME")
	private File file;

	//bi-directional many-to-one association to Relation
	@ManyToOne
	@JoinColumn(name="RELATIONNAME", referencedColumnName="RELATIONNAME")
	private Relation relation;

	//uni-directional many-to-one association to Sourcedatatype
	@ManyToOne
	@JoinColumn(name="SOURCEDATATYPEID")
	private Sourcedatatype sourcedatatype;

	public Column() {
	}

	public ColumnPK getId() {
		return this.id;
	}

	public void setId(ColumnPK id) {
		this.id = id;
	}

	public String getColumncomment() {
		return this.columncomment;
	}

	public void setColumncomment(String columncomment) {
		this.columncomment = columncomment;
	}

	public File getFile() {
		return this.file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Relation getRelation() {
		return this.relation;
	}

	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	public Sourcedatatype getSourcedatatype() {
		return this.sourcedatatype;
	}

	public void setSourcedatatype(Sourcedatatype sourcedatatype) {
		this.sourcedatatype = sourcedatatype;
	}

	@Override
	public String toString() {
		return "Column{" +
				"id=" + id +
				", columncomment='" + columncomment + '\'' +
				", sourcedatatype=" + sourcedatatype +
				'}';
	}
}