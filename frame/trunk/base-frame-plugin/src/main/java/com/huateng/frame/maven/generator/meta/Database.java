package com.huateng.frame.maven.generator.meta;

import java.util.ArrayList;
import java.util.List;

public class Database {
	
	private List<Table> tables = new ArrayList<Table>();
	private List<Relationship> relationships = new ArrayList<Relationship>();
	
	public List<Table> getTables() {
		return tables;
	}
	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
	public List<Relationship> getRelationships() {
		return relationships;
	}
	public void setRelationships(List<Relationship> relationships) {
		this.relationships = relationships;
	}
}
