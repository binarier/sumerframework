package com.huateng.frame.maven.generator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.ibator.api.dom.java.CompilationUnit;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;
import org.springframework.util.StringUtils;

import com.huateng.frame.gwt.client.ui.ColumnHelper;
import com.huateng.frame.maven.generator.meta.Column;
import com.huateng.frame.maven.generator.meta.Database;
import com.huateng.frame.maven.generator.meta.Table;

public class ClientHome implements Generator
{
	private String targetPackage;
	
	public ClientHome(String targetPackage)
	{
		this.targetPackage = targetPackage;
	}

	public List<CompilationUnit> generateAdditionalClasses(Table table, Database database)
	{
		List<CompilationUnit> result = new ArrayList<CompilationUnit>();
		TopLevelClass clazz = new TopLevelClass(new FullyQualifiedJavaType(
				targetPackage + "." +
				table.getJavaClass().getShortName() + "Home"));
		
		clazz.setVisibility(JavaVisibility.PUBLIC);
		
		FullyQualifiedJavaType fqjtHelper = new FullyQualifiedJavaType(ColumnHelper.class.getCanonicalName());
		clazz.addImportedType(fqjtHelper);
		for (Column col : table.getColumns())
		{
			Field f = new Field();
			f.setName(StringUtils.capitalize(col.getPropertyName()));
			f.setVisibility(JavaVisibility.PUBLIC);
			f.setFinal(true);
			f.setStatic(true);
			f.setType(fqjtHelper);
			f.setInitializationString(MessageFormat.format("new ColumnHelper(\"{0}\", \"{1}\", {2});", col.getPropertyName(), col.getTextName(), col.getLength()));
			clazz.addField(f);
		}
		result.add(clazz);
		return result;
	}

	public List<CompilationUnit> generateAdditionalClasses(Database database)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
