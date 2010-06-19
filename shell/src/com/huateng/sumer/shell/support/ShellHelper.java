package com.huateng.sumer.shell.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.util.NodeComparator;

/**
 * 提供在处理Shell时的公共功能，如依赖项管理等。
 * @author chenjun.li
 *
 */
public class ShellHelper {

	public static Properties getProjectInfo()
	{
		try
		{
			Properties props = new Properties();
			FileInputStream fis = new FileInputStream("project-info.properties");
			
			props.load(fis);
			IOUtils.closeQuietly(fis);
			
			return props;
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException("读取project-info.properties出错", e);
		}
	}
	
	/**
	 * 将一个结点写入对应的Document，并且确定是否修改了文档
	 * @param document
	 * @param xpath
	 * @param element
	 * @param parentXPath
	 * @return 是否修改了文档对象
	 */
	public static boolean mergeDOM(Document document, String xpath, Element element, String parentXPath)
	{
		Element parent = (Element)document.selectSingleNode(parentXPath);
		Element node = (Element)document.selectSingleNode(xpath);
		if (node != null)
		{
			//确认是否完全一样
			NodeComparator nc = new NodeComparator();
			if (nc.compare(node, element) == 0)
				return false;
			parent.remove(node);
		}
			
		parent.add(element);
		
		return true;
	}
	
	public static Document readDocument(File file) throws DocumentException
	{
		SAXReader reader = new SAXReader();
		return reader.read(file);
	}
	
	public static void writeDocument(Document doc, File file) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(file);
		OutputFormat of = OutputFormat.createPrettyPrint();
		of.setIndent("	");
		XMLWriter xw = new XMLWriter(fos, OutputFormat.createPrettyPrint());
		xw.write(doc);
		IOUtils.closeQuietly(fos);

		//由于DOM4j在属性上不认XMLWriter的setEscapeText设置，所以"->"都被转义成-&gt;了
		//为了增加可读性，重新把文件替换一下
		String content = new String(FileUtils.readFileToByteArray(file), "utf-8");
		content = StringUtils.replace(content, "-&gt;", "->");
		FileUtils.writeStringToFile(file, content, "utf-8");
		
		System.out.println("写入" + file.getCanonicalPath() + " ...");
	}
	
}
