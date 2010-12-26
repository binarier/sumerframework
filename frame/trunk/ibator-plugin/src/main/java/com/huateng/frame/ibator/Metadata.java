package com.huateng.frame.ibator;

import java.text.MessageFormat;
import java.util.List;

import org.apache.ibatis.ibator.api.IbatorPluginAdapter;
import org.apache.ibatis.ibator.api.IntrospectedColumn;
import org.apache.ibatis.ibator.api.IntrospectedTable;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;

public class Metadata extends IbatorPluginAdapter
{

	public boolean validate(List<String> warnings)
	{
		return true;
	}
	
	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
	{
		FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("javax.persistence.Table");
		topLevelClass.addImportedType(fqjt);
		topLevelClass.addAnnotation(MessageFormat.format("@Table(name=\"{0}\")", introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()));
		return true;
	}
	
	@Override
	public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,
			ModelClassType modelClassType)
	{
		FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("javax.persistence.Column");
		topLevelClass.addImportedType(fqjt);
		field.addAnnotation(MessageFormat.format("@Column(name=\"{0}\", length={1})", introspectedColumn.getActualColumnName(), introspectedColumn.getLength()));
		return true;
	}
}
