package com.huateng.frame.maven.generator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.ibator.api.dom.java.CompilationUnit;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;

import com.huateng.frame.gwt.server.HibernateSmartCriteriaService;
import com.huateng.frame.maven.generator.meta.Database;
import com.huateng.frame.maven.generator.meta.Table;

public class StdSmartDataSource implements Generator
{
	private String targetPackage;
	
	public StdSmartDataSource(String targetPackage)
	{
		this.targetPackage = targetPackage;
	}

	public List<CompilationUnit> generateAdditionalClasses(Table table, Database database)
	{
		FullyQualifiedJavaType fqjtSuper = new FullyQualifiedJavaType(HibernateSmartCriteriaService.class.getCanonicalName());
		fqjtSuper.addTypeArgument(table.getJavaClass());

		TopLevelClass clazz = new TopLevelClass(new FullyQualifiedJavaType(targetPackage + "." + table.getJavaClass().getShortName() + "DS"));
		clazz.setVisibility(JavaVisibility.PUBLIC);
		clazz.addImportedType(fqjtSuper);
		clazz.setSuperClass(fqjtSuper);

		clazz.addImportedType(table.getJavaClass());

		clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
		clazz.addAnnotation("@Service");

		Method method = new Method();
		method.setName(clazz.getType().getShortName());
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setConstructor(true);
//		clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
//		method.addAnnotation("@Autowired");
		method.addBodyLine(MessageFormat.format("super({0}.class);", table.getJavaClass().getShortName()));
		clazz.addMethod(method);

		List<CompilationUnit> classes = new ArrayList<CompilationUnit>();
		classes.add(clazz);
		return classes;
	}

	public List<CompilationUnit> generateAdditionalClasses(Database database)
	{
		return null;
	}

}
