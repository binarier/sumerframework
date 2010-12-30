package com.huateng.frame.maven.generator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.WordUtils;
import org.apache.ibatis.ibator.api.dom.java.CompilationUnit;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.InnerClass;
import org.apache.ibatis.ibator.api.dom.java.Interface;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;

import com.huateng.frame.maven.generator.meta.Column;
import com.huateng.frame.maven.generator.meta.Database;
import com.huateng.frame.maven.generator.meta.Table;

public class UIDataSource implements Generator
{
	private String targetPackage;
	private Map<String, String> constants = new TreeMap<String, String>();
	
	public UIDataSource(String targetPackage)
	{
		this.targetPackage = targetPackage;
	}
	private FullyQualifiedJavaType getConstantsInterface(String targetPackage)
	{
		return new FullyQualifiedJavaType(targetPackage + ".i18n.FieldTitleConstants");
	}

	private FullyQualifiedJavaType getConstantsUtil(String targetPackage)
	{
		return new FullyQualifiedJavaType(targetPackage + ".i18n.FieldTitleUtil");
	}

	public List<CompilationUnit> generateAdditionalClasses(Table table, Database database)
	{

		FullyQualifiedJavaType fqjtClazz = new FullyQualifiedJavaType(targetPackage + ".UI" + table.getJavaClass().getShortName());
		TopLevelClass clazz = new TopLevelClass(fqjtClazz);
		FullyQualifiedJavaType fqjt;

		fqjt = new FullyQualifiedJavaType("com.huateng.frame.gwt.client.ui.UIRestDataSource");
		clazz.addImportedType(fqjt);
		clazz.setSuperClass(fqjt);
		clazz.setVisibility(JavaVisibility.PUBLIC);

		Method m = new Method();
		m.setName(fqjtClazz.getShortName());
		m.setConstructor(true);
		m.setVisibility(JavaVisibility.PUBLIC);
		m.addBodyLine("setDataSrcID(\"" + WordUtils.uncapitalize(table.getJavaClass().getShortName()) + "DS\");");

		clazz.addMethod(m);

		clazz.addImportedType(getConstantsUtil(targetPackage));

		// 字段子类
		for (Column col :table.getColumns())
		{
			FullyQualifiedJavaType fqjtInner = new FullyQualifiedJavaType(WordUtils.capitalize(col.getPropertyName()));
			InnerClass inner = new InnerClass(fqjtInner);
			inner.setStatic(true);
			inner.setVisibility(JavaVisibility.PUBLIC);

			Method mi = new Method();
			mi.setVisibility(JavaVisibility.PUBLIC);
			mi.setName(fqjtInner.getShortName());
			mi.setConstructor(true);
			mi.addBodyLine("setName(\"" + col.getPropertyName() + "\");");
			
			//主键
			if (table.getPrimaryKeyColumns().contains(col))
				mi.addBodyLine("setPrimaryKey(true);");

			// 国际化
			String val = MessageFormat.format("{0}_{1}", table.getJavaClass().getShortName(), fqjtInner.getShortName());
			mi.addBodyLine(MessageFormat.format("setTitle(FieldTitleUtil.constants.{0}());", val));
			constants.put(val, col.getTextName());

			inner.addMethod(mi);

			String type = col.getJavaType().getShortName();

			if (type.equals("Integer") || type.equals("Long") || type.equals("Short"))
			{
				// Integer or Long or Short
				inner.setSuperClass(new FullyQualifiedJavaType("org.synthful.smartgwt.client.widgets.UIDataSourceIntegerField"));
				clazz.addImportedType(inner.getSuperClass());
			} else if (type.equals("BigDecimal"))
			{
				// BigDecimal
				inner.setSuperClass(new FullyQualifiedJavaType("org.synthful.smartgwt.client.widgets.UIDataSourceFloatField"));
				clazz.addImportedType(inner.getSuperClass());
			} else if (type.equals("Date"))
			{
				// Date
				inner.setSuperClass(new FullyQualifiedJavaType("org.synthful.smartgwt.client.widgets.UIDataSourceDateField"));
				clazz.addImportedType(inner.getSuperClass());
			} else if (type.equals("String"))
			{
				inner.setSuperClass(new FullyQualifiedJavaType("org.synthful.smartgwt.client.widgets.UIDataSourceTextField"));
				clazz.addImportedType(inner.getSuperClass());
			} else
			{
				throw new IllegalArgumentException("无法识别的类型：" + type);
			}
			clazz.addInnerClass(inner);
		}

		List<CompilationUnit> classes = new ArrayList<CompilationUnit>();
		classes.add(clazz);
		return classes;
	}

	public List<CompilationUnit> generateAdditionalClasses(Database database)
	{
		FullyQualifiedJavaType fqjtSuper = new FullyQualifiedJavaType("com.google.gwt.i18n.client.Constants");
		FullyQualifiedJavaType fqjtGWT = new FullyQualifiedJavaType("com.google.gwt.core.client.GWT");

		Interface interfaze = new Interface(getConstantsInterface(targetPackage));
		interfaze.setVisibility(JavaVisibility.PUBLIC);

		interfaze.addImportedType(fqjtSuper);
		interfaze.addSuperInterface(fqjtSuper);

		for (String s : constants.keySet())
		{
			Method m = new Method();
			m.setName(s);
			m.setReturnType(FullyQualifiedJavaType.getStringInstance());
			m.addAnnotation("@DefaultStringValue(\"" + constants.get(s) + "\")");
			interfaze.addMethod(m);
		}

		TopLevelClass util = new TopLevelClass(getConstantsUtil(targetPackage));
		util.setVisibility(JavaVisibility.PUBLIC);

		Field f = new Field();
		f.setName("constants");
		f.setVisibility(JavaVisibility.PUBLIC);
		f.setStatic(true);
		util.addImportedType(fqjtGWT);
		util.addImportedType(getConstantsInterface(targetPackage));
		f.setType(getConstantsInterface(targetPackage));
		f.setInitializationString("GWT.create(FieldTitleConstants.class);");

		util.addField(f);

		List<CompilationUnit> classes = new ArrayList<CompilationUnit>();
		classes.add(interfaze);
		classes.add(util);
		return classes;
	}
}
