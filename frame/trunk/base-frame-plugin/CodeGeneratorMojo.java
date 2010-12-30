package com.huateng.frame.maven.hibernate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.ibatis.ibator.api.GeneratedFile;
import org.apache.ibatis.ibator.api.GeneratedJavaFile;
import org.apache.ibatis.ibator.api.ShellCallback;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.Parameter;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;
import org.apache.ibatis.ibator.internal.DefaultShellCallback;
import org.apache.ibatis.ibator.internal.util.JavaBeansUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;


/**
 * 代码生成工具
 * @author chenjun.li
 */
public class CodeGeneratorMojo extends AbstractMojo
{
	protected boolean trimStrings = true;
	protected ShellCallback shellCallback = new DefaultShellCallback(true);
	protected String daoPackage;
	protected String daoDir;
	protected String daoJarName;
	protected String charset = "gb18030";
	protected List<Pdm> source = new ArrayList<Pdm>();
	protected String entityPackage;
	protected String entityDir;
	protected String fieldsFile;
	protected String referredWebXml;
	
	protected final FullyQualifiedJavaType fqjtJPAUtils = new FullyQualifiedJavaType("com.cup.um.utils.jpa.JPAUtils");
	protected final FullyQualifiedJavaType fqjtAuditListener = new FullyQualifiedJavaType("com.cup.um.base.jpa.EntityAuditListener");
	protected final FullyQualifiedJavaType fqjtEntityManager = new FullyQualifiedJavaType("javax.persistence.EntityManager");
	protected final FullyQualifiedJavaType fqjtObject = new FullyQualifiedJavaType("java.lang.Object");
	protected final FullyQualifiedJavaType fqjtInteger = new FullyQualifiedJavaType("int");;
	protected final FullyQualifiedJavaType fqjtString = new FullyQualifiedJavaType("String");;
	protected final FullyQualifiedJavaType fqjtLong = new FullyQualifiedJavaType("long");;
	protected final FullyQualifiedJavaType fqjtBrowseService = new FullyQualifiedJavaType("com.cup.um.utils.jpa.BrowseService");
	protected final FullyQualifiedJavaType fqjtPrimaryKey = new FullyQualifiedJavaType("com.cup.um.utils.jpa.PrimaryKey");
	protected final FullyQualifiedJavaType fqjtList = new FullyQualifiedJavaType("java.util.List");
	protected final FullyQualifiedJavaType fqjtVoid = new FullyQualifiedJavaType("void");
	protected final FullyQualifiedJavaType fqjtBoolean = new FullyQualifiedJavaType("boolean");
	protected final FullyQualifiedJavaType fqjtEntity = new FullyQualifiedJavaType("javax.persistence.Entity");
	protected final FullyQualifiedJavaType fqjtTable = new FullyQualifiedJavaType("javax.persistence.Table");
	protected final FullyQualifiedJavaType fqjtColumn = new FullyQualifiedJavaType("javax.persistence.Column");
	protected final FullyQualifiedJavaType fqjtId = new FullyQualifiedJavaType("javax.persistence.Id");
	protected final FullyQualifiedJavaType fqjtIdClass = new FullyQualifiedJavaType("javax.persistence.IdClass");
	protected final FullyQualifiedJavaType fqjtLob = new FullyQualifiedJavaType("javax.persistence.Lob");
	protected final FullyQualifiedJavaType fqjtSerial = new FullyQualifiedJavaType("java.io.Serializable");
	protected final FullyQualifiedJavaType fqjtSecondaryTable = new FullyQualifiedJavaType("javax.persistence.SecondaryTable");
	protected final FullyQualifiedJavaType fqjtSecondaryTables = new FullyQualifiedJavaType("javax.persistence.SecondaryTables");
	protected final FullyQualifiedJavaType fqjtPrimaryKeyJoinColumn = new FullyQualifiedJavaType("javax.persistence.PrimaryKeyJoinColumn");
	protected final FullyQualifiedJavaType fqjtJoinColumn = new FullyQualifiedJavaType("javax.persistence.JoinColumn");
	protected final FullyQualifiedJavaType fqjtJoinColumns = new FullyQualifiedJavaType("javax.persistence.JoinColumns");
	protected final FullyQualifiedJavaType fqjtManyToOne = new FullyQualifiedJavaType("javax.persistence.ManyToOne");
	protected final FullyQualifiedJavaType fqjtOneToMany = new FullyQualifiedJavaType("javax.persistence.OneToMany");
	protected final FullyQualifiedJavaType fqjtOneToOne = new FullyQualifiedJavaType("javax.persistence.OneToOne");
	protected final FullyQualifiedJavaType fqjtEntityListeners = new FullyQualifiedJavaType("javax.persistence.EntityListeners");
	protected final FullyQualifiedJavaType fqjtArrayList = new FullyQualifiedJavaType("java.util.ArrayList");
	protected final FullyQualifiedJavaType fqjtCascadeType = new FullyQualifiedJavaType("javax.persistence.CascadeType");
	protected final FullyQualifiedJavaType fqjtVersion = new FullyQualifiedJavaType("javax.persistence.Version");
	protected final FullyQualifiedJavaType fqjtEqualsBuilder = new FullyQualifiedJavaType("org.apache.commons.lang.builder.EqualsBuilder");

