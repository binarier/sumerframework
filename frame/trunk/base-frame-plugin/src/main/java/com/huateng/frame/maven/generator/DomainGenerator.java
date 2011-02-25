package com.huateng.frame.maven.generator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.ibatis.ibator.api.dom.java.CompilationUnit;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.Parameter;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;

import com.huateng.frame.gwt.shared.DomainSupport;
import com.huateng.frame.maven.generator.meta.Database;
import com.huateng.frame.maven.generator.meta.Domain;

public class DomainGenerator extends AbstractGenerator
{
	private String targetPackage;

	public DomainGenerator(String targetPackage)
	{
		this.targetPackage = targetPackage;
	}
	
	@Override
	public List<CompilationUnit> generateAdditionalClasses(Database database)
	{
		List<CompilationUnit> result = new ArrayList<CompilationUnit>();
		
		for (Domain domain : database.getDomains())
		{
			TopLevelClass clazz = new TopLevelClass(new FullyQualifiedJavaType(
					targetPackage + "." +
					GeneratorUtils.dbName2ClassName(domain.getCode())));
			clazz.setVisibility(JavaVisibility.PUBLIC);
			FullyQualifiedJavaType fqjtSuper = new FullyQualifiedJavaType(DomainSupport.class.getCanonicalName());

			clazz.addImportedType(domain.getJavaType());
			fqjtSuper.addTypeArgument(domain.getJavaType());

			clazz.addImportedType(fqjtSuper);
			clazz.setSuperClass(fqjtSuper);
			
//			protected void fill(Map<String, String> map) {
//				map.put("Normal", "正常");
//				map.put("Locked", "锁定");
//			}

			Method fill = new Method();
			fill.setName("fill");
			fill.setVisibility(JavaVisibility.PROTECTED);
			FullyQualifiedJavaType fqjtMap = new FullyQualifiedJavaType("java.util.Map");
			fqjtMap.addTypeArgument(domain.getJavaType());
			fqjtMap.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
			clazz.addImportedType(fqjtMap);
			fill.addParameter(new Parameter(fqjtMap, "map"));
			if (domain.getValueMap() != null)
			{
				for (Entry<String, String> entry : domain.getValueMap().entrySet())
				{
					String key, value;
					if (domain.getJavaType().equals(FullyQualifiedJavaType.getStringInstance()))
						key = "\"" + entry.getKey() + "\"";
					else
						key = entry.getKey();
					value = "\"" + entry.getValue() + "\"";
					fill.addBodyLine(MessageFormat.format("map.put({0}, {1});", key, value));
				}
			}
			clazz.addMethod(fill);
			
			result.add(clazz);
			
			//TODO 需要整合进总控，因为修改了Domain
			domain.setType(clazz.getType());
		}
		
		return result;
	}
}
