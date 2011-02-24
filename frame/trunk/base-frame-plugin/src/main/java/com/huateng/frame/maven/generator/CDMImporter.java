package com.huateng.frame.maven.generator;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.maven.plugin.logging.Log;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.huateng.frame.maven.generator.meta.Column;
import com.huateng.frame.maven.generator.meta.Database;
import com.huateng.frame.maven.generator.meta.JoinColumn;
import com.huateng.frame.maven.generator.meta.Relationship;
import com.huateng.frame.maven.generator.meta.Table;

public class CDMImporter
{
	private File cdmSource;
	
	private Log logger;
	
	public CDMImporter(File cdmSource, Log logger)
	{
		this.cdmSource = cdmSource;
		this.logger = logger;
	}

	@SuppressWarnings("unchecked")
	protected Database doImport()
	{
		try
		{
			Database result = new Database();

			// 解析源文件
			SAXReader sar = new SAXReader();
			Document docSource = sar.read(cdmSource);
			
			//缓存所有DataItem
			Map<String, Column> dataitems = new TreeMap<String, Column>();
			for (Element nodeDataItem : (List<Element>) docSource.selectNodes("//c:DataItems/o:DataItem"))
			{
				Node nodeCode = nodeDataItem.selectSingleNode("a:Code");
				Node nodeName = nodeDataItem.selectSingleNode("a:Name");
				Node nodeDescription = nodeDataItem.selectSingleNode("a:Description");
				Node nodeLength = nodeDataItem.selectSingleNode("a:Length");
				Node nodeDataType = nodeDataItem.selectSingleNode("a:DataType");
				Node nodePrecision = nodeDataItem.selectSingleNode("a:Precision");
				Column col = new Column();
				if (nodeCode != null) col.setDbName(nodeCode.getText());
				if (nodeName != null) col.setTextName(nodeName.getText());
				if (nodeDescription != null) col.setDescription(nodeDescription.getText());
				if (nodeLength != null) col.setLength(Integer.parseInt(nodeLength.getText()));
				if (nodePrecision != null) col.setScale(Integer.parseInt(nodePrecision.getText()));
				if (nodeDataType != null) resolveColumnType(nodeDataType.getText(), col);

				dataitems.put(nodeDataItem.attributeValue("Id"), col);
				
			}

			// 取所有表
			Map<String, Table> tableIdMap = new HashMap<String, Table>();
			for (Element nodeTable : (List<Element>) docSource.selectNodes("//c:Entities/o:Entity"))
			{
				Table table = new Table();
				table.setDbName(nodeTable.selectSingleNode("a:Code").getText());
				tableIdMap.put(nodeTable.attributeValue("Id"), table);

				for (Element nodeColumn : (List<Element>) nodeTable.selectNodes("c:Attributes/o:EntityAttribute/c:DataItem/o:DataItem"))
				{
					//取DataItem
					String idref = nodeColumn.attributeValue("Ref");
					Column col = dataitems.get(idref);
					
					table.getColumns().add(col);
				}

				// 处理主键
				Element nodePrimaryKeyRef = (Element) nodeTable.selectSingleNode("c:PrimaryIdentifier/o:Identifier");
				if (nodePrimaryKeyRef == null)
				{
					logger.warn(MessageFormat.format("主键没找到[{0}], 跳过该表", table.getDbName()));
					continue;
				}
				String ref = nodePrimaryKeyRef.attributeValue("Ref");
				List<Element> pkColumns = (List<Element>) nodeTable.selectNodes("c:Identifiers/o:Identifier[@Id=\"" + ref + "\"]/c:Identifier.Attributes/o:EntityAttribute");
				for (Element pkColumn : pkColumns)
				{
					table.getPrimaryKeyColumns().add(dataitems.get(pkColumn.attributeValue("Ref")));
				}

				result.getTables().add(table);
			}
			// 处理关系
			List<Element> references = (List<Element>) docSource.selectNodes("//c:References/o:Reference");
			for (Element r : references)
			{
				Relationship rel = new Relationship();
				Element table1 = (Element) r.selectSingleNode("c:Object1/o:Table");
				Element table2 = (Element) r.selectSingleNode("c:Object2/o:Table");
				String id1 = table1.attributeValue("Ref");
				String id2 = table2.attributeValue("Ref");
				if (!tableIdMap.containsKey(id1) || !tableIdMap.containsKey(id2))
					continue;		//可能是因为没有主键而跳过的表
				rel.setParent(tableIdMap.get(id1));
				rel.setChild(tableIdMap.get(id2));
				for (Element rj : (List<Element>) r.selectNodes("c:Joins/o:ReferenceJoin"))
				{
					JoinColumn jc = new JoinColumn();
					Element col1 = (Element) rj.selectSingleNode("c:Object1/o:Column");
					Element col2 = (Element) rj.selectSingleNode("c:Object2/o:Column");
					id1 = col1.attributeValue("Ref");
					id2 = col2.attributeValue("Ref");
//					jc.setPk(columnIdMap.get(id1));
//					jc.setFk(columnIdMap.get(id2));

					rel.getJoinColumns().add(jc);
				}

				result.getRelationships().add(rel);
			}
			return result;
		}
		catch (Throwable t)
		{
			throw new IllegalArgumentException(t);
		}
	}

	private void resolveColumnType(String type, Column col)
	{
		FullyQualifiedJavaType fqjt = null;
		type = type.toUpperCase().trim();
		col.setDbType(type);
		if (type.startsWith("CHAR") || type.startsWith("VARCHAR"))
			fqjt = FullyQualifiedJavaType.getStringInstance();
		else if (type.startsWith("DECIMAL")||type.equals("NUMERIC"))
		{
			if (col.getScale() == 0)
			{
				// 整数
				if (col.getLength() <= 8)
					fqjt = new FullyQualifiedJavaType("java.lang.Integer");
				else if (col.getLength() <= 18)
					fqjt = new FullyQualifiedJavaType("java.lang.Long");
				else
					fqjt = new FullyQualifiedJavaType("java.math.BigDecimal");
			}
			else
			{
				fqjt = new FullyQualifiedJavaType("java.math.BigDecimal");
			}
		}
		else if (type.equals("INTEGER") || type.equals("INT") || type.equals("SMALLINT") || type.equals("TINYINT"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Integer");
		}
		else if (type.equals("BIGINT") || type.equals("LONG"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Long");
		}
		else if (type.equals("LONGTEXT") || type.equals("TEXT") || type.equals("LONG VARCHAR"))
		{
			col.setLob(true);
			fqjt = new FullyQualifiedJavaType("java.lang.String");
		}
		else if (type.equals("LONGBLOB") || type.equals("BLOB"))
		{
			col.setLob(true);
			fqjt = new FullyQualifiedJavaType("java.lang.Byte");
		}
		else if (type.equals("DATETIME") || type.equals("TIMESTAMP") || type.startsWith("DATE"))
		{
			fqjt = new FullyQualifiedJavaType("java.util.Date");
		}
		else if (type.equals("FLOAT"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Float");
		}
		else if (type.equals("NUMBERIC"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Float");
		}
		else if (type.equals("BIT"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Boolean");
		}
		else
		{
			System.err.println("未支持类型:" + type + "(" + col.getDbName() + ")");
			throw new IllegalArgumentException();
		}

		col.setJavaType(fqjt);
	}
}