	public void execute() throws MojoExecutionException, MojoFailureException
	{
		try {
			Database db = parsePdmSource();

			for (Table table : db.getTables())
			{
				if (entityDir != null)
					generateEntity(db, table);
			}
		}
		catch (Throwable t)
		{
			throw new MojoExecutionException("fail", t);
		}
	}

	private Database parsePdmSource() throws FileNotFoundException, DocumentException, UnsupportedEncodingException, MojoExecutionException
	{
		//结果对象
		Database result = new Database();
		
		for (Pdm s : source)
		{
			//解析源文件
			SAXReader sar = new SAXReader();
			Document docSource = sar.read(new FileInputStream(s.getFilename()));
			
			//取所有表
			for (Element nodeTable : (List<Element>)docSource.selectNodes("//c:Tables/o:Table"))
			{
				Table table = new Table();
				String tableCode = nodeTable.selectSingleNode("a:Code").getText();
				String tableClass = WordUtils.capitalizeFully(tableCode, new char[]{'_'});
				tableClass = StringUtils.remove(tableClass, "_");
				table.setCode(tableCode);
				table.setClazz(tableClass);
				table.setId(nodeTable.attributeValue("Id"));
	
				for (Element nodeColumn : (List<Element>)nodeTable.selectNodes("c:Columns/o:Column"))
				{
					Node nodeCode = nodeColumn.selectSingleNode("a:Code");
					Node nodeName = nodeColumn.selectSingleNode("a:Name");
					Node nodeDescription = nodeColumn.selectSingleNode("a:Description");
					Node nodeLength = nodeColumn.selectSingleNode("a:Length");
					Node nodeType = nodeColumn.selectSingleNode("a:DataType");
					String code = nodeCode.getText().trim();
					String name = nodeName.getText();
					String type = nodeType.getText().trim().toUpperCase();
					String id = nodeColumn.attributeValue("Id");
					int scale = 0;	//小数位
	
					//处理属性名
					String property = WordUtils.capitalizeFully(code, new char[]{'_'});
					property = StringUtils.remove(property, "_");
					property = WordUtils.uncapitalize(property);
					int length = (nodeLength == null)?-1:Integer.valueOf(nodeLength.getText().trim());
					//处理数值型字段,并确定小数位
					if (type.startsWith("DECIMAL"))
					{
						String tmp = StringUtils.remove(type, "DECIMAL");
						tmp = StringUtils.remove(tmp, '(');
						tmp = StringUtils.remove(tmp, ')');
						
						String n[] = StringUtils.split(tmp, ',');
						
						if (n.length == 2)
							scale = Integer.valueOf(n[1].trim());
					}
					
					Column col = new Column();
					col.setCode(code);
					col.setName(name);
					if (nodeDescription != null)
						col.setDescription(nodeDescription.getText());
					col.setLength(length);
					col.setType(type);
					col.setScale(scale);
					col.setProperty(property);
					col.setFqjt(resolveColumnType(col));
					col.setId(id);
					col.setTable(table);
	
					table.getColumns().add(col);
					table.getColumnMap().put(col.getId(), col);
					table.getColumnMapByCode().put(col.getCode(), col);
					result.getAllColumnsMap().put(col.getId(), col);
				}
				
				//处理主键
				Element nodePrimaryKeyRef = (Element)nodeTable.selectSingleNode("c:PrimaryKey/o:Key");
				if (nodePrimaryKeyRef == null)
					throw new MojoExecutionException("主键没找到:"+table.getCode());
				String ref = nodePrimaryKeyRef.attribute("Ref").getText();
				List<Element> pkColumns = (List<Element>)nodeTable.selectNodes("c:Keys/o:Key[@Id=\""+ref+"\"]/c:Key.Columns/o:Column");
				for (Element pkColumn : pkColumns)
				{
					table.getPrimaryKey().add(table.getColumnMap().get(pkColumn.attributeValue("Ref")));
				}
				
				//处理生成类名
				table.setFqjtImpl(new FullyQualifiedJavaType(daoPackage + "." + table.getClazz() + "DAOImpl"));
				table.setFqjtInterface(new FullyQualifiedJavaType(daoPackage + "." + table.getClazz() + "DAO"));
				table.setFqjtEntity(new FullyQualifiedJavaType(entityPackage + "." + table.getClazz()));
				table.setFqjtExample(new FullyQualifiedJavaType(entityPackage + "." + table.getClazz()+"Example"));
				if (pkColumns.size()>1)
					table.setFqjtPrimaryKey(new FullyQualifiedJavaType(entityPackage + "." + table.getClazz()+"PK"));
				table.setPersistenceUnit(s.getPersistenceUnit());

				result.getTables().add(table);
				result.getTableMap().put(table.getId(), table);
				result.getTableMapByCode().put(table.getCode(), table);
			}
			//处理关系
			List<Element> references = (List<Element>)docSource.selectNodes("//c:References/o:Reference");
			for (Element r : references)
			{
				Relationship rel = new Relationship();
				Element table1 = (Element)r.selectSingleNode("c:Object1/o:Table");
				Element table2 = (Element)r.selectSingleNode("c:Object2/o:Table");
				String id1 = table1.attributeValue("Ref");
				String id2 = table2.attributeValue("Ref");
				rel.setParent(result.getTableMap().get(id1));
				rel.setChild(result.getTableMap().get(id2));
				for (Element rj : (List<Element>)r.selectNodes("c:Joins/o:ReferenceJoin"))
				{
					JoinColumn jc = new JoinColumn();
					Element col1 = (Element)rj.selectSingleNode("c:Object1/o:Column");
					Element col2 = (Element)rj.selectSingleNode("c:Object2/o:Column");
					id1 = col1.attributeValue("Ref");
					id2 = col2.attributeValue("Ref");
					jc.setPk(result.getAllColumnsMap().get(id1));
					jc.setFk(result.getAllColumnsMap().get(id2));
					
					rel.getJoinColumns().add(jc);
				}
				
				result.getRelationships().add(rel);

				//添加parent映射
				Map<Table, List<Relationship>> map = result.getRelMapByParentTable();
				if (map.get(rel.getParent()) == null)
					map.put(rel.getParent(), new ArrayList<Relationship>());
				map.get(rel.getParent()).add(rel);

				//添加child映射
				map = result.getRelMapByChildTable();
				if (map.get(rel.getChild()) == null)
					map.put(rel.getChild(), new ArrayList<Relationship>());
				map.get(rel.getChild()).add(rel);
			}
		}
		return result;
	}
	private void generateEntity(Database db, Table table) throws Exception
	{
		// 建立Entity类
		Method method;
		StringBuilder sb;
		boolean first;
		TopLevelClass entityClass = new TopLevelClass(table.getFqjtEntity());
		entityClass.setVisibility(JavaVisibility.PUBLIC);
		entityClass.addImportedType(fqjtEntity);
		entityClass.addImportedType(fqjtTable);
		entityClass.addImportedType(fqjtColumn);
		entityClass.addImportedType(fqjtId);
		entityClass.addAnnotation("@Entity");
		entityClass.addAnnotation("@Table(name=\""+table.getCode()+"\")");
		
		//引入主键接口
		entityClass.addImportedType(fqjtPrimaryKey);
		entityClass.addSuperInterface(fqjtPrimaryKey);
		
		if (table.getFqjtPrimaryKey() != null)
		{
			//复合主键
			TopLevelClass keyClass = new TopLevelClass(table.getFqjtPrimaryKey());
			keyClass.setVisibility(JavaVisibility.PUBLIC);
			for (Column pc : table.getPrimaryKey())
			{
				generateColumn(keyClass, table, pc, false, true);
			}
			
			//toString方法
			method = new Method();
			method.setName("toString");
			method.setVisibility(JavaVisibility.PUBLIC);
			method.setReturnType(fqjtString);
			sb = new StringBuilder();
			sb.append("return ");
			first = true;
			for (Column pc : table.getPrimaryKey())
			{
				if (first)
					first = false;
				else
					sb.append(" + \"|\" + ");
				sb.append(MessageFormat.format("String.valueOf({0})", pc.getProperty()));
			}
			sb.append(";");
			method.addBodyLine(sb.toString());
			keyClass.addMethod(method);

			generateEqualsHash(keyClass, table.getPrimaryKey());

			//构造函数
			generateConstructor(keyClass, table.getPrimaryKey());

			//序列化
			makeSerializable(keyClass);

			//生成主键类
			generateFile(new GeneratedJavaFile(keyClass, entityDir));
			
			//引入主键类
			entityClass.addImportedType(fqjtIdClass);
			entityClass.addAnnotation("@IdClass("+table.getFqjtPrimaryKey().getShortName()+".class)");
			
			//生成getPrimaryKey方法
			method = new Method();
			method.setName("getPrimaryKey");
			method.setVisibility(JavaVisibility.PUBLIC);
			method.setReturnType(fqjtObject);

			sb = new StringBuilder();
			sb.append("return new "+table.getFqjtPrimaryKey().getShortName()+"(");
			first = true;
			for (Column pc : table.getPrimaryKey())
			{
				if (first)
					first = false;
				else
					sb.append(", ");
				sb.append(pc.getProperty());
			}
			sb.append(");");
			method.addBodyLine(sb.toString());
			entityClass.addMethod(method);
		}
		else
		{
			//生成单主键的getPrimaryKey方法
			method = new Method();
			method.setName("getPrimaryKey");
			method.setVisibility(JavaVisibility.PUBLIC);
			method.setReturnType(fqjtObject);
			
			method.addBodyLine("return "+table.getPrimaryKey().get(0).getProperty()+";");
			entityClass.addMethod(method);
		}

		for (Column col : table.getColumns()) {
			// 建立Entity类里的属性
			generateColumn(entityClass, table, col, table.getPrimaryKey().contains(col), false);
		}
		
		//建立ManyToOne
		//	    @ManyToOne
		//	    @JoinColumn(name = "cdhd_usr_id", referencedColumnName = "cdhd_usr_id")
		//		或
		//		@JoinColumns({
		//			@JoinColumn(name="a", referencedColumnName = "d"),
		//			@JoinColumn(name="e", referencedColumnName = "f")
		//		})
		//	    private TblUmsvcCdhdBasInf tblUmsvcCdhdBasInf;
		List<Relationship> rels = db.getRelMapByChildTable().get(table);
		if (rels != null)
		{
			entityClass.addImportedType(fqjtJoinColumn);
			for (Relationship rel : rels)
			{
				Field f;
				
				//添加属性
				f = generateProperty(entityClass, rel.getParent().getFqjtEntity(), WordUtils.uncapitalize(rel.getParent().getFqjtEntity().getShortName()));
				if (rel.isOne2One())
				{
					entityClass.addImportedType(fqjtOneToOne);
					f.addAnnotation("@OneToOne(optional = false)");
				}
				else
				{
					entityClass.addImportedType(fqjtManyToOne);
					f.addAnnotation("@ManyToOne");
				}
				if (rel.getJoinColumns().size() > 1)
				{
					entityClass.addImportedType(fqjtJoinColumns);
					f.addAnnotation("@JoinColumns({");

					Iterator<JoinColumn> iter = rel.getJoinColumns().iterator();
					while (iter.hasNext())
					{
						JoinColumn jc = iter.next();
						f.addAnnotation(MessageFormat.format("	@JoinColumn(name=\"{0}\", referencedColumnName = \"{1}\"){2}", jc.getFk().getCode(), jc.getPk().getCode(), iter.hasNext()?",":""));
					}
					f.addAnnotation("})");
				}
				else
				{
					JoinColumn jc = rel.getJoinColumns().get(0);
					f.addAnnotation(MessageFormat.format("@JoinColumn(name=\"{0}\", referencedColumnName = \"{1}\")", jc.getFk().getCode(), jc.getPk().getCode()));
				}
			}
		}
		
		//建立OneToMany
		//	    @OneToMany(mappedBy = "xxx", cascade = CascadeType.ALL)
		//	    @JoinColumn(name = "a", referencedColumnName = "b")
		//	    private List<> xxx = new ArrayList<>();
		rels = db.getRelMapByParentTable().get(table);
		if (rels != null)
		{
			for (Relationship rel : rels)
			{
				//添加属性
				Field f;
				
				entityClass.addImportedType(fqjtCascadeType);

				if (rel.isOne2One())
				{
					entityClass.addImportedType(fqjtOneToOne);
					f = generateProperty(entityClass, rel.getChild().getFqjtEntity(), WordUtils.uncapitalize(rel.getChild().getFqjtEntity().getShortName()));
					f.addAnnotation(MessageFormat.format("@OneToOne(mappedBy = \"{0}\", optional = true, cascade = CascadeType.ALL)", WordUtils.uncapitalize(table.getFqjtEntity().getShortName()))); 
				}
				else
				{
					entityClass.addImportedType(fqjtOneToMany);
					entityClass.addImportedType(fqjtArrayList);
					FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(fqjtList.getFullyQualifiedName());
					fqjt.addTypeArgument(rel.getChild().getFqjtEntity());
					f = generateProperty(entityClass, fqjt, WordUtils.uncapitalize(rel.getChild().getFqjtEntity().getShortName()+"s"));
					f.setInitializationString("new ArrayList<"+rel.getChild().getFqjtEntity().getShortName()+">()");
					f.addAnnotation(MessageFormat.format("@OneToMany(mappedBy = \"{0}\", cascade = CascadeType.ALL)", WordUtils.uncapitalize(table.getFqjtEntity().getShortName())));
				}
			}
		}		
		
		//构造函数
		generateConstructor(entityClass, table.getColumns());
		
		//序列化
		makeSerializable(entityClass);
		
		//trim函数
		method = new Method();
		method.setName("trimStringValues");
		method.setVisibility(JavaVisibility.PUBLIC);
		for (Column col : table.getColumns())
		{
			if (!table.getPrimaryKey().contains(col))
			{
				if (col.getFqjt().equals(fqjtString))
				{
					method.addBodyLine(MessageFormat.format("if ({0} != null) {0} = {0}.trim();", col.getProperty()));
				}
			}
		}
		method.addBodyLine("return;");
		entityClass.addMethod(method);

		// 生成Entity类文件
		generateFile(new GeneratedJavaFile(entityClass, entityDir));
		
	}

