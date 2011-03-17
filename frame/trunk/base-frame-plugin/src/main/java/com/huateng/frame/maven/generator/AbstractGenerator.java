package com.huateng.frame.maven.generator;

import java.util.List;

import org.apache.ibatis.ibator.api.dom.java.CompilationUnit;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;

import com.huateng.frame.maven.generator.meta.Database;
import com.huateng.frame.maven.generator.meta.Table;

/**
 * 为实现类提供默认空实现
 * @author chenjun.li
 *
 */
public abstract class AbstractGenerator implements Generator
{

	public List<CompilationUnit> generateAdditionalClasses(Table table, Database database)
	{
		return null;
	}

	public List<CompilationUnit> generateAdditionalClasses(Database database)
	{
		return null;
	}

	public void afterEntityGenerated(TopLevelClass entityClass, Table table)
	{
	}

	public void afterKeyGenerated(TopLevelClass keyClass)
	{
	}
}
