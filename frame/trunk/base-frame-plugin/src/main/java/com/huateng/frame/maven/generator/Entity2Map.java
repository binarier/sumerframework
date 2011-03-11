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
		//建立 convertToMap方法
		FullyQualifiedJavaType fqjtSerializable = new FullyQualifiedJavaType("java.io.Serializable");
		entityClass.addImportedType(fqjtSerializable);
		FullyQualifiedJavaType fqjtMap = new FullyQualifiedJavaType("java.util.Map");
		fqjtMap.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
		fqjtMap.addTypeArgument(fqjtSerializable);
		entityClass.addImportedType(fqjtMap);
		
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

		
		//updateFromMap
		Method uf = new Method();
		uf.setName("updateFromMap");
		uf.setVisibility(JavaVisibility.PUBLIC);
		uf.addParameter(new Parameter(fqjtMap, "map"));

		setupSetFields(entityClass, uf, "this");
		
		entityClass.addMethod(uf);
	}
	
	private void setupSetFields(TopLevelClass clazz, Method method, String instanceName)
	{
		for (Field field : clazz.getFields())
		{
			clazz.addImportedType(field.getType());
			if (!field.getType().getShortName().equals("BigDecimal"))
			{
				method.addBodyLine(MessageFormat.format("{0}.set{1}(({2})map.get(\"{3}\"));",
					instanceName,
					StringUtils.capitalize(field.getName()),
					field.getType().getShortName(),
					field.getName()));
			}
			else
			{
				//由于SmartGWT目前还不正式支持BigDecimal，所以我们使用String来传递BigDecimal
				method.addBodyLine(MessageFormat.format("{0}.set{1}(new BigDecimal((String)map.get(\"{2}\")));",
						instanceName,
						StringUtils.capitalize(field.getName()),
						field.getName()));
			}
		}
	}
}
