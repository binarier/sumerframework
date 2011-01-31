package com.huateng.frame.maven.generator;

import java.io.File;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.ibatis.ibator.api.GeneratedJavaFile;
import org.apache.ibatis.ibator.api.dom.java.CompilationUnit;
import org.apache.ibatis.ibator.api.dom.java.Field;
import org.apache.ibatis.ibator.api.dom.java.FullyQualifiedJavaType;
import org.apache.ibatis.ibator.api.dom.java.JavaVisibility;
import org.apache.ibatis.ibator.api.dom.java.Method;
import org.apache.ibatis.ibator.api.dom.java.TopLevelClass;
import org.apache.ibatis.ibator.internal.util.JavaBeansUtil;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import com.huateng.frame.maven.generator.meta.Column;
import com.huateng.frame.maven.generator.meta.Database;
import com.huateng.frame.maven.generator.meta.JoinColumn;
import com.huateng.frame.maven.generator.meta.Relationship;
import com.huateng.frame.maven.generator.meta.Table;

/**
 * @author chenjun.li
 * 
 * @goal entity
 * 
 * @phase generate-sources
 */
public class EntityMojo extends AbstractMojo
{
	/**
	 * @parameter
	 * @required
	 */
	private String basePackage;
	/**
	 * @parameter default-value="target/base-frame-generated"
	 */
	private String outputDirectory;
	/**
	 * @parameter
	 * @required
	 */
	private File pdmSource;

	/**
	 * @parameter default-value=false
	 */
	private boolean trimStrings;
	
    /**
     * <i>Maven Internal</i>: Project to interact with.
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     * @noinspection UnusedDeclaration
     */
    private MavenProject project;


	public void execute() throws MojoExecutionException, MojoFailureException
	{
		try
		{
			project.addCompileSourceRoot(outputDirectory);
			Resource r = new Resource();
			ArrayList<String> in = new ArrayList<String>();
			in.add("**/client/**");
			in.add("**/model/**");
			r.setDirectory(outputDirectory);
			r.setIncludes(in);
			project.addResource(r);
			PowerDesignerImporter importer = new PowerDesignerImporter(pdmSource, getLog());
			Database db = importer.doImport();

			List<CompilationUnit> units = generateEntity(db);
			
			//调各插件
			//TODO使用配置方式调
			List<Generator> generators = new ArrayList<Generator>();
			generators.add(new UIDataSource(basePackage + ".client.ds"));
			generators.add(new ClientHome(basePackage + ".client.home"));
			generators.add(new ServerHome(basePackage + ".server.home"));
			for (Table table : db.getTables())
			{
				for (Generator gen : generators)
				{
					List<CompilationUnit> result = gen.generateAdditionalClasses(table, db);
					if (result != null)
						units.addAll(result);
				}
			}
			for (Generator gen : generators)
			{
				List<CompilationUnit> result = gen.generateAdditionalClasses(db);
				if (result != null)
					units.addAll(result);
			}
			for (CompilationUnit unit : units)
			{
				GeneratedJavaFile gjf = new GeneratedJavaFile(unit, outputDirectory);
				String targetFilename = StringUtils.replace(unit.getType().getFullyQualifiedName(), ".", "/") + ".java";
				targetFilename = FilenameUtils.concat(outputDirectory, targetFilename);
				FileUtils.forceMkdir(new File(FilenameUtils.getPath(targetFilename)));
				File targetFile = new File(targetFilename);
				FileUtils.writeStringToFile(targetFile, gjf.getFormattedContent(), "utf-8");
			}
		}
		catch (Exception e)
		{
			throw new MojoFailureException("生成过程出错", e);
		}
	}