	private void generateConstructor(TopLevelClass clazz, List<Column> cols) {
		Method method;
		
		//默认值函数
		method = new Method();
		method.setName("fillDefaultValues");
		method.setVisibility(JavaVisibility.PUBLIC);
		for (Column col : cols)
		{
			if (!col.isGeneratedValue()&&!col.getCode().equalsIgnoreCase("ver_no"))
			{
				String shortName = col.getFqjt().getShortName();
				if (shortName.equals("String"))
					method.addBodyLine(MessageFormat.format("if ({0} == null) {0} = \"\";", col.getProperty()));
				else if (shortName.equals("Integer"))
					method.addBodyLine(MessageFormat.format("if ({0} == null) {0} = 0;", col.getProperty()));
				else if (shortName.equals("BigDecimal"))
					method.addBodyLine(MessageFormat.format("if ({0} == null) {0} = new BigDecimal(0);", col.getProperty()));
				else if (shortName.equals("Long"))
					method.addBodyLine(MessageFormat.format("if ({0} == null) {0} = 0l;", col.getProperty()));
				else if (shortName.equals("Date"))
					method.addBodyLine(MessageFormat.format("if ({0} == null) {0} = new Date();", col.getProperty()));
			}
		}
		clazz.addMethod(method);
		
		//空构造函数
		method = new Method();
		method.setName(clazz.getType().getShortName());
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setConstructor(true);
		method.addBodyLine("");
		clazz.addMethod(method);

		//指定值
		method = new Method();
		method.setName(clazz.getType().getShortName());
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setConstructor(true);
		for (Column col : cols)
		{
			method.addParameter(new Parameter(col.getFqjt(), col.getProperty()));
			method.addBodyLine(MessageFormat.format("this.{0} = {0};", col.getProperty()));
		}
		clazz.addMethod(method);
	}
	
