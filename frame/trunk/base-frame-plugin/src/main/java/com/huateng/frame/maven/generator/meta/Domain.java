package com.huateng.frame.maven.generator.meta;

import java.util.LinkedHashMap;

import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;

public class Domain {
	private String code;
	private String name;
	private String dbType;
	private FullyQualifiedJavaType javaType;
	/**
	 * 自身的全名，需由总控填写
	 */
	private FullyQualifiedJavaType type;
	/**
	 * 可用值列表，key为取值，value为描述, 需要保持有序，所以使用 {@link LinkedHashMap}，这里不作类型处理
	 */
	private LinkedHashMap<String, String> valueMap;
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDbType()
	{
		return dbType;
	}
	public void setDbType(String dbType)
	{
		this.dbType = dbType;
	}
	public FullyQualifiedJavaType getJavaType()
	{
		return javaType;
	}
	public void setJavaType(FullyQualifiedJavaType javaType)
	{
		this.javaType = javaType;
	}
	public LinkedHashMap<String, String> getValueMap()
	{
		return valueMap;
	}
	public void setValueMap(LinkedHashMap<String, String> valueMap)
	{
		this.valueMap = valueMap;
	}
	public FullyQualifiedJavaType getType()
	{
		return type;
	}
	public void setType(FullyQualifiedJavaType type)
	{
		this.type = type;
	}
}
