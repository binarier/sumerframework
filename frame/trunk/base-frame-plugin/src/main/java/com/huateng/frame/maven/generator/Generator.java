package com.huateng.frame.maven.generator;

import java.util.List;

import org.apache.ibatis.ibator.api.dom.java.CompilationUnit;

import com.huateng.frame.maven.generator.meta.Database;
import com.huateng.frame.maven.generator.meta.Table;

public interface Generator
{
	List<CompilationUnit> generateAdditionalClasses(Table table, Database database);
	
	List<CompilationUnit> generateAdditionalClasses(Database database);
}
