package com.huateng.frame.maven.generator.meta;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;

public class Table {
	
	private String dbName;
	private String textName;
	private List<Column> columns = new ArrayList<Column>();
	private List<Column> primaryKeyColumns = new ArrayList<Column>();
	
	private FullyQualifiedJavaType javaClass;
	private FullyQualifiedJavaType javaKeyClass;
	
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
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
	public List<Column> getPrimaryKeyColumns()
	{
		return primaryKeyColumns;
	}
	public void setPrimaryKeyColumns(List<Column> primaryKeyColumns)
	{
		this.primaryKeyColumns = primaryKeyColumns;
	}
	public FullyQualifiedJavaType getJavaClass()
	{
		return javaClass;
	}
	public void setJavaClass(FullyQualifiedJavaType javaClass)
	{
		this.javaClass = javaClass;
	}
	public FullyQualifiedJavaType getJavaKeyClass()
	{
		return javaKeyClass;
	}
	public void setJavaKeyClass(FullyQualifiedJavaType javaKeyClass)
	{
		this.javaKeyClass = javaKeyClass;
	}
}
