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
		//����Ŀ�����
		//ibator�ļ򻯵�XML�ӿ����˵㣬����֧��Schema�ģ�����ֻ����DTD������
		Document doc = new Document("-//SPRING//DTD BEAN 2.0//EN", "http://www.springframework.org/dtd/spring-beans-2.0.dtd");
		XmlElement beans = new XmlElement("beans");
		doc.setRootElement(beans);

		Map<String, Element> pdmTableMap = null;
		if (pdmMap != null)
			pdmTableMap = pdmMap.get(introspectedTable.getFullyQualifiedTable().getIntrospectedTableName());
		for (IntrospectedColumn ic : introspectedTable.getAllColumns())
		{
			//�����ֶζ���
			XmlElement bean = new XmlElement("bean");
			beans.addElement(bean);
			
			String id = introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()+"__"+ic.getActualColumnName();

			bean.addAttribute(new Attribute("id", "ibatorgenerated_"+id));	//���Ϊ�˿���Merge��ʹ�õ�ǰ׺
			bean.addAttribute(new Attribute("name", id));					//�������ʵ�ʷ���
			bean.addAttribute(new Attribute("abstract", "true"));
			
			addBeanProperty(bean, "path", ic.getJavaProperty());
			
			//�����ֶ�����
			if (ic.getFullyQualifiedJavaType().equals(FullyQualifiedJavaType.getDateInstance()))
			{
				bean.addAttribute(new Attribute("class", DATE_FILED_TYPE));
			}
			else
			{
				bean.addAttribute(new Attribute("class", TEXT_FILED_TYPE));
			}
			
			//ȡPDM�����ı�
			if (pdmTableMap != null)
			{
				Element col = pdmTableMap.get(ic.getActualColumnName());
				String label = col.selectSingleNode("a:Name").getText().trim();
				addBeanProperty(bean, "label", label);	//iBator�Ŀ��̫���ˣ���Ȼ��XML�ļ�д��UTF-8���룬Ȼ��ϵͳĬ�ϱ����������Ҫ���
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
			//����PDM
			String pdmfile = getProperties().getProperty("powerdesigner");
			
			if (StringUtils.isNotBlank(pdmfile))
			{
				SAXReader sar = new SAXReader();
				
				//����Դ�ļ�
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
			throw new IllegalArgumentException("PDM�ļ�������", e);
		}

		return true;
	}
	
}
