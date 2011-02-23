package com.huateng.frame.maven.generator;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.Parameter;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;

public class Entity2Map extends AbstractGenerator
{
	@Override
	public void afterEntityGenerated(TopLevelClass entityClass)
	{
		//建立 createFromMap 方法和 convertToMap方法
		FullyQualifiedJavaType fqjtSerializable = new FullyQualifiedJavaType("java.io.Serializable");
		entityClass.addImportedType(fqjtSerializable);
		FullyQualifiedJavaType fqjtMap = new FullyQualifiedJavaType("java.util.Map");
		fqjtMap.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
		fqjtMap.addTypeArgument(fqjtSerializable);
		entityClass.addImportedType(fqjtMap);
		
		Method from = new Method();
		from.setName("createFromMap");
		from.setVisibility(JavaVisibility.PUBLIC);
		from.setStatic(true);
		from.setReturnType(entityClass.getType());
		from.addParameter(new Parameter(fqjtMap, "map"));
		from.addBodyLine(MessageFormat.format("{0} instance = new {0}();", entityClass.getType().getShortName()));

		for (Field field : entityClass.getFields())
		{
			entityClass.addImportedType(field.getType());
			from.addBodyLine(MessageFormat.format("instance.set{0}(({1})map.get(\"{2}\"));", 
					StringUtils.capitalize(field.getName()),
					field.getType().getShortName(),
					field.getName()));
		}
		from.addBodyLine("return instance;");
		entityClass.addMethod(from);
		
		Method to = new Method();
		to.setName("convertToMap");
		to.setVisibility(JavaVisibility.PUBLIC);
		to.setReturnType(fqjtMap);
		FullyQualifiedJavaType fqjtHashMap = new FullyQualifiedJavaType("java.util.HashMap");
		fqjtHashMap.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
		fqjtHashMap.addTypeArgument(fqjtSerializable);
		entityClass.addImportedType(fqjtHashMap);
		
		to.addBodyLine("HashMap<String, Serializable> map = new HashMap<String, Serializable>();");
		for (Field field : entityClass.getFields())
		{
			entityClass.addImportedType(field.getType());
			to.addBodyLine(MessageFormat.format("map.put(\"{0}\", {0});", field.getName()));
		}
		to.addBodyLine("return map;");
		
		entityClass.addMethod(to);
	}
}