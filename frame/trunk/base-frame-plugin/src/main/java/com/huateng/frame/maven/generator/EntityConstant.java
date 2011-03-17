package com.huateng.frame.maven.generator;

import org.apache.commons.lang.WordUtils;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;

import com.huateng.frame.maven.generator.meta.Column;
import com.huateng.frame.maven.generator.meta.Table;

public class EntityConstant extends AbstractGenerator {
	
	private String prefix = "P_";

	@Override
	public void afterEntityGenerated(TopLevelClass entityClass, Table table) {
		for (Column col : table.getColumns())
		{
			Field field = new Field();
			field.setVisibility(JavaVisibility.PUBLIC);
			field.setStatic(true);
			field.setFinal(true);
			field.setType(FullyQualifiedJavaType.getStringInstance());
			field.setName(prefix + WordUtils.capitalize(col.getPropertyName()));
			field.setInitializationString('"' + col.getPropertyName() + '"');
			
			entityClass.addField(field);
		}
	}
}