	private FullyQualifiedJavaType resolveColumnType(Column col)
	{
		FullyQualifiedJavaType fqjt = null;
		if (col.getType().startsWith("CHAR")||col.getType().startsWith("VARCHAR"))
		{
			fqjt = fqjtString;
		}
		else if (col.getType().startsWith("DECIMAL"))
		{
			if (col.getScale() == 0)
			{
				//整数
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
		else if (col.getType().equals("INTEGER")||col.getType().equals("INT")||col.getType().equals("SMALLINT")||col.getType().equals("TINYINT"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Integer");
		}
		else if (col.getType().equals("BIGINT")||col.getType().equals("LONG"))
		{
			fqjt = new FullyQualifiedJavaType("java.lang.Long");
		}
		else if (col.getType().equals("LONGTEXT")||col.getType().equals("TEXT")||col.getType().equals("LONG VARCHAR"))
		{
			col.setLob(true);
			fqjt = new FullyQualifiedJavaType("java.lang.String");
		}
		else if (col.getType().equals("LONGBLOB")||col.getType().equals("BLOB"))
		{
			col.setLob(true);
			fqjt = new FullyQualifiedJavaType("java.lang.Byte");
		}
		else if (col.getType().equals("DATETIME")||col.getType().equals("TIMESTAMP")||col.getType().startsWith("DATE"))
		{
			fqjt = new FullyQualifiedJavaType("java.util.Date");
		}
		else
		{
			throw new IllegalArgumentException("未支持类型:"+col.getType()+"("+col.getCode()+")");
		}
		return fqjt;
	}

	private Field generateProperty(TopLevelClass clazz, FullyQualifiedJavaType fqjt, String property)
	{
		clazz.addImportedType(fqjt);

		Field field = new Field();
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(fqjt);
		field.setName(property);
		clazz.addField(field);

		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(fqjt);
		method.setName(JavaBeansUtil.getGetterMethodName(field.getName(), field.getType()));
		StringBuilder sb = new StringBuilder();
		sb.append("return ");
		sb.append(property);
		sb.append(';');
		method.addBodyLine(sb.toString());
		clazz.addMethod(method);

		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName(JavaBeansUtil.getSetterMethodName(property));
		method.addParameter(new Parameter(fqjt, property));

		if (trimStrings && fqjt.equals(FullyQualifiedJavaType.getStringInstance()))
		{
			sb.setLength(0);
			sb.append("this."); //$NON-NLS-1$
			sb.append(property);
			sb.append(" = "); //$NON-NLS-1$
			sb.append(property);
			sb.append(" == null ? null : "); //$NON-NLS-1$
			sb.append(property);
			sb.append(".trim();"); //$NON-NLS-1$
			method.addBodyLine(sb.toString());
		}
		else
		{
			sb.setLength(0);
			sb.append("this."); //$NON-NLS-1$
			sb.append(property);
			sb.append(" = "); //$NON-NLS-1$
			sb.append(property);
			sb.append(';');
			method.addBodyLine(sb.toString());
		}

		clazz.addMethod(method);
		
		return field;
	}

	private void generateColumn(TopLevelClass clazz, Table table, Column col, boolean idColumn, boolean keyColumn) {

		Field field = generateProperty(clazz, col.getFqjt(), col.getProperty());
		boolean joined = (col.getTable() != table)&&(!StringUtils.startsWithIgnoreCase(table.getCode(),"viw"));

		if (idColumn)
			field.addAnnotation("@Id");
		if (!keyColumn)
		{
			if (!joined)
				field.addAnnotation(MessageFormat.format("@Column(name=\"{0}\")", col.getCode()));
			else
				field.addAnnotation(MessageFormat.format("@Column(name=\"{0}\", table=\"{1}\")", col.getCode(), col.getTable().getCode()));
			if (col.getFqjt().getShortName().equals("Date"))
			{
				clazz.addImportedType(new FullyQualifiedJavaType("javax.persistence.Temporal"));
				clazz.addImportedType(new FullyQualifiedJavaType("javax.persistence.TemporalType"));
				field.addAnnotation("@Temporal(value=TemporalType."+col.getType()+")");
			}
			if (col.isLob())
			{
				clazz.addImportedType(fqjtLob);
				field.addAnnotation("@Lob");
			}
			if (col.getCode().equalsIgnoreCase("ver_no")&&!joined)
			{
				clazz.addImportedType(fqjtVersion);
				field.addAnnotation("@Version");
			}
		}
	}


	private void generateFile(GeneratedFile gjf) throws Exception
	{
		File dir = shellCallback.getDirectory(gjf.getTargetProject(), gjf.getTargetPackage());
		File file = new File(dir, gjf.getFileName());

		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "gb18030");
		osw.write(gjf.getFormattedContent());
		
		osw.close();
		fos.close();
	}

	private void generateEqualsHash(TopLevelClass clazz, List<Column> columns)
	{
		Method method;

//		@Override
//		 public boolean equals(Object o) {
//			   if (!(o instanceof MyClass)) {
//			     return false;
//			   }
//			   if (this == o) {
//			     return true;
//			   }
//			   MyClass rhs = (MyClass) obj;
//			   return new EqualsBuilder()
//			                 .appendSuper(super.equals(obj))
//			                 .append(field1, rhs.field1)
//			                 .append(field2, rhs.field2)
//			                 .append(field3, rhs.field3)
//			                 .isEquals();
//			  }
		
		clazz.addImportedType(fqjtEqualsBuilder);

		method = new Method();
		method.setName("equals");
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(fqjtBoolean);
		method.addAnnotation("@Override");
		method.addParameter(new Parameter(fqjtObject, "o"));
		method.addBodyLine("if (o == this) return true;");
		method.addBodyLine(MessageFormat.format("if (!(o instanceof {0})) return false;", clazz.getType().getShortName()));
		
		method.addBodyLine(MessageFormat.format("{0} other = ({0}) o;", clazz.getType().getShortName()));
		
		method.addBodyLine("return new EqualsBuilder().appendSuper(super.equals(other))");
		for (Column col : columns)
			method.addBodyLine(MessageFormat.format(".append({0}, other.{0})", col.getProperty()));
		method.addBodyLine(".isEquals();");
		clazz.addMethod(method);
		
//		@Override
//		public int hashCode() {
//			final int prime = 31;
//			int hash = 17;
//			hash = hash * prime + this.projectId;
//			hash = hash * prime + this.category.hashCode();
//			return hash;
//		}
		method = new Method();
		method.setName("hashCode");
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(fqjtInteger);
		method.addAnnotation("@Override");
		method.addBodyLine("final int prime = 31;");
		method.addBodyLine("int hash = 17;");
		
		for (Column col : columns)
			method.addBodyLine(MessageFormat.format("hash = hash * prime + this.{0}.hashCode();", col.getProperty()));

		method.addBodyLine("return hash;");
		clazz.addMethod(method);
	}
	
	private void makeSerializable(TopLevelClass clazz)
	{
		//序列化
		//暂时使用1L，理论上需要改为所有字段定义的hash值
		//private static final long serialVersionUID = 1L;
		clazz.addImportedType(fqjtSerial);
		clazz.addSuperInterface(fqjtSerial);

		Field f;
		f = new Field();
		f.setVisibility(JavaVisibility.PRIVATE);
		f.setStatic(true);
		f.setFinal(true);
		f.setType(fqjtLong);
		f.setName("serialVersionUID");
		f.setInitializationString("1L");
		
		clazz.addField(f);
	}
	
	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void addSource(Pdm source) {
		this.source.add(source);
	}

	public String getDaoPackage() {
		return daoPackage;
	}

	public void setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
	}

	public String getDaoDir() {
		return daoDir;
	}

	public void setDaoDir(String daoDir) {
		this.daoDir = daoDir;
	}

	public boolean isTrimStrings() {
		return trimStrings;
	}

	public void setTrimStrings(boolean trimStrings) {
		this.trimStrings = trimStrings;
	}

	public String getEntityPackage() {
		return entityPackage;
	}

	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}

	public String getEntityDir() {
		return entityDir;
	}

	public void setEntityDir(String entityDir) {
		this.entityDir = entityDir;
	}

	public String getFieldsFile() {
		return fieldsFile;
	}

	public void setFieldsFile(String fieldsFile) {
		this.fieldsFile = fieldsFile;
	}

	public String getReferredWebXml() {
		return referredWebXml;
	}

	public void setReferredWebXml(String referredWebXml) {
		this.referredWebXml = referredWebXml;
	}

	public String getDaoJarName() {
		return daoJarName;
	}

	public void setDaoJarName(String daoJarName) {
		this.daoJarName = daoJarName;
	}

}
