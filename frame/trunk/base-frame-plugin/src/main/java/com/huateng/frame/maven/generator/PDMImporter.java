package com.huateng.frame.maven.generator;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.maven.plugin.logging.Log;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.huateng.frame.maven.generator.meta.Column;
import com.huateng.frame.maven.generator.meta.Database;
import com.huateng.frame.maven.generator.meta.Domain;
import com.huateng.frame.maven.generator.meta.JoinColumn;
import com.huateng.frame.maven.generator.meta.Relationship;
import com.huateng.frame.maven.generator.meta.Table;

public class PDMImporter
{
	private File pdmSource;
	
	private Log logger;
	
	public PDMImporter(File pdmSource, Log logger)
	{
		this.pdmSource = pdmSource;
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
			Document docSource = sar.read(pdmSource);
			
			// 首先处理domain
			Map<String, Domain> domainMap = new HashMap<String, Domain>();
			for (Element nodeDomain : (List<Element>) docSource.selectNodes("//c:Domains/o:PhysicalDomain"))
			{
				Node nodeCode = nodeDomain.selectSingleNode("a:Code");
				Node nodeName = nodeDomain.selectSingleNode("a:Name");
				Node nodeLength = nodeDomain.selectSingleNode("a:Length");
				Node nodeDataType = nodeDomain.selectSingleNode("a:DataType");
				Node nodePrecision = nodeDomain.selectSingleNode("a:Precision");
				Node nodeValues = nodeDomain.selectSingleNode("a:ListOfValues");

				Domain domain = new Domain();
				int length = 0;
				int scale = 0;
				if (nodeCode != null) domain.setCode(nodeCode.getText());
				if (nodeName != null) domain.setName(nodeName.getText());
				if (nodeLength != null)
					length = Integer.parseInt(nodeLength.getText());
				if (nodePrecision != null)
					scale = Integer.parseInt(nodePrecision.getText());
				if (nodeDataType != null)
				{
					domain.setDbType(nodeDataType.getText());
					domain.setJavaType(resolveColumnType(domain.getDbType(), length, scale));
				}
				
				if (nodeValues != null)
				{
					String text = nodeValues.getText();
					if (StringUtils.isNotBlank(text))
					{
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						String lines[] = StringUtils.split(text, "\r\n");
						for (String line : lines)
						{
							if (StringUtils.isNotBlank(line))
							{
								String kv[] = StringUtils.split(line, "\t");
								if (kv.length != 2)
									throw new IllegalArgumentException("("+domain.getCode()+")无效键值对:"+line);
								map.put(kv[0], kv[1]);
							}
						}
						domain.setValueMap(map);
					}
				}
				domainMap.put(nodeDomain.attributeValue("Id"), domain);
				result.getDomains().add(domain);
			}

			// 取所有表
			Map<String, Column> columnIdMap = new HashMap<String, Column>();
			Map<String, Table> tableIdMap = new HashMap<String, Table>();
			for (Element nodeTable : (List<Element>) docSource.selectNodes("//c:Tables/o:Table"))
			{
				Table table = new Table();
				table.setDbName(nodeTable.selectSingleNode("a:Code").getText());
				tableIdMap.put(nodeTable.attributeValue("Id"), table);

				for (Element nodeColumn : (List<Element>) nodeTable.selectNodes("c:Columns/o:Column"))
				{
					Node nodeCode = nodeColumn.selectSingleNode("a:Code");
					Node nodeName = nodeColumn.selectSingleNode("a:Name");
					Node nodeDescription = nodeColumn.selectSingleNode("a:Description");
					Node nodeLength = nodeColumn.selectSingleNode("a:Length");
					Node nodeDataType = nodeColumn.selectSingleNode("a:DataType");
					Node nodePrecision = nodeColumn.selectSingleNode("a:Precision");
					Column col = new Column();
					if (nodeCode != null) col.setDbName(nodeCode.getText());
					if (nodeName != null) col.setTextName(nodeName.getText());
					if (nodeDescription != null) col.setDescription(nodeDescription.getText());
					if (nodeLength != null) col.setLength(Integer.parseInt(nodeLength.getText()));
					if (nodePrecision != null) col.setScale(Integer.parseInt(nodePrecision.getText()));
					if (nodeDataType != null)
					{
						col.setDbType(nodeDataType.getText());
						col.setLob(isLobType(col.getDbType()));
						col.setJavaType(resolveColumnType(col.getDbType(), col.getLength(), col.getScale()));
					}

					columnIdMap.put(nodeColumn.attributeValue("Id"), col);
					
					//处理domain
					Element nodeDomain = (Element) nodeColumn.selectSingleNode("c:Domain/o:PhysicalDomain");
					if (nodeDomain != null)
						col.setDomain(domainMap.get(nodeDomain.attributeValue("Ref")));

					table.getColumns().add(col);
				}

				// 处理主键
				Element nodePrimaryKeyRef = (Element) nodeTable.selectSingleNode("c:PrimaryKey/o:Key");
				if (nodePrimaryKeyRef == null)
				{
					logger.warn(MessageFormat.format("主键没找到[{0}], 跳过该表", table.getDbName()));
					continue;
				}
				String ref = nodePrimaryKeyRef.attribute("Ref").getText();
				List<Element> pkColumns = (List<Element>) nodeTable.selectNodes("c:Keys/o:Key[@Id=\"" + ref + "\"]/c:Key.Columns/o:Column");
				for (Element pkColumn : pkColumns)
				{
					table.getPrimaryKeyColumns().add(columnIdMap.get(pkColumn.attributeValue("Ref")));
				}
				
				result.getTables().add(table);
			}
			// 处理关系
			List<Element> references = (List<Element>) docSource.selectNodes("//c:References/o:Reference");
			for (Element r : references)
			{
				Relationship rel = new Relationship();
				Element table1 = (Element) r.selectSingleNode("c:ParentTable/o:Table");
				Element table2 = (Element) r.selectSingleNode("c:ChildTable/o:Table");
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
					jc.setPk(columnIdMap.get(id1));
					jc.setFk(columnIdMap.get(id2));

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

	private FullyQualifiedJavaType resolveColumnType(String dbType, int length, int scale)
	{
		FullyQualifiedJavaType fqjt = null;
		dbType = dbType.toUpperCase().trim();
		if (dbType.startsWith("CHAR") || dbType.startsWith("VARCHAR"))
			fqjt = FullyQualifiedJavaType.getStringInstance();
		else if (dbType.startsWith("DECIMAL")||dbType.equals("NUMERIC"))
		{
			if (scale == 0)
			{
				// 整数
				if (length <= 8)
					fqjt = new FullyQualifiedJavaType("java.lang.Integer");
				else if (length <= 18)
					fqjt = new FullyQualifiedJavaType("java.lang.Long");
				else
					fqjt = new FullyQualifiedJavaType("java.math.BigDecimal");
			}
			else
			{
				fqjt = new FullyQualifiedJavaType("java.math.BigDecimal");
			}
		}
		else if (dbType.equals("INTEGER") || dbType.equals("INT") || dbType.equals("SMALLINT") || dbType.equals("TINYINT"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Integer");
		}
		else if (dbType.equals("BIGINT") || dbType.equals("LONG"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Long");
		}
		else if (dbType.equals("LONGTEXT") || dbType.equals("TEXT") || dbType.equals("LONG VARCHAR"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.String");
		}
		else if (dbType.equals("LONGBLOB") || dbType.equals("BLOB"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Byte");
		}
		else if (dbType.equals("DATETIME") || dbType.equals("TIMESTAMP") || dbType.startsWith("DATE"))
		{
			fqjt = new FullyQualifiedJavaType("java.util.Date");
		}
		else if (dbType.equals("FLOAT"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Float");
		}
		else if (dbType.equals("NUMBERIC"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Float");
		}
		else if (dbType.equals("BIT"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Boolean");
		}
		else
		{
			System.err.println("未支持类型:" + dbType + "(" + dbType + ")");
			throw new IllegalArgumentException();
		}

		return fqjt;
	}
	
	private boolean isLobType(String dbType)
	{
		return dbType.equals("LONGTEXT") || dbType.equals("TEXT") || dbType.equals("LONG VARCHAR")||dbType.equals("LONGBLOB") || dbType.equals("BLOB");
	}
}
