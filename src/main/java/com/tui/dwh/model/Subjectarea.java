package com.tui.dwh.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the SUBJECTAREA database table.
 * 
 */
@Entity
@NamedQuery(name="Subjectarea.findAll", query="SELECT s FROM Subjectarea s")
public class Subjectarea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String subjectareaname;

	private String subjectareadescription;

	//bi-directional many-to-one association to Cbc
	@OneToMany(mappedBy="subjectarea")
	private List<Cbc> cbcs = new ArrayList<>();

	//bi-directional many-to-one association to Nbr
	@OneToMany(mappedBy="subjectarea")
	private List<Nbr> nbrs = new ArrayList<>();

    public Subjectarea() {
	}

	public String getSubjectareaname() {
		return this.subjectareaname;
	}

	public void setSubjectareaname(String subjectareaname) {
		this.subjectareaname = subjectareaname;
	}

	public String getSubjectareadescription() {
		return this.subjectareadescription;
	}

	public void setSubjectareadescription(String subjectareadescription) {
		this.subjectareadescription = subjectareadescription;
	}

	public List<Cbc> getCbcs() {
		return this.cbcs;
	}

	public void setCbcs(List<Cbc> cbcs) {
		this.cbcs = cbcs;
	}

	public Cbc addCbc(Cbc cbc) {
		getCbcs().add(cbc);
		cbc.setSubjectarea(this);

		return cbc;
	}

	public Cbc removeCbc(Cbc cbc) {
		getCbcs().remove(cbc);
		cbc.setSubjectarea(null);

		return cbc;
	}

	public List<Nbr> getNbrs() { return this.nbrs; }

	public void setNbrs(List<Nbr> nbrs) { this.nbrs = nbrs; }

	public Nbr addNbr(Nbr nbr) {
		getNbrs().add(nbr);
		nbr.setSubjectarea(this);
		return nbr;
	}

	public Nbr removeNbr(Nbr nbr) {
		getNbrs().remove(nbr);
		nbr.setSubjectarea(null);
		return nbr;
	}

	@Override
	public String toString() {
		return "Subjectarea{" +
				"subjectareaname='" + subjectareaname + '\'' +
				", subjectareadescription='" + subjectareadescription + '\'' +
				'}';
	}
}