package com.huateng.frame.maven.generator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.Parameter;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;
import org.apache.ibatis.ibator.internal.util.JavaBeansUtil;

public class GeneratorUtils
{
	public static String dbName2ClassName(String dbName)
	{
		String s = dbName.toLowerCase();
		s = WordUtils.capitalizeFully(s, new char[]
		{ '_' });
		s = StringUtils.remove(s, "_");
		return s;
	}

	public static String dbName2PropertyName(String dbName)
	{
		return WordUtils.uncapitalize(dbName2ClassName(dbName));
	}

	public static FullyQualifiedJavaType forType(TopLevelClass topLevelClass, String type)
	{
		FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(type);
		topLevelClass.addImportedType(fqjt);
		return fqjt;
	}

	public static Field generateProperty(TopLevelClass clazz, FullyQualifiedJavaType fqjt, String property, boolean trimStrings)
	{
		clazz.addImportedType(fqjt);

		Field field = new Field();
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(fqjt);
		field.setName(property);
		clazz.addField(field);

		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(fqjt);
		method.setName(JavaBeansUtil.getGetterMethodName(field.getName(), field.getType()));
		StringBuilder sb = new StringBuilder();
		sb.append("return ");
		sb.append(property);
		sb.append(';');
		method.addBodyLine(sb.toString());
		clazz.addMethod(method);

		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName(JavaBeansUtil.getSetterMethodName(property));
		method.addParameter(new Parameter(fqjt, property));

		if (trimStrings && fqjt.equals(FullyQualifiedJavaType.getStringInstance()))
		{
			sb.setLength(0);
			sb.append("this."); //$NON-NLS-1$
			sb.append(property);
			sb.append(" = "); //$NON-NLS-1$
			sb.append(property);
			sb.append(" == null ? null : "); //$NON-NLS-1$
			sb.append(property);
			sb.append(".trim();"); //$NON-NLS-1$
			method.addBodyLine(sb.toString());
		}
		else
		{
			sb.setLength(0);
			sb.append("this."); //$NON-NLS-1$
			sb.append(property);
			sb.append(" = "); //$NON-NLS-1$
			sb.append(property);
			sb.append(';');
			method.addBodyLine(sb.toString());
		}

		clazz.addMethod(method);

		return field;
	}
}
