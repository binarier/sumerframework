package com.huateng.ibatis.ibator.plugins;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.apache.ibatis.ibator.api.GeneratedJavaFile;
import org.apache.ibatis.ibator.api.IbatorPluginAdapter;
import org.apache.ibatis.ibator.api.IntrospectedColumn;
import org.apache.ibatis.ibator.api.IntrospectedTable;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.InnerClass;
import org.apache.ibatis.ibator.api.dom.java.Interface;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;

public class UIDataSource extends IbatorPluginAdapter {

	public boolean validate(List<String> warnings) {
		return true;
	}
	
	private FullyQualifiedJavaType getConstantsInterface(String targetPackage)
	{
		return new FullyQualifiedJavaType(targetPackage+".i18n.FieldTitleConstants");
	}

	private FullyQualifiedJavaType getConstantsUtil(String targetPackage)
	{
		return new FullyQualifiedJavaType(targetPackage+".i18n.FieldTitleUtil");
	}
	
	private List<String> constants = new ArrayList<String>();
	
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
		String targetPackage = getProperties().getProperty("targetPackage");
		String targetProject = getProperties().getProperty("targetProject");
		FullyQualifiedJavaType fqjtSuper = new FullyQualifiedJavaType("com.google.gwt.i18n.client.Constants");
		FullyQualifiedJavaType fqjtGWT = new FullyQualifiedJavaType("com.google.gwt.core.client.GWT");
		
		
		Interface interfaze = new Interface(getConstantsInterface(targetPackage));
		interfaze.setVisibility(JavaVisibility.PUBLIC);
		
		interfaze.addImportedType(fqjtSuper);
		interfaze.addSuperInterface(fqjtSuper);
		
		for (String s : constants)
		{
			Method m = new Method();
			m.setName(s);
			m.setReturnType(FullyQualifiedJavaType.getStringInstance());
			m.addAnnotation("@DefaultStringValue(\"" + s + "\")");
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
		
		List<GeneratedJavaFile> gjfs = new ArrayList<GeneratedJavaFile>();
		gjfs.add(new GeneratedJavaFile(interfaze, targetProject));
		gjfs.add(new GeneratedJavaFile(util, targetProject));
		
		return gjfs;
	}

	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
			IntrospectedTable introspectedTable) {
		String targetPackage = getProperties().getProperty("targetPackage");
		String targetProject = getProperties().getProperty("targetProject");

		FullyQualifiedJavaType fqjtClazz = new FullyQualifiedJavaType(targetPackage + ".UI" + introspectedTable.getBaseRecordType().getShortName());
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
		m.addBodyLine("setDataSrcID(\"" + WordUtils.uncapitalize(introspectedTable.getBaseRecordType().getShortName()) + "DataSource\");");
		
		clazz.addMethod(m);

		
		clazz.addImportedType(getConstantsUtil(targetPackage));
		
		//字段子类
		for (IntrospectedColumn col : introspectedTable.getAllColumns())
		{
			FullyQualifiedJavaType fqjtInner = new FullyQualifiedJavaType(WordUtils.capitalize(col.getJavaProperty()));
			InnerClass inner = new InnerClass(fqjtInner);
			inner.setStatic(true);
			inner.setVisibility(JavaVisibility.PUBLIC);

			Method mi = new Method();
			mi.setVisibility(JavaVisibility.PUBLIC);
			mi.setName(fqjtInner.getShortName());
			mi.setConstructor(true);
			mi.addBodyLine("setName(\"" + col.getJavaProperty() + "\");");
			
			//国际化
			String val = MessageFormat.format("{0}_{1}", introspectedTable.getBaseRecordType().getShortName(), fqjtInner.getShortName());
			mi.addBodyLine(MessageFormat.format("setTitle(FieldTitleUtil.constants.{0}());", val));
			constants.add(val);

			inner.addMethod(mi);
			
			String type = col.getFullyQualifiedJavaType().getShortName();

			if (type.equals("Integer")||type.equals("Long"))
			{
				//Integer or Long
				inner.setSuperClass(new FullyQualifiedJavaType("org.synthful.smartgwt.client.widgets.UIDataSourceIntegerField"));
				clazz.addImportedType(inner.getSuperClass());
			}
			else if (type.equals("BigDecimal"))
			{
				//BigDecimal
				inner.setSuperClass(new FullyQualifiedJavaType("org.synthful.smartgwt.client.widgets.UIDataSourceFloatField"));
				clazz.addImportedType(inner.getSuperClass());
			}
			else if (type.equals("Date"))
			{
				//Date
				inner.setSuperClass(new FullyQualifiedJavaType("org.synthful.smartgwt.client.widgets.UIDataSourceDateField"));
				clazz.addImportedType(inner.getSuperClass());
			}
			else if (type.equals("String"))
			{
				inner.setSuperClass(new FullyQualifiedJavaType("org.synthful.smartgwt.client.widgets.UIDataSourceTextField"));
				clazz.addImportedType(inner.getSuperClass());
			}
			else
			{
				throw new IllegalArgumentException("未识别的类型：" + col.getFullyQualifiedJavaType());
			}
			clazz.addInnerClass(inner);
		}
		
		List<GeneratedJavaFile> gjfs = new ArrayList<GeneratedJavaFile>();
		gjfs.add(new GeneratedJavaFile(clazz, targetProject));
		
		return gjfs;
	}

}
