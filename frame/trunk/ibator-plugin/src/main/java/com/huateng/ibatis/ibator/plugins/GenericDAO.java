package com.huateng.ibatis.ibator.plugins;

import java.util.List;

import org.apache.ibatis.ibator.api.IbatorPluginAdapter;
import org.apache.ibatis.ibator.api.IntrospectedTable;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.Interface;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.Parameter;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;

import com.huateng.frame.orm.IbatorDAO;
import com.huateng.frame.orm.IbatorDAOImpl;


/**
 * @author chenjun.li
 *
 */
public class GenericDAO extends IbatorPluginAdapter {

	public boolean validate(List<String> warnings) {
		return true;
	}
	
	@Override
	public boolean daoInterfaceGenerated(Interface interfaze, IntrospectedTable introspectedTable)
	{
		FullyQualifiedJavaType record = getRecordType(introspectedTable);
		FullyQualifiedJavaType key = getKeyType(introspectedTable);
		FullyQualifiedJavaType example = introspectedTable.getExampleType();

		interfaze.addImportedType(record);
		interfaze.addImportedType(key);
		interfaze.addImportedType(example);

		FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(IbatorDAO.class.getCanonicalName());
		fqjt.addTypeArgument(record);
		fqjt.addTypeArgument(key);
		fqjt.addTypeArgument(example);
		
		interfaze.addImportedType(fqjt);
		interfaze.addSuperInterface(fqjt);

		interfaze.getMethods().clear();

		return true;
	}
	
	@Override
	public boolean daoImplementationGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
	{
		FullyQualifiedJavaType record = getRecordType(introspectedTable);
		FullyQualifiedJavaType key = getKeyType(introspectedTable);
		FullyQualifiedJavaType example = introspectedTable.getExampleType();

		topLevelClass.addImportedType(record);
		topLevelClass.addImportedType(key);
		topLevelClass.addImportedType(example);

		FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(IbatorDAOImpl.class.getCanonicalName());
		fqjt.addTypeArgument(record);
		fqjt.addTypeArgument(key);
		fqjt.addTypeArgument(example);
		
		topLevelClass.addImportedType(fqjt);
		topLevelClass.setSuperClass(fqjt);

		//修改构造函数
		for (Method m : topLevelClass.getMethods())
		{
			if (m.isConstructor())
			{
				topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
				m.addAnnotation("@Autowired");
				
				FullyQualifiedJavaType fqjtClient = new FullyQualifiedJavaType("com.ibatis.sqlmap.client.SqlMapClient");
				topLevelClass.addImportedType(fqjtClient);
				m.addParameter(new Parameter(fqjtClient, "client"));
				
				m.getBodyLines().clear();
				m.addBodyLine("super(client, \"" + introspectedTable.getFullyQualifiedTable().getIntrospectedTableName() + "\");");
			}
		}
		return true;
	}
	
	private FullyQualifiedJavaType getKeyType(IntrospectedTable introspectedTable)
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
	
	private FullyQualifiedJavaType getRecordType(IntrospectedTable introspectedTable)
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
	
    public boolean daoCountByExampleMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    public boolean daoDeleteByExampleMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    public boolean daoDeleteByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    public boolean daoInsertMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }
    
    public boolean daoInsertSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    	return false;
    }

    public boolean daoSelectByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    public boolean daoSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    public boolean daoSelectByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    public boolean daoUpdateByPrimaryKeySelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    public boolean daoUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    public boolean daoUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

}
