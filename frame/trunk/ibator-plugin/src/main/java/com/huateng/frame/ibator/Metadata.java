package com.huateng.frame.ibator;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

import org.apache.ibatis.ibator.api.GeneratedJavaFile;
import org.apache.ibatis.ibator.api.IbatorPluginAdapter;
import org.apache.ibatis.ibator.api.IntrospectedColumn;
import org.apache.ibatis.ibator.api.IntrospectedTable;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
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
		
		fqjt = new FullyQualifiedJavaType("javax.persistence.Entity");
		topLevelClass.addImportedType(fqjt);
		topLevelClass.addAnnotation("@Entity");
		
		if (introspectedTable.getPrimaryKeyColumns().size() > 1)
		{
			fqjt = new FullyQualifiedJavaType("javax.persistence.IdClass");
			topLevelClass.addImportedType(fqjt);
			topLevelClass.addAnnotation(MessageFormat.format("@IdClass({0}.class)", introspectedTable.getPrimaryKeyType()));
		}
		return true;
	}
	
	@Override
	public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,
			ModelClassType modelClassType)
	{
		FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("javax.persistence.Column");
		topLevelClass.addImportedType(fqjt);
		field.addAnnotation(MessageFormat.format("@Column(name=\"{0}\", length={1})", introspectedColumn.getActualColumnName(), introspectedColumn.getLength()));
		if (introspectedTable.getPrimaryKeyColumns().contains(introspectedColumn))
		{
			fqjt = new FullyQualifiedJavaType("javax.persistence.Id");
			topLevelClass.addImportedType(fqjt);
			field.addAnnotation("@Id");
		}
		return true;
	}
	
}
