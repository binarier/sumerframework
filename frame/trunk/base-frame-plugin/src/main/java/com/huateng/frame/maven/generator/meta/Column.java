package com.huateng.frame.maven.generator.meta;

import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;

public class Column {
	//由importer填写
	private String dbName;
	private String textName;
	private String description;
	private int length;
	private int scale;
	private boolean mandatory;
	private FullyQualifiedJavaType javaType;
	private boolean lob = false;
	private String dbType;
	private Domain domain;
	
	//由总控填写
	private String propertyName;
	private String id;
	private Table table;
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isLob() {
		return lob;
	}
	public void setLob(boolean lob) {
		this.lob = lob;
	}
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	public String getDbName()
	{
		return dbName;
	}
	public void setDbName(String dbName)
	{
		this.dbName = dbName;
	}
	public String getTextName()
	{
		return textName;
	}
	public void setTextName(String textName)
	{
		this.textName = textName;
	}
	public String getPropertyName()
	{
		return propertyName;
	}
	public void setPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
	}
	public boolean isMandatory()
	{
		return mandatory;
	}
	public void setMandatory(boolean mandatory)
	{
		this.mandatory = mandatory;
	}
	public FullyQualifiedJavaType getJavaType()
	{
		return javaType;
	}
	public void setJavaType(FullyQualifiedJavaType javaType)
	{
		this.javaType = javaType;
	}
	public String getDbType()
	{
		return dbType;
	}
	public void setDbType(String dbType)
	{
		this.dbType = dbType;
	}
	public Domain getDomain()
	{
		return domain;
	}
	public void setDomain(Domain domain)
	{
		this.domain = domain;
	}
}
