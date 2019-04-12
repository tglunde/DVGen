package com.tui.dwh.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the RELATION database table.
 * 
 */
@Entity
@NamedQuery(name="Relation.findAll", query="SELECT r FROM Relation r")
public class Relation implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RelationPK id;

	private String relationcomment;

	private String sqltext;

	//bi-directional many-to-one association to Column
	@OneToMany(mappedBy="relation")
	private List<Column> columns;

	//bi-directional many-to-one association to Sourcesystem
	@ManyToOne
	@JoinColumn(name="RSRC")
	private Sourcesystem sourcesystem;

	public Relation() {
	}

	public RelationPK getId() {
		return this.id;
	}

	public void setId(RelationPK id) {
		this.id = id;
	}

	public String getRelationcomment() {
		return this.relationcomment;
	}

	public void setRelationcomment(String relationcomment) {
		this.relationcomment = relationcomment;
	}

	public String getSqltext() {
		return this.sqltext;
	}

	public void setSqltext(String sqltext) {
		this.sqltext = sqltext;
	}

	public List<Column> getColumns() {
		return this.columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public Column addColumn(Column column) {
		getColumns().add(column);
		column.setRelation(this);

		return column;
	}

	public Column removeColumn(Column column) {
		getColumns().remove(column);
		column.setRelation(null);

		return column;
	}

	public Sourcesystem getSourcesystem() {
		return this.sourcesystem;
	}

	public void setSourcesystem(Sourcesystem sourcesystem) {
		this.sourcesystem = sourcesystem;
	}

}