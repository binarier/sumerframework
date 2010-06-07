package com.huateng.sumer.shell.support;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 提供在处理Shell时的公共功能，如依赖项管理等。
 * 出于简化设计考虑，可以规定所有的dependency项的conf都只配置一个conf对应，
 * 以便于查找重复，也提高ivy.xml的可读性。
 * @author chenjun.li
 *
 */
public class ShellHelper {

	public static void addDependency(String org, String name,String rev, String conf)
	{
		try
		{
			File ivyFile = new File("ivy.xml");
			SAXReader reader = new SAXReader();
			Document ivy = reader.read(ivyFile);
			//除去可能存在于conf中的"->"
			String parts [] = StringUtils.split(conf, "-");
			String pureConf = StringUtils.trim(parts[0]);
			
			//先确认conf存在
			if (ivy.selectSingleNode("/ivy-module/configurations/conf[@name='"+pureConf+"']") == null)
			{
				Element confs = (Element)ivy.selectSingleNode("/ivy-module/configurations");
				confs.addElement("conf").addAttribute("name", pureConf);
			}
			
			//再查找是否已经存在配置
			if (ivy.selectSingleNode("/ivy-module/dependencies/dependency[@org='"+org+"'" +
					" and @name='"+name+"'" +
					" and starts-with(@conf, '"+pureConf+"')]") == null)
			{
				Element dependencies = (Element)ivy.selectSingleNode("/ivy-module/dependencies");
				dependencies.addElement("dependency")
					.addAttribute("org", org)
					.addAttribute("name", name)
					.addAttribute("rev", rev)
					.addAttribute("conf", conf);
			}
			FileOutputStream fos = new FileOutputStream(ivyFile);
			XMLWriter xw = new XMLWriter(fos, OutputFormat.createPrettyPrint());
			xw.write(ivy);
			IOUtils.closeQuietly(fos);

			//由于DOM4j在属性上不认XMLWriter的setEscapeText设置，所以"->"都被转义成-&gt;了
			//为了增加可读性，重新把文件替换一下
			String content = new String(FileUtils.readFileToByteArray(ivyFile), "utf-8");
			content = StringUtils.replace(content, "-&gt;", "->");
			FileUtils.writeStringToFile(ivyFile, content, "utf-8");
			
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException(e);
		}
		
	}
}