	private TopLevelClass generateKeyClass(Table table)
	{
		// 复合主键
		TopLevelClass keyClass = new TopLevelClass(new FullyQualifiedJavaType(table.getJavaClass().getFullyQualifiedName() + "Key"));
		keyClass.setVisibility(JavaVisibility.PUBLIC);
		for (Column pc : table.getPrimaryKeyColumns())
		{
			GeneratorUtils.generateProperty(keyClass, pc.getJavaType(), pc.getPropertyName(), trimStrings);
		}

		// toString方法
		Method method = new Method();
		method.setName("toString");
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getStringInstance());
		StringBuilder sb = new StringBuilder();
		sb.append("return ");
		boolean first = true;
		for (Column pc : table.getPrimaryKeyColumns())
		{
			if (first)
				first = false;
			else
				sb.append(" + \"|\" + ");
			sb.append(MessageFormat.format("String.valueOf({0})", pc.getPropertyName()));
		}
		sb.append(";");
		method.addBodyLine(sb.toString());
		keyClass.addMethod(method);

		// 序列化
		keyClass.addSuperInterface(GeneratorUtils.forType(keyClass, Serializable.class.getCanonicalName()));

		return keyClass;
	}

	private List<CompilationUnit> generateEntity(Database db)
	{
		List<CompilationUnit> generatedFiles = new ArrayList<CompilationUnit>();

		Map<Table, TopLevelClass> generatingMap = new HashMap<Table, TopLevelClass>();

		for (Table table : db.getTables())
		{
			//计算表名及列名
			table.setJavaClass(new FullyQualifiedJavaType(basePackage + ".model." + JavaBeansUtil.getCamelCaseString(table.getDbName(), true)));

			for (Column col : table.getColumns())
				col.setPropertyName(JavaBeansUtil.getCamelCaseString(col.getDbName(), false));
			
			// 建立Entity类
			TopLevelClass entityClass = new TopLevelClass(table.getJavaClass());
			entityClass.setVisibility(JavaVisibility.PUBLIC);

			GeneratorUtils.forType(entityClass, "javax.persistence.Entity");
			entityClass.addAnnotation("@Entity");
			GeneratorUtils.forType(entityClass, "javax.persistence.Table");
			entityClass.addAnnotation("@Table(name=\"" + table.getDbName() + "\")");

			if (table.getPrimaryKeyColumns().size() > 1)
			{
				TopLevelClass keyClass = generateKeyClass(table);
				entityClass.addImportedType(keyClass.getType());
				entityClass.addImportedType(new FullyQualifiedJavaType("javax.persistence.IdClass"));
				entityClass.addAnnotation(MessageFormat.format("@IdClass({0}.class)", keyClass.getType().getShortName()));
				table.setJavaKeyClass(entityClass.getType());
				generatedFiles.add(keyClass);
			}
			else
			{
				table.setJavaKeyClass(table.getPrimaryKeyColumns().get(0).getJavaType());
			}

			for (Column col : table.getColumns())
			{
				// 建立Entity类里的属性
				Field f = GeneratorUtils.generateProperty(entityClass, col.getJavaType(), col.getPropertyName(), trimStrings);
				if (table.getPrimaryKeyColumns().contains(col))
				{
					entityClass.addImportedType(new FullyQualifiedJavaType("javax.persistence.Id"));
					f.addAnnotation("@Id");
				}
				
				if (col.getJavaType().getShortName().equals("Date"))
				{
					entityClass.addImportedType(new FullyQualifiedJavaType("javax.persistence.Temporal"));
					entityClass.addImportedType(new FullyQualifiedJavaType("javax.persistence.TemporalType"));
					f.addAnnotation("@Temporal(value=TemporalType."+col.getDbType()+")");
				}
				if (col.isLob())
				{
					entityClass.addImportedType(new FullyQualifiedJavaType("javax.persistence.Lob"));
					f.addAnnotation("@Lob");
				}
				entityClass.addImportedType(new FullyQualifiedJavaType("javax.persistence.Column"));
				f.addAnnotation(MessageFormat.format("@Column(name=\"{0}\")", col.getDbName()));
			}

			generatedFiles.add(entityClass);
			generatingMap.put(table, entityClass);
		}

		// 建立ManyToOne
		// @ManyToOne
		// @JoinColumn(name = "cdhd_usr_id", referencedColumnName =
		// "cdhd_usr_id")
		// 或
		// @JoinColumns({
		// @JoinColumn(name="a", referencedColumnName = "d"),
		// @JoinColumn(name="e", referencedColumnName = "f")
		// })
		// private TblUmsvcCdhdBasInf tblUmsvcCdhdBasInf;
		for (Relationship rel : db.getRelationships())
		{
			TopLevelClass parentClass = generatingMap.get(rel.getParent());
			TopLevelClass childClass = generatingMap.get(rel.getChild());
			Field f;

			// ManyToOne
			f = GeneratorUtils.generateProperty(childClass, parentClass.getType(), WordUtils.uncapitalize(parentClass.getType().getShortName()), false);
			if (rel.isOne2One())
			{
				childClass.addImportedType(new FullyQualifiedJavaType("javax.persistence.OneToOne"));
				f.addAnnotation("@OneToOne");
			}
			else
			{
				childClass.addImportedType(new FullyQualifiedJavaType("javax.persistence.ManyToOne"));
				f.addAnnotation("@ManyToOne");
			}
			childClass.addImportedType(new FullyQualifiedJavaType("javax.persistence.JoinColumns"));
			if (rel.getJoinColumns().size() > 1)
			{
				childClass.addImportedType(new FullyQualifiedJavaType("javax.persistence.JoinColumns"));
				f.addAnnotation("@JoinColumns({");

				Iterator<JoinColumn> iter = rel.getJoinColumns().iterator();
				while (iter.hasNext())
				{
					JoinColumn jc = iter.next();
					f.addAnnotation(MessageFormat.format("	@JoinColumn(name=\"{0}\", referencedColumnName = \"{1}\"){2}", jc.getFk().getDbName(), jc.getPk()
							.getDbName(), iter.hasNext() ? "," : ""));
				}
				f.addAnnotation("})");
			}
			else
			{
				JoinColumn jc = rel.getJoinColumns().get(0);
				f.addAnnotation(MessageFormat.format("@JoinColumn(name=\"{0}\", referencedColumnName = \"{1}\")", jc.getFk().getDbName(), jc.getPk()
						.getDbName()));
			}

			// 建立OneToMany
			// @OneToMany(mappedBy = "xxx", cascade = CascadeType.ALL)
			// private List<> xxx = new ArrayList<>();
			// 添加属性

			if (rel.isOne2One())
			{
				parentClass.addImportedType(new FullyQualifiedJavaType("javax.persistence.OneToOne"));
				f = GeneratorUtils.generateProperty(parentClass, childClass.getType(), WordUtils.uncapitalize(childClass.getType().getShortName()), false);
				f.addAnnotation(MessageFormat.format("@OneToOne(mappedBy = \"{0}\", optional = true, cascade = CascadeType.ALL)",
						WordUtils.uncapitalize(childClass.getType().getShortName())));
			}
			else
			{
				parentClass.addImportedType(new FullyQualifiedJavaType("javax.persistence.OneToMany"));
				FullyQualifiedJavaType fqjtArrayList = GeneratorUtils.forType(parentClass, "java.util.ArrayList");
				fqjtArrayList.addTypeArgument(childClass.getType());
				f = GeneratorUtils.generateProperty(childClass, fqjtArrayList, WordUtils.uncapitalize(childClass.getType().getShortName() + "s"), false);
				f.setInitializationString("new ArrayList<" + childClass.getType().getShortName() + ">()");
				f.addAnnotation(MessageFormat.format("@OneToMany(mappedBy = \"{0}\", cascade = CascadeType.ALL)",
						WordUtils.uncapitalize(childClass.getType().getShortName())));
			}
		}
		return generatedFiles;
	}

	public String getOutputDirectory()
	{
		return outputDirectory;
	}

	public void setOutputDirectory(String outputDirectory)
	{
		this.outputDirectory = outputDirectory;
	}

	public File getPdmSource()
	{
		return pdmSource;
	}

	public void setPdmSource(File pdmSource)
	{
		this.pdmSource = pdmSource;
	}

	public boolean isTrimStrings()
	{
		return trimStrings;
	}

	public void setTrimStrings(boolean trimStrings)
	{
		this.trimStrings = trimStrings;
	}

	public String getBasePackage()
	{
		return basePackage;
	}

	public void setBasePackage(String basePackage)
	{
		this.basePackage = basePackage;
	}

	public MavenProject getProject()
	{
		return project;
	}

	public void setProject(MavenProject project)
	{
		this.project = project;
	}
}
