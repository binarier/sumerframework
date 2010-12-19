package com.huateng.frame.maven;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @goal field-definition
 * @phase generate-sources
 * @author chenjun.li
 */
public class FieldDefinitionMojo extends AbstractMojo {

	/**
	 * @parameter
	 */
	private File sourcePdm;
	
	/**
	 * @parameter
	 */
	private String charset = "gb18030";
	
	/**
	 * @parameter
	 */
	private File targetXml;
	
	/**
	 * @parameter
	 */
	private File configfile;
	
    @SuppressWarnings("unchecked")
	public void execute() throws MojoExecutionException, MojoFailureException {
        try
        {
            SAXReader sar = new SAXReader();
            
            //解析ibator配置文件，只生成出现在配置文件中的表，以减少生成量
            Document docConfig = sar.read(configfile);
            List<Element> configuredTables = docConfig.selectNodes("//table");
            Set<String> tableSet = new HashSet<String>();
            
            for (Element e : configuredTables)
            	tableSet.add(StringUtils.upperCase(e.attributeValue("tableName")));
            
            

            //解析pdm源文件
            Document docSource = sar.read(sourcePdm);

            //取所有表
			List<Node> lst = (List<Node>)docSource.selectNodes("//c:Tables/o:Table");
            Iterator<Node> iterTable = lst.iterator();
            
            QName qn = DocumentHelper.createQName("bean", new Namespace("", "http://www.springframework.org/schema/beans"));

            TreeMap<String, Element> tm = new TreeMap<String, Element>();
            while (iterTable.hasNext())
            {
                Node nodeTable = iterTable.next();
                String table = nodeTable.selectSingleNode("a:Code").getText().trim().toUpperCase();
                
                if (!tableSet.contains(StringUtils.upperCase(table)))
                	continue;
                
                if(!table.startsWith("TBL_"))continue;
                
                //如果在ibator配置文件中没有定义，就不要生成
                Iterator<Node> iterColumn = nodeTable.selectNodes("c:Columns/o:Column").iterator();
                table = WordUtils.capitalizeFully(table, new char[]{'_'});
                table = StringUtils.remove(table, "_");
                
                while (iterColumn.hasNext())
                {
                	
                    Node nodeColumn = iterColumn.next();
                    Node nodeCode = nodeColumn.selectSingleNode("a:Code");
                    Node nodeName = nodeColumn.selectSingleNode("a:Name");
                    Node nodeLength = nodeColumn.selectSingleNode("a:Length");
                    Node nodeType = nodeColumn.selectSingleNode("a:DataType");
                    Node nodeComment = nodeColumn.selectSingleNode("a:Comment");
                    String code = nodeCode.getText().trim();
                    String name = nodeName.getText().trim();
                    if (nodeType == null) {
                    	System.out.println(table + " : " + nodeCode);
                    	continue;
                    }
                    String type = nodeType.getText().trim();
                    
                    code = WordUtils.capitalizeFully(code, new char[]{'_'});
                    code = StringUtils.remove(code, "_");
                    
                    if (StringUtils.containsIgnoreCase(code, "exception"))	//排除会引起eclipse问题的字段
                    	continue;

                    int length = (nodeLength == null) ? 1 : Integer.valueOf(nodeLength.getText().trim());

                    String id = table + "_" + code;

                    //先放树里以排序
                    Element e = DocumentHelper.createElement(qn).addAttribute("parent", "fieldDefinition").addAttribute("id", id)
                            .addAttribute("p:field", WordUtils.uncapitalize(code))
                            .addAttribute("p:table", WordUtils.uncapitalize(table))
                            .addAttribute("p:name", name)
                            .addAttribute("p:size", Math.min(length,30)+"")
                            .addNamespace("", "http://www.springframework.org/schema/beans");
                    if (nodeComment != null && (StringUtils.containsIgnoreCase(nodeComment.getText(), "@@System@@")))
                    	e.addAttribute("p:systemModified", "true");

                    if (type.startsWith("DECIMAL"))
                    {
                        type = StringUtils.remove(type, "DECIMAL");
                        type = StringUtils.remove(type, '(');
                        type = StringUtils.remove(type, ')');

                        String n[] = StringUtils.split(type, ',');

                        switch (n.length)
                        {
                            case 1:
                                e.addAttribute("p:maxValue", StringUtils.repeat("9", Integer.valueOf(n[0].trim())));
                                break;
                            case 2:
                                String max;
                                int n1, n2;
                                n1 = Integer.valueOf(n[0].trim());
                                n2 = Integer.valueOf(n[1].trim());
                                max = StringUtils.repeat("9", n1 - n2);
                                if (n2 > 0)
                                {
	                                max += ".";
	                                length++;    //容纳小数位
	                                
	                                max += StringUtils.repeat("9", n2);

	                                if (n2 == 3)
	                                {
	                                	//金额字段只显示两位小数
	                                	length--;
	                                	n2--;
	                                }
	                                e.addAttribute("p:scale", String.valueOf(n2));
                                }
                                e.addAttribute("p:maxValue", max);
                                break;
                        }
                    }

                    e.addAttribute("p:maxLength", String.valueOf(length));

                    tm.put(id, e);
                }
            }
            //建立目标对象
            Document docTarget = DocumentHelper.createDocument();
            docTarget.setXMLEncoding(charset);
            Element rootTarget = docTarget.addElement("beans").addNamespace("", "http://www.springframework.org/schema/beans").addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance").addAttribute("xmlns:p", "http://www.springframework.org/schema/p").addAttribute("xsi:schemaLocation", "http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd");
            rootTarget.addComment("由FieldDefinitionMojo生成，禁止手工编辑");

            //添加bean定义
            for (Map.Entry<String, Element> entry : tm.entrySet())
            {
                rootTarget.add(entry.getValue());
            }
            //写目标文件
            Writer wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetXml), charset));
            
            OutputFormat of = new OutputFormat("\t", true, charset);
            of.setNewLineAfterDeclaration(true);
            of.setSuppressDeclaration(false);
            of.setExpandEmptyElements(false);
            XMLWriter xwr = new XMLWriter(wr, of);

            xwr.write(docTarget);
            xwr.close();
        } catch (Exception t)
        {
            throw new MojoExecutionException("执行出错", t);
        }
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public File getSourcePdm() {
		return sourcePdm;
	}

	public void setSourcePdm(File sourcePdm) {
		this.sourcePdm = sourcePdm;
	}

	public File getTargetXml() {
		return targetXml;
	}

	public void setTargetXml(File targetXml) {
		this.targetXml = targetXml;
	}

	public File getConfigfile() {
		return configfile;
	}

	public void setConfigfile(File configfile) {
		this.configfile = configfile;
	}

}
