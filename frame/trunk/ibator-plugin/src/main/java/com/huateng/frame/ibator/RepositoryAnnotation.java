package com.huateng.frame.ibator;

import java.util.List;

import org.apache.ibatis.ibator.api.IbatorPluginAdapter;
import org.apache.ibatis.ibator.api.IntrospectedTable;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;

public class RepositoryAnnotation extends IbatorPluginAdapter {

	@Override
	public boolean daoImplementationGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));
		topLevelClass.addAnnotation("@Repository");

		return true;
	}

	public boolean validate(List<String> warnings) {
		return true;
	}
}