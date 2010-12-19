package com.huateng.frame.maven;

import java.util.Properties;

import org.apache.ibatis.ibator.api.CommentGenerator;
import org.apache.ibatis.ibator.api.FullyQualifiedTable;
import org.apache.ibatis.ibator.api.dom.java.CompilationUnit;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.InnerClass;
import org.apache.ibatis.ibator.api.dom.java.InnerEnum;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.xml.XmlElement;

public class IbatorNullCommentGenerator implements CommentGenerator {

	public void addConfigurationProperties(Properties properties) {
	}

	public void addFieldComment(Field field, FullyQualifiedTable table,
			String columnName) {
	}

	public void addFieldComment(Field field, FullyQualifiedTable table) {
	}

	public void addClassComment(InnerClass innerClass, FullyQualifiedTable table) {
	}

	public void addEnumComment(InnerEnum innerEnum, FullyQualifiedTable table) {
	}

	public void addGetterComment(Method method, FullyQualifiedTable table,
			String columnName) {
	}

	public void addSetterComment(Method method, FullyQualifiedTable table,
			String columnName) {
	}

	public void addGeneralMethodComment(Method method, FullyQualifiedTable table) {
	}

	public void addJavaFileComment(CompilationUnit compilationUnit) {
	}

	public void addComment(XmlElement xmlElement) {
	}

	public void addRootComment(XmlElement rootElement) {
	}

}
