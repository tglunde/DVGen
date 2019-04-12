package com.tui.dwh.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Column;


/**
 * The persistent class for the SOURCEDATATYPE database table.
 * 
 */
@Entity
@NamedQuery(name="Sourcedatatype.findAll", query="SELECT s FROM Sourcedatatype s")
public class Sourcedatatype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String sourcedatatypeid;

	@javax.persistence.Column(name="\"LENGTH\"")
	private String length;

	@Column(name="\"PRECISION\"")
	private String precision;

	private String scale;

	private String typename;

	public Sourcedatatype() {
	}

	public String getSourcedatatypeid() {
		return this.sourcedatatypeid;
	}

	public void setSourcedatatypeid(String sourcedatatypeid) {
		this.sourcedatatypeid = sourcedatatypeid;
	}

	public String getLength() {
		return this.length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getPrecision() {
		return this.precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getScale() {
		return this.scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

}