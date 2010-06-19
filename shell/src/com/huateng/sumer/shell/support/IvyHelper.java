package com.huateng.sumer.shell.support;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 用于辅助管理Ivy.xml
 * @author chenjun.li
 *
 */
public class IvyHelper {
	
	private static File ivyFile = new File("ivy.xml");
	
	public static void addConf(String name, String extend) throws DocumentException, IOException
	{
		Document ivyDoc = ShellHelper.readDocument(ivyFile);
		String parts [] = StringUtils.split(name, "-");
		String pureConf = StringUtils.trim(parts[0]);
		
		Element element = DocumentHelper.createElement("conf")
							.addAttribute("name", pureConf);
		if (StringUtils.isNotBlank(extend))
			element.addAttribute("extends", extend);
		
		if (ShellHelper.mergeDOM(ivyDoc, 
				"/ivy-module/configurations/conf[@name='"+pureConf+"']", 
				element,
				"/ivy-module/configurations"))
		{
			ShellHelper.writeDocument(ivyDoc, ivyFile);
		}
	}
	
	/**
	 * 添加依赖项。
	 * 目前查找重复的方案比较简单，只查找conf中第一个name符合的。
	 * 有待改进
	 * @param org
	 * @param name
	 * @param rev
	 * @param conf
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static void addDependency(String org, String name,String rev, String conf) throws DocumentException, IOException
	{
		Document ivyDoc = ShellHelper.readDocument(ivyFile);
		String parts [] = StringUtils.split(conf, "-");
		String pureConf = StringUtils.trim(parts[0]);
		
		Element element = DocumentHelper.createElement("dependency")
					.addAttribute("conf", conf)
					.addAttribute("org", org)
					.addAttribute("name", name)
					.addAttribute("rev", rev);
		
		if (ShellHelper.mergeDOM(
				ivyDoc, 
				"/ivy-module/dependencies/dependency[@org='"+org+"'" + " and @name='"+name+"'" + " and starts-with(@conf, '"+pureConf+"')]",
				element,
				"/ivy-module/dependencies"))
		{
			ShellHelper.writeDocument(ivyDoc, ivyFile);
		}
	}
}
