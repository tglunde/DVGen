package com.tui.dwh.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Column;

/**
 * The primary key class for the RELATION database table.
 * 
 */
@Embeddable
public class RelationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@javax.persistence.Column(name="\"SCHEMA\"")
	private String schema;

	private String relationname;

	@Column(name="\"DATABASE\"")
	private String database;

	public RelationPK() {
	}
	public String getSchema() {
		return this.schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getRelationname() {
		return this.relationname;
	}
	public void setRelationname(String relationname) {
		this.relationname = relationname;
	}
	public String getDatabase() {
		return this.database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelationPK)) {
			return false;
		}
		RelationPK castOther = (RelationPK)other;
		return 
			this.schema.equals(castOther.schema)
			&& this.relationname.equals(castOther.relationname)
			&& this.database.equals(castOther.database);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.schema.hashCode();
		hash = hash * prime + this.relationname.hashCode();
		hash = hash * prime + this.database.hashCode();
		
		return hash;
	}
}