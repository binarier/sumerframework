package com.huateng.frame.ibator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.ibator.api.IbatorPluginAdapter;
import org.apache.ibatis.ibator.api.IntrospectedColumn;
import org.apache.ibatis.ibator.api.IntrospectedTable;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.InnerClass;
import org.apache.ibatis.ibator.api.dom.java.Interface;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.Parameter;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;

import com.huateng.frame.orm.IbatorCriteria;
import com.huateng.frame.orm.IbatorDAO;
import com.huateng.frame.orm.IbatorDAOImpl;
import com.huateng.frame.orm.IbatorExample;

/**
 * @author chenjun.li
 * 
 */
public class GenericDAO extends IbatorPluginAdapter
{

	public boolean validate(List<String> warnings)
	{
		return true;
	}

	@Override
	public boolean daoInterfaceGenerated(Interface interfaze, IntrospectedTable introspectedTable)
	{
		FullyQualifiedJavaType record = ModelTypeUtils.getRecordType(introspectedTable);
		FullyQualifiedJavaType key = ModelTypeUtils.getKeyType(introspectedTable);
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
		FullyQualifiedJavaType record = ModelTypeUtils.getRecordType(introspectedTable);
		FullyQualifiedJavaType key = ModelTypeUtils.getKeyType(introspectedTable);
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

		// 修改构造函数
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
				m.addBodyLine(MessageFormat.format("super(client, \"{0}\");", introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()));
			}
		}
		return true;
	}

	public boolean daoCountByExampleMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
	{
		return false;
	}

	public boolean daoDeleteByExampleMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
	{
		return false;
	}

	public boolean daoDeleteByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
	{
		return false;
	}

	public boolean daoInsertMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
	{
		return false;
	}

	public boolean daoInsertSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
	{
		return false;
	}

	public boolean daoSelectByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
	{
		return false;
	}

	public boolean daoSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
	{
		return false;
	}

	public boolean daoSelectByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
	{
		return false;
	}

	public boolean daoUpdateByPrimaryKeySelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
	{
		return false;
	}

	public boolean daoUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
	{
		return false;
	}

	public boolean daoUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
	{
		return false;
	}

	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
	{

		// 增加父类
		FullyQualifiedJavaType fqjtSuper = new FullyQualifiedJavaType(IbatorExample.class.getCanonicalName());
		FullyQualifiedJavaType fqjtInner = new FullyQualifiedJavaType(IbatorCriteria.class.getCanonicalName());

		topLevelClass.addImportedType(fqjtSuper);
		topLevelClass.addImportedType(fqjtInner);
		
		topLevelClass.setSuperClass(fqjtSuper);

		topLevelClass.getInnerClasses().get(0).setSuperClass(fqjtInner);
		
		// field 不留，method只留createCriteria和createCriteriaInternal
		topLevelClass.getFields().clear();
		List<Method> newMethods = new ArrayList<Method>();
		for (Method m : topLevelClass.getMethods())
		{
			if (m.getName().startsWith("createCriteria"))
				newMethods.add(m);
		}
		topLevelClass.getMethods().clear();
		topLevelClass.getMethods().addAll(newMethods);
		
		//添加构造函数
		Method m;
		m = new Method();
		m.setVisibility(JavaVisibility.PUBLIC);
		m.setConstructor(true);
		m.setName(topLevelClass.getType().getShortName());
		m.addBodyLine("super();");
		m.addBodyLine("setupMapping();");
		topLevelClass.addMethod(m);
		
		m = new Method();
		m.setVisibility(JavaVisibility.PUBLIC);
		m.setConstructor(true);
		m.setName(topLevelClass.getType().getShortName());
		m.addParameter(new Parameter(topLevelClass.getType(), "example"));
		m.addBodyLine("super(example);");
		m.addBodyLine("setupMapping();");
		topLevelClass.addMethod(m);

		//处理子类，只留操作条件的方法，特征是返回值为Criteria
		InnerClass inner = topLevelClass.getInnerClasses().get(0);
		inner.getFields().clear();
		newMethods.clear();
		for (Method im : inner.getMethods())
		{
			if (im.getReturnType() != null &&
				im.getReturnType().getShortName().equals("Criteria"))
				newMethods.add(im);
		}
		inner.getMethods().clear();
		inner.getMethods().addAll(newMethods);
		
		//field mapping
		FullyQualifiedJavaType fqjtHashMap = new FullyQualifiedJavaType("java.util.HashMap");
		fqjtHashMap.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
		fqjtHashMap.addTypeArgument(FullyQualifiedJavaType.getStringInstance());

		Field f = new Field();
		f.setName("mapping");
		f.setStatic(true);
		f.setType(fqjtHashMap);
		f.setVisibility(JavaVisibility.PRIVATE);
		topLevelClass.addField(f);

		m = new Method();
		m.setVisibility(JavaVisibility.PRIVATE);
		m.setName("setupMapping");
		m.addBodyLine(MessageFormat.format("synchronized ({0}.class)", topLevelClass.getType().getShortName())+"{");
		m.addBodyLine("if (mapping == null){");
		m.addBodyLine("mapping = new HashMap<String, String>();");
		for (IntrospectedColumn ic : introspectedTable.getAllColumns())
		{
			m.addBodyLine(MessageFormat.format("mapping.put(\"{0}\", \"{1}\");", ic.getJavaProperty(), ic.getActualColumnName()));
		}
		m.addBodyLine("}");
		m.addBodyLine("fieldNameMap = mapping;");
		m.addBodyLine("}");

		topLevelClass.addMethod(m);
		return true;
		
	}
	
	@Override
	public void initialized(IntrospectedTable introspectedTable)
	{
		String examplePackage = getProperties().getProperty("examplePackage");
		FullyQualifiedJavaType fqjtOriginal = (FullyQualifiedJavaType)introspectedTable.getAttribute(IntrospectedTable.ATTR_EXAMPLE_TYPE);
		introspectedTable.setAttribute(IntrospectedTable.ATTR_EXAMPLE_TYPE, new FullyQualifiedJavaType(examplePackage + "." + fqjtOriginal.getShortName()));
	}
}
