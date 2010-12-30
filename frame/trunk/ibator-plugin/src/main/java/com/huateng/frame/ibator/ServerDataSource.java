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

import com.huateng.frame.gwt.server.HibernateSmartCriteriaService;

public class ServerDataSource extends IbatorPluginAdapter {

	public boolean validate(List<String> warnings) {
		return true;
	}
	
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		String targetPackage = getProperties().getProperty("targetPackage");
		String targetProject = getProperties().getProperty("targetProject");
		
		FullyQualifiedJavaType fqjtSuper = new FullyQualifiedJavaType(HibernateSmartCriteriaService.class.getCanonicalName());
		fqjtSuper.addTypeArgument(ModelTypeUtils.getRecordType(introspectedTable));

		TopLevelClass clazz = new TopLevelClass(new FullyQualifiedJavaType(targetPackage + "." + introspectedTable.getBaseRecordType().getShortName() + "DataSource"));
		clazz.setVisibility(JavaVisibility.PUBLIC);
		clazz.addImportedType(fqjtSuper);
		clazz.setSuperClass(fqjtSuper);

		clazz.addImportedType(ModelTypeUtils.getRecordType(introspectedTable));

		clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
		clazz.addAnnotation("@Service");

		Method method = new Method();
		method.setName(clazz.getType().getShortName());
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setConstructor(true);
		clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
		method.addAnnotation("@Autowired");
		method.addBodyLine(MessageFormat.format("super({0}.class);", ModelTypeUtils.getRecordType(introspectedTable).getShortName()));
		clazz.addMethod(method);

		List<GeneratedJavaFile> gjfs = new ArrayList<GeneratedJavaFile>();
		gjfs.add(new GeneratedJavaFile(clazz, targetProject));
		return gjfs;
	}
}
