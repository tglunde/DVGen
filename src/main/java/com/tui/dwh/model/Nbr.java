package com.tui.dwh.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the NBR database table.
 * 
 */
@Entity
@NamedQuery(name="Nbr.findAll", query="SELECT n FROM Nbr n")
public class Nbr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	private int id;

	@Id
	private String nbr;

	private int nbrdescription;

	//bi-directional many-to-one association to Subjectarea
	@ManyToOne
	@JoinColumn(name="SUBJECTAREANAME")
	private Subjectarea subjectarea;

	//uni-directional many-to-many association to Cbc
	@ManyToMany
	@JoinTable(
		name="LINK"
		, joinColumns={
			@JoinColumn(name="NBR")
			}
		, inverseJoinColumns={
			@JoinColumn(name="CBC")
			}
		)
	private List<Cbc> cbcs = new ArrayList<>();

	//uni-directional many-to-one association to Cbc
	@ManyToOne
	@JoinColumn(name="KEYEDINSTANCE")
	private Cbc cbc;

	public Nbr() {
	}

	public Nbr(Integer id, String name, Cbc keyedInstance) {
		this.id = id;
		this.nbr = name;
		this.cbc = keyedInstance;
	}

	public String getNbr() {
		return this.nbr;
	}

	public void setNbr(String nbr) {
		this.nbr = nbr;
	}

	public int getNbrdescription() {
		return this.nbrdescription;
	}

	public void setNbrdescription(int nbrdescription) {
		this.nbrdescription = nbrdescription;
	}

	public List<Cbc> getCbcs() {
		return this.cbcs;
	}

	public void setCbcs(List<Cbc> cbcs) {
		this.cbcs = cbcs;
	}

	public Cbc getCbc() {
		return this.cbc;
	}

	public void setCbc(Cbc cbc) {
		this.cbc = cbc;
	}

	public Subjectarea getSubjectarea() {
		return subjectarea;
	}

	public void setSubjectarea(Subjectarea subjectarea) {
		this.subjectarea = subjectarea;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Nbr{" +
				"id=" + id +
				", nbr='" + nbr + '\'' +
				", nbrdescription=" + nbrdescription +
				", subjectarea=" + subjectarea +
				'}';
	}
}