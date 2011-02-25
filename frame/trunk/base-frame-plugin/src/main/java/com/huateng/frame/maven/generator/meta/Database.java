package com.huateng.frame.maven.generator.meta;

import java.util.ArrayList;
import java.util.List;

public class Database {
	
	private List<Table> tables = new ArrayList<Table>();
	private List<Relationship> relationships = new ArrayList<Relationship>();
	private List<Domain> domains = new ArrayList<Domain>();
	
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
	public List<Domain> getDomains()
	{
		return domains;
	}
	public void setDomains(List<Domain> domains)
	{
		this.domains = domains;
	}
}
