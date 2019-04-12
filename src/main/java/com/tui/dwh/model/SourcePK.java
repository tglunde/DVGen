package com.tui.dwh.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Column;

/**
 * The primary key class for the "SOURCE" database table.
 * 
 */
@Embeddable
public class SourcePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@javax.persistence.Column(name="\"COLUMN\"", insertable=false, updatable=false)
	private String column;

	@javax.persistence.Column(name="\"SCHEMA\"", insertable=false, updatable=false)
	private String schema;

	@javax.persistence.Column(insertable=false, updatable=false)
	private String relationname;

	@javax.persistence.Column(name="\"DATABASE\"", insertable=false, updatable=false)
	private String database;

	@Column(insertable=false, updatable=false)
	private int mappingid;

	public SourcePK() {
	}
	public String getColumn() {
		return this.column;
	}
	public void setColumn(String column) {
		this.column = column;
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
	public int getMappingid() {
		return this.mappingid;
	}
	public void setMappingid(int mappingid) {
		this.mappingid = mappingid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SourcePK)) {
			return false;
		}
		SourcePK castOther = (SourcePK)other;
		return 
			this.column.equals(castOther.column)
			&& this.schema.equals(castOther.schema)
			&& this.relationname.equals(castOther.relationname)
			&& this.database.equals(castOther.database)
			&& (this.mappingid == castOther.mappingid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.column.hashCode();
		hash = hash * prime + this.schema.hashCode();
		hash = hash * prime + this.relationname.hashCode();
		hash = hash * prime + this.database.hashCode();
		hash = hash * prime + this.mappingid;
		
		return hash;
	}
}