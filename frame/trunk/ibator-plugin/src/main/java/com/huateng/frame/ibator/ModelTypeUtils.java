package com.huateng.frame.ibator;

import org.apache.ibatis.ibator.api.IntrospectedTable;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;

public class ModelTypeUtils {
	public static FullyQualifiedJavaType getKeyType(IntrospectedTable introspectedTable)
	{
		if (introspectedTable.getPrimaryKeyColumns().size() == 0)
		{
			//没有主键
			return introspectedTable.getBaseRecordType();
		}
		else
		{
			return introspectedTable.getPrimaryKeyType();
		}
	}
	
	public static FullyQualifiedJavaType getRecordType(IntrospectedTable introspectedTable)
	{
		if (introspectedTable.getNonPrimaryKeyColumns().size() == 0)
		{
			//如果全是主鍵，則使用Key類型
			return introspectedTable.getPrimaryKeyType();
		}
		else
		{
			//否則使用記錄類型
			return introspectedTable.getBaseRecordType();
		}
	}
}
