package com.tui.dwh.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DATATYPE database table.
 * 
 */
@Entity
@NamedQuery(name="Datatype.findAll", query="SELECT d FROM Datatype d")
public class Datatype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String typename;

	private String typelength;

	private String typeprecision;

	private String typescale;

	public Datatype() {
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getTypelength() {
		return this.typelength;
	}

	public void setTypelength(String typelength) {
		this.typelength = typelength;
	}

	public String getTypeprecision() {
		return this.typeprecision;
	}

	public void setTypeprecision(String typeprecision) {
		this.typeprecision = typeprecision;
	}

	public String getTypescale() {
		return this.typescale;
	}

	public void setTypescale(String typescale) {
		this.typescale = typescale;
	}

}