package com.tui.dwh.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SOURCESYSTEM database table.
 * 
 */
@Entity
@NamedQuery(name="Sourcesystem.findAll", query="SELECT s FROM Sourcesystem s")
public class Sourcesystem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String rsrc;

	private String systemdescription;

	private String systemname;

	//bi-directional many-to-one association to File
	@OneToMany(mappedBy="sourcesystem")
	private List<File> files;

	//bi-directional many-to-one association to Relation
	@OneToMany(mappedBy="sourcesystem")
	private List<Relation> relations;

	public Sourcesystem() {
	}

	public String getRsrc() {
		return this.rsrc;
	}

	public void setRsrc(String rsrc) {
		this.rsrc = rsrc;
	}

	public String getSystemdescription() {
		return this.systemdescription;
	}

	public void setSystemdescription(String systemdescription) {
		this.systemdescription = systemdescription;
	}

	public String getSystemname() {
		return this.systemname;
	}

	public void setSystemname(String systemname) {
		this.systemname = systemname;
	}

	public List<File> getFiles() {
		return this.files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public File addFile(File file) {
		getFiles().add(file);
		file.setSourcesystem(this);

		return file;
	}

	public File removeFile(File file) {
		getFiles().remove(file);
		file.setSourcesystem(null);

		return file;
	}

	public List<Relation> getRelations() {
		return this.relations;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}

	public Relation addRelation(Relation relation) {
		getRelations().add(relation);
		relation.setSourcesystem(this);

		return relation;
	}

	public Relation removeRelation(Relation relation) {
		getRelations().remove(relation);
		relation.setSourcesystem(null);

		return relation;
	}

}