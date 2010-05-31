package com.huateng.sumer.tools.ibator.plugins;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.ibator.api.GeneratedXmlFile;
import org.apache.ibatis.ibator.api.IbatorPluginAdapter;
import org.apache.ibatis.ibator.api.IntrospectedColumn;
import org.apache.ibatis.ibator.api.IntrospectedTable;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.xml.Attribute;
import org.apache.ibatis.ibator.api.dom.xml.Document;
import org.apache.ibatis.ibator.api.dom.xml.XmlElement;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class FieldDefinitionPlugin extends IbatorPluginAdapter {
	
	private Map<String, Map<String, Element>> pdmMap;
	
	public final String DATE_FILED_TYPE = "com.huateng.sumer.runtime.web.meta.DateField";
	public final String TEXT_FILED_TYPE = "com.huateng.sumer.runtime.web.meta.TextField";

	public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(
			IntrospectedTable introspectedTable) {
		//建立目标对象
		//ibator的简化的XML接口土了点，不能支持Schema的，所以只能用DTD声明了
		Document doc = new Document("-//SPRING//DTD BEAN 2.0//EN", "http://www.springframework.org/dtd/spring-beans-2.0.dtd");
		XmlElement beans = new XmlElement("beans");
		doc.setRootElement(beans);

		Map<String, Element> pdmTableMap = null;
		if (pdmMap != null)
			pdmTableMap = pdmMap.get(introspectedTable.getFullyQualifiedTable().getIntrospectedTableName());
		for (IntrospectedColumn ic : introspectedTable.getAllColumns())
		{
			//建立字段定义
			XmlElement bean = new XmlElement("bean");
			beans.addElement(bean);
			
			String id = introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()+"__"+ic.getActualColumnName();

			bean.addAttribute(new Attribute("id", "ibatorgenerated_"+id));	//这个为了可以Merge而使用的前缀
			bean.addAttribute(new Attribute("name", id));					//用这个来实际访问
			bean.addAttribute(new Attribute("abstract", "true"));
			
			addBeanProperty(bean, "path", ic.getJavaProperty());
			
			//决定字段类型
			if (ic.getFullyQualifiedJavaType().equals(FullyQualifiedJavaType.getDateInstance()))
			{
				bean.addAttribute(new Attribute("class", DATE_FILED_TYPE));
			}
			else
			{
				bean.addAttribute(new Attribute("class", TEXT_FILED_TYPE));
			}
			
			//取PDM决定文本
			if (pdmTableMap != null)
			{
				Element col = pdmTableMap.get(ic.getActualColumnName());
				String label = col.selectSingleNode("a:Name").getText().trim();
				addBeanProperty(bean, "label", label);	//iBator的框架太土了，竟然把XML文件写死UTF-8编码，然后按系统默认编码输出，需要解决
			}
		}
		GeneratedXmlFile gxf = new GeneratedXmlFile(doc, introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()+".xml", "field", "src/META-INF", true);
		List<GeneratedXmlFile> gxfs = new ArrayList<GeneratedXmlFile>();
		gxfs.add(gxf);
		return gxfs;
	}

	private XmlElement addBeanProperty(XmlElement bean, String name, String value)
	{
		XmlElement prop = new XmlElement("property");
		prop.addAttribute(new Attribute("name", name));
		prop.addAttribute(new Attribute("value", value));
		
		bean.addElement(prop);
		return bean;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean validate(List<String> warnings) {
		
		try
		{
			//解析PDM
			String pdmfile = getProperties().getProperty("powerdesigner");
			
			if (StringUtils.isNotBlank(pdmfile))
			{
				SAXReader sar = new SAXReader();
				
				//解析源文件
				org.dom4j.Document pdm = sar.read(new FileInputStream(pdmfile));
				pdmMap = new HashMap<String, Map<String,Element>>();
				
				for (Element nodeTable : (List<Element>)pdm.selectNodes("//c:Tables/o:Table"))
				{
					String table = nodeTable.selectSingleNode("a:Code").getText();
					Map<String, Element> tableMap = new HashMap<String, Element>();
					pdmMap.put(table, tableMap);
					for (Element nodeColumn : (List<Element>)nodeTable.selectNodes("c:Columns/o:Column"))
					{
						Node nodeCode = nodeColumn.selectSingleNode("a:Code");
						String code = nodeCode.getText().trim();
						tableMap.put(code, nodeColumn);
					}
				}
			}
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException("PDM文件解析错", e);
		}

		return true;
	}
	
}
