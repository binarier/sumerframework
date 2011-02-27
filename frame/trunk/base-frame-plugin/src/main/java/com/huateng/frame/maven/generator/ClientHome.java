package com.huateng.frame.maven.generator;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.ibator.api.dom.java.CompilationUnit;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;

import com.huateng.frame.gwt.client.ui.DecimalColumnHelper;
import com.huateng.frame.gwt.client.ui.IntegerColumnHelper;
import com.huateng.frame.gwt.client.ui.TextColumnHelper;
import com.huateng.frame.gwt.shared.DomainSupport;
import com.huateng.frame.maven.generator.meta.Column;
import com.huateng.frame.maven.generator.meta.Database;
import com.huateng.frame.maven.generator.meta.Domain;
import com.huateng.frame.maven.generator.meta.Table;

public class ClientHome extends AbstractGenerator
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
				table.getJavaClass().getShortName() + "ClientHome"));
		
		clazz.setVisibility(JavaVisibility.PUBLIC);
		
		// public static final ColumnHelper<String> RoleId(){ return new ColumnHelper<String>("roleId", "角色ID", 20);}
		// 这是为了避免静态变量初始化时时间过长的问题，所以改为函数调用
		// TODO 还需要进一步优化，改为缓存形式
		for (Column col : table.getColumns())
		{
			Method m = new Method();
			m.setName(StringUtils.capitalize(col.getPropertyName()));
			m.setVisibility(JavaVisibility.PUBLIC);
			m.setFinal(true);
			m.setStatic(true);
			
			FullyQualifiedJavaType fqjtHelper;
			String line;
			if (col.getJavaType().equals(FullyQualifiedJavaType.getStringInstance()))
			{
				//文本字段
				fqjtHelper = new FullyQualifiedJavaType(TextColumnHelper.class.getCanonicalName());
				line = MessageFormat.format("return new {0}<{1}>(\"{2}\", \"{3}\", {4})", fqjtHelper.getShortName(), col.getJavaType().getShortName(), col.getPropertyName(), col.getTextName(), col.getLength());
			}
			else if (col.getJavaType().equals(FullyQualifiedJavaType.getIntInstance()))
			{
				//整型
				fqjtHelper = new FullyQualifiedJavaType(IntegerColumnHelper.class.getCanonicalName());
				String max = StringUtils.repeat("9", col.getLength());
				line = MessageFormat.format("return new {0}<{1}>(\"{2}\", \"{3}\", {4}, {5}, {6})",
						fqjtHelper.getShortName(),
						col.getJavaType().getShortName(),
						col.getPropertyName(),
						col.getTextName(),
						col.getLength(),
						0,
						max);
			}
			else
			{
				//带小数
				fqjtHelper = new FullyQualifiedJavaType(DecimalColumnHelper.class.getCanonicalName());
				String max = StringUtils.repeat("9", col.getLength() - col.getScale()) + "." + StringUtils.repeat("9", col.getScale());
				line = MessageFormat.format("return new {0}<{1}>(\"{2}\", \"{3}\", {4}, {5}, {6}f, {7})",
						fqjtHelper.getShortName(), 
						col.getJavaType().getShortName(),
						col.getPropertyName(),
						col.getTextName(),
						col.getLength(),
						0,
						max,
						col.getScale());
			}

			clazz.addImportedType(fqjtHelper);
			fqjtHelper.addTypeArgument(col.getJavaType());
			m.setReturnType(fqjtHelper);
			clazz.addImportedType(col.getJavaType());
			
			if (col.getDomain() == null)
				m.addBodyLine(line + ";");
			else
			{
				Domain domain = col.getDomain();

				clazz.addImportedType(domain.getJavaType());
				clazz.addImportedType(domain.getType());
				clazz.addImportedType(new FullyQualifiedJavaType(DomainSupport.class.getCanonicalName()));

				m.addBodyLine(line + "{");
				//再加上动态子类定义
				//return new ColumnHelper<String>("status", "用户状态", 10){
				//	@Override
				//	public DomainSupport<String> getDomain(){
				//		return new DmnScmUserStatus();
				//	}
				//};
				m.addBodyLine("@Override");
				m.addBodyLine(MessageFormat.format("public DomainSupport<{0}> getDomain()'{'", domain.getJavaType().getShortName()));
				m.addBodyLine(MessageFormat.format("return new {0}();", domain.getType().getShortName()));
				m.addBodyLine("}");
				m.addBodyLine("};");
			}
			clazz.addMethod(m);
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
