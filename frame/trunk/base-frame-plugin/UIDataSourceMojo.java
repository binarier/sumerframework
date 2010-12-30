package com.huateng.frame.maven;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.ibatis.ibator.api.GeneratedJavaFile;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.InnerClass;
import org.apache.ibatis.ibator.api.dom.java.Interface;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Table;

/**
 * @goal uidatasource
 * @phase generate-sources
 * @execute phase="process-resources"
 * @author chenjun.li
 *
 */
@SuppressWarnings("unchecked")
public class UIDataSourceMojo extends AbstractMojo
{
	/**
	 * @parameter
	 */
	private File configfile;

	/**
	 * @parameter default-value="src/main/java"
	 */
	private String outputDirectory;

	/**
	 * @parameter
	 */
	private String targetPackage;
	
	/**
	 * @parameter default-value="utf-8"
	 */
	private String encoding;

	public void execute() throws MojoExecutionException, MojoFailureException
	{
		Configuration cfg = new Configuration();
		if (configfile != null)
			cfg.configure(configfile);
		else
			cfg.configure();
		
		List<GeneratedJavaFile> allFiles = new ArrayList<GeneratedJavaFile>();

		Iterator<Table> iterTable = cfg.getTableMappings();
		while (iterTable.hasNext())
		{
			Table t = iterTable.next();
			PersistentClass pc = cfg.getClassMapping(getEntityName(t));
//			allFiles.addAll(generateTable(t, pc));
		}

		allFiles.addAll(generateI18nInterface());

		for (GeneratedJavaFile gjf : allFiles)
		{
			File targetFile = new File(outputDirectory, gjf.getFileName());
			try
			{
				FileUtils.forceMkdir(targetFile.getParentFile());
				FileUtils.writeStringToFile(targetFile, gjf.getFormattedContent(), encoding);
			} catch (IOException e)
			{
				throw new MojoExecutionException("文件访问出错", e);
			}
		}
	}

	private FullyQualifiedJavaType getConstantsInterface(String targetPackage)
	{
		return new FullyQualifiedJavaType(targetPackage + ".i18n.FieldTitleConstants");
	}

	private FullyQualifiedJavaType getConstantsUtil(String targetPackage)
	{
		return new FullyQualifiedJavaType(targetPackage + ".i18n.FieldTitleUtil");
	}

	private String getEntityName(Table table)
	{
		return StringUtils.remove(WordUtils.capitalizeFully(table.getName()), "_");
	}

	private String getPropertyName(Column col)
	{
		return StringUtils.uncapitalize(StringUtils.remove(WordUtils.capitalizeFully(col.getName()), "_"));
	}

	private Set<String> constants = new HashSet<String>();

	private List<GeneratedJavaFile> generateI18nInterface()
	{
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
		gjfs.add(new GeneratedJavaFile(interfaze, outputDirectory));
		gjfs.add(new GeneratedJavaFile(util, outputDirectory));

		return gjfs;
	}

	private List<GeneratedJavaFile> generateTable(Table table, PersistentClass pc)
	{
		FullyQualifiedJavaType fqjtClazz = new FullyQualifiedJavaType(targetPackage + ".UI" + getEntityName(table));
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
		m.addBodyLine("setDataSrcID(\"" + WordUtils.uncapitalize(getEntityName(table)) + "DataSource\");");

		clazz.addMethod(m);

		clazz.addImportedType(getConstantsUtil(targetPackage));

		// 字段子类
		Iterator<Column> iterCol = table.getColumnIterator();
		while (iterCol.hasNext())
		{
			Column col = iterCol.next();
			FullyQualifiedJavaType fqjtInner = new FullyQualifiedJavaType(WordUtils.capitalize(getPropertyName(col)));
			InnerClass inner = new InnerClass(fqjtInner);
			inner.setStatic(true);
			inner.setVisibility(JavaVisibility.PUBLIC);

			Method mi = new Method();
			mi.setVisibility(JavaVisibility.PUBLIC);
			mi.setName(fqjtInner.getShortName());
			mi.setConstructor(true);
			mi.addBodyLine("setName(\"" + getPropertyName(col) + "\");");

			// 主键
			if (table.getPrimaryKey().containsColumn(col))
				mi.addBodyLine("setPrimaryKey(true);");

			// 国际化
			String val = MessageFormat.format("{0}_{1}", getEntityName(table), fqjtInner.getShortName());
			mi.addBodyLine(MessageFormat.format("setTitle(FieldTitleUtil.constants.{0}());", val));
			constants.add(val);

			inner.addMethod(mi);

			Property javaProperty = pc.getProperty(getPropertyName(col));
			String type = javaProperty.getType().getName();

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

		List<GeneratedJavaFile> gjfs = new ArrayList<GeneratedJavaFile>();
		gjfs.add(new GeneratedJavaFile(clazz, outputDirectory));

		return gjfs;
	}
}
