package com.huateng.frame.maven.generator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.ibator.api.dom.java.CompilationUnit;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.Parameter;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;
import org.springframework.util.StringUtils;

import com.huateng.frame.gwt.server.HibernateServerHome;
import com.huateng.frame.maven.generator.meta.Column;
import com.huateng.frame.maven.generator.meta.Database;
import com.huateng.frame.maven.generator.meta.Table;
import com.huateng.frame.orm.TypedProperty;

public class ServerHome extends AbstractGenerator
{
	private String targetPackage;
	
	public ServerHome(String targetPackage)
	{
		this.targetPackage = targetPackage;
	}

	public List<CompilationUnit> generateAdditionalClasses(Table table, Database database)
	{
		List<CompilationUnit> result = new ArrayList<CompilationUnit>();
		TopLevelClass clazz = new TopLevelClass(new FullyQualifiedJavaType(
				targetPackage + "." +
				table.getJavaClass().getShortName() + "ServerHome"));
		
		clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));
		clazz.addAnnotation("@Repository");
		clazz.setVisibility(JavaVisibility.PUBLIC);
		
		FullyQualifiedJavaType fqjtSuper = new FullyQualifiedJavaType(HibernateServerHome.class.getCanonicalName());
		clazz.addImportedType(table.getJavaClass());
		fqjtSuper.addTypeArgument(table.getJavaClass());
		clazz.addImportedType(table.getJavaKeyClass());
		fqjtSuper.addTypeArgument(table.getJavaKeyClass());
		clazz.addImportedType(fqjtSuper);
		clazz.setSuperClass(fqjtSuper);
		
		Method method = new Method();
		method.setName(clazz.getType().getShortName());
		method.setConstructor(true);
		clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
		method.addAnnotation("@Autowired");
		method.setVisibility(JavaVisibility.PUBLIC);
		method.addParameter(new Parameter(GeneratorUtils.forType(clazz, "org.hibernate.SessionFactory"), "sessionFactory"));
		method.addBodyLine(MessageFormat.format("super({0}.class);", table.getJavaClass().getShortName()));
		method.addBodyLine("setSessionFactory(sessionFactory);");
		clazz.addMethod(method);
		
		for (Column col : table.getColumns())
		{
			//判断是否有Domain
			FullyQualifiedJavaType fqjtDomain;
			if (col.getDomain() == null)
				fqjtDomain = new FullyQualifiedJavaType("java.lang.Void");
			else
				fqjtDomain = col.getDomain().getType();

			FullyQualifiedJavaType fqjtTypedProperty = new FullyQualifiedJavaType(TypedProperty.class.getCanonicalName());
			clazz.addImportedType(fqjtTypedProperty);
			fqjtTypedProperty.addTypeArgument(col.getJavaType());
			clazz.addImportedType(fqjtDomain);
			fqjtTypedProperty.addTypeArgument(fqjtDomain);
			

			Field f = new Field();
			f.setName(StringUtils.capitalize(col.getPropertyName()));
			f.setVisibility(JavaVisibility.PUBLIC);
			f.setFinal(true);
			f.setStatic(true);
			f.setType(fqjtTypedProperty);
			clazz.addImportedType(col.getJavaType());
			f.setInitializationString(MessageFormat.format("new TypedProperty<{0}, {1}>(\"{2}\")", col.getJavaType().getShortName(), fqjtDomain.getShortName(), col.getPropertyName()));
			clazz.addField(f);
		}
		result.add(clazz);
		return result;
	}

	public List<CompilationUnit> generateAdditionalClasses(Database database)
	{
		return null;
	}

}
