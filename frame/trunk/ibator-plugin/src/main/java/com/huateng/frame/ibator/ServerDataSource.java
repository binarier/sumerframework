package com.huateng.frame.ibator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.ibator.api.GeneratedJavaFile;
import org.apache.ibatis.ibator.api.IbatorPluginAdapter;
import org.apache.ibatis.ibator.api.IntrospectedTable;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.Parameter;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;

import com.huateng.frame.gwt.server.StandardSmartDataSource;

public class ServerDataSource extends IbatorPluginAdapter {

	public boolean validate(List<String> warnings) {
		return true;
	}
	
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		String targetPackage = getProperties().getProperty("targetPackage");
		String targetProject = getProperties().getProperty("targetProject");
		
		FullyQualifiedJavaType fqjtSuper = new FullyQualifiedJavaType(StandardSmartDataSource.class.getCanonicalName());
		fqjtSuper.addTypeArgument(ModelTypeUtils.getRecordType(introspectedTable));
		fqjtSuper.addTypeArgument(ModelTypeUtils.getKeyType(introspectedTable));
		fqjtSuper.addTypeArgument(introspectedTable.getExampleType());

		TopLevelClass clazz = new TopLevelClass(new FullyQualifiedJavaType(targetPackage + "." + introspectedTable.getBaseRecordType().getShortName() + "DataSource"));
		clazz.setVisibility(JavaVisibility.PUBLIC);
		clazz.addImportedType(fqjtSuper);
		clazz.setSuperClass(fqjtSuper);

		clazz.addImportedType(ModelTypeUtils.getRecordType(introspectedTable));
		clazz.addImportedType(ModelTypeUtils.getKeyType(introspectedTable));
		clazz.addImportedType(introspectedTable.getExampleType());

		clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
		clazz.addAnnotation("@Service");

		Method method = new Method();
		method.setName(clazz.getType().getShortName());
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setConstructor(true);
		clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
		method.addAnnotation("@Autowired");
		clazz.addImportedType(introspectedTable.getDAOInterfaceType());
		method.addParameter(new Parameter(introspectedTable.getDAOInterfaceType(), "dao"));
		clazz.addImportedType(introspectedTable.getExampleType());
		method.addBodyLine(MessageFormat.format("super(dao, {0}.class);", introspectedTable.getExampleType().getShortName()));
		clazz.addMethod(method);

		List<GeneratedJavaFile> gjfs = new ArrayList<GeneratedJavaFile>();
		gjfs.add(new GeneratedJavaFile(clazz, targetProject));
		return gjfs;
	}
}
