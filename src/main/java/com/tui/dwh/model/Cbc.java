package com.tui.dwh.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the CBC database table.
 * 
 */
@Entity
@NamedQuery(name="Cbc.findAll", query="SELECT c FROM Cbc c")
public class Cbc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	private int id;

	@Transient
	private Context defaultContext = new Context("Main");

	@Id
	private String cbc;

	private String cbcdescription;

	//uni-directional many-to-one association to Context
	@ManyToOne
	@JoinColumn(name="BUSINESSKEY")
	private Context businessKey = new Context("BOK");

	//bi-directional many-to-one association to Subjectarea
	@ManyToOne
	@JoinColumn(name="SUBJECTAREANAME")
	private Subjectarea subjectarea;

	//bi-directional many-to-one association to Context
	@OneToMany(mappedBy="cbcBean")
	private List<Context> contexts = new ArrayList<>();

	public Cbc() {
		contexts.add(defaultContext);
	}

	public Cbc(Integer id, String name) {
		this.id = id;
		this.cbc = name;
		contexts.add(defaultContext);
	}

	public String getCbc() {
		return this.cbc;
	}

	public void setCbc(String cbc) {
		this.cbc = cbc;
	}

	public String getCbcdescription() {
		return this.cbcdescription;
	}

	public void setCbcdescription(String cbcdescription) {
		this.cbcdescription = cbcdescription;
	}

	public Context getBusinessKey() {
		return this.businessKey;
	}

	public void setBusinessKey(Context businessKey) {
		this.businessKey = businessKey;
	}

	public Subjectarea getSubjectarea() {
		return this.subjectarea;
	}

	public void setSubjectarea(Subjectarea subjectarea) {
		this.subjectarea = subjectarea;
	}

	public List<Context> getContexts() {
		return this.contexts;
	}

	public void setContexts(List<Context> contexts) {

		this.contexts = contexts;
	}

	public Context addContext(Context context) {
		getContexts().add(context);
		context.setCbcBean(this);

		return context;
	}

	public Context removeContext(Context context) {
		getContexts().remove(context);
		context.setCbcBean(null);

		return context;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Context getDefaultContext() {
		return defaultContext;
	}

	public void setDefaultContext(Context defaultContext) {
		this.defaultContext = defaultContext;
	}

	@Override
	public String toString() {
		return "Cbc{" +
				"id=" + id +
				", cbc='" + cbc + '\'' +
				", cbcdescription=" + cbcdescription +
				", businessKey=" + businessKey +
				", subjectarea=" + subjectarea +
				", contexts=" + contexts +
				'}';
	}
}