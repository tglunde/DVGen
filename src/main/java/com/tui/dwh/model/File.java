package com.tui.dwh.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the "FILE" database table.
 * 
 */
@Entity
@Table(name="\"FILE\"")
@NamedQuery(name="File.findAll", query="SELECT f FROM File f")
public class File implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String filename;

	private String parser;

	private String url;

	//bi-directional many-to-one association to Column
	@OneToMany(mappedBy="file")
	private List<Column> columns;

	//bi-directional many-to-one association to Sourcesystem
	@ManyToOne
	@JoinColumn(name="RSRC")
	private Sourcesystem sourcesystem;

	public File() {
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getParser() {
		return this.parser;
	}

	public void setParser(String parser) {
		this.parser = parser;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Column> getColumns() {
		return this.columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public Column addColumn(Column column) {
		getColumns().add(column);
		column.setFile(this);

		return column;
	}

	public Column removeColumn(Column column) {
		getColumns().remove(column);
		column.setFile(null);

		return column;
	}

	public Sourcesystem getSourcesystem() {
		return this.sourcesystem;
	}

	public void setSourcesystem(Sourcesystem sourcesystem) {
		this.sourcesystem = sourcesystem;
	}

}