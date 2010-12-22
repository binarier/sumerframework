package com.huateng.frame.maven;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * @goal i18n-pdm
 * 
 * @phase generate-resources
 * 
 * @author chenjun.li
 */
public class I18nPowerDesignerMojo extends AbstractMojo
{
	/**
	 * @parameter
	 */
	private File pdmSource;

	/**
	 * @parameter
	 */
	private String targetPackage;

	/**
	 * @parameter default-value="src/main/resources"
	 */
	private String resourceDir;
	
	/**
	 * @parameter default-value="FieldTitleConstants"
	 */
	private String bundleBaseName;
	
	/**
	 * @parameter default-value="zh"
	 */
	private String locale;

	@SuppressWarnings("unchecked")
	public void execute() throws MojoExecutionException, MojoFailureException
	{
		OutputStreamWriter osw = null;
		try
		{
			if (StringUtils.isBlank(targetPackage))
				throw new MojoExecutionException("需要设置targetPackage");
			if (pdmSource == null)
				throw new MojoExecutionException("需要设置pdmSource");

			String file = MessageFormat.format("{0}/{1}/{2}_{3}.properties", resourceDir, StringUtils.replace(targetPackage, ".",	"/") ,bundleBaseName, locale);
			FileUtils.forceMkdir(new File(FilenameUtils.getPath(file)));
			osw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");

			osw.write("#由base-frame-plugin生成的字段基础国际化文件\n");
			SAXReader sar = new SAXReader();
			// 解析pdm源文件
			Document docSource = sar.read(pdmSource);
			// 取所有表
			for (Node nodeTable : (List<Node>) docSource.selectNodes("//c:Tables/o:Table"))
			{
                String table = nodeTable.selectSingleNode("a:Code").getText().trim();
                table = WordUtils.capitalizeFully(table, new char[]{'_'});
                table = StringUtils.remove(table, "_");

				for (Node nodeColumn : (List<Node>) nodeTable.selectNodes("c:Columns/o:Column"))
				{
					Node nodeCode = nodeColumn.selectSingleNode("a:Code");
					Node nodeName = nodeColumn.selectSingleNode("a:Name");
					String code = nodeCode.getText().trim();
					String name = nodeName.getText().trim();
					
					code = WordUtils.capitalizeFully(code, new char[]{ '_' });
					code = StringUtils.remove(code, "_");
					
					osw.write(MessageFormat.format("{0}={1}\n", table+"_"+code, name));
				}
			}
		}
		catch (Throwable t)
		{
			throw new MojoFailureException("执行出错", t);
		}
		finally
		{
			IOUtils.closeQuietly(osw);
		}
	}

	public File getPdmSource()
	{
		return pdmSource;
	}

	public void setPdmSource(File pdmSource)
	{
		this.pdmSource = pdmSource;
	}

	public String getTargetPackage()
	{
		return targetPackage;
	}

	public void setTargetPackage(String targetPackage)
	{
		this.targetPackage = targetPackage;
	}

	public String getResourceDir()
	{
		return resourceDir;
	}

	public void setResourceDir(String resourceDir)
	{
		this.resourceDir = resourceDir;
	}

	public String getBundleBaseName()
	{
		return bundleBaseName;
	}

	public void setBundleBaseName(String bundleBaseName)
	{
		this.bundleBaseName = bundleBaseName;
	}

}
