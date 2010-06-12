package com.huateng.sumer.shell.ibator;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import jline.ConsoleReader;

import com.huateng.sumer.shell.api.Handler;
import com.huateng.sumer.shell.support.ShellHelper;

public class Config implements Handler {

	@Override
	public void handle(ConsoleReader console, Properties params) throws Exception{
			Properties props = ShellHelper.getProjectInfo();
			
		Class.forName(props.getProperty("sumer.jdbc.driver"));
		Connection conn = DriverManager.getConnection(props.getProperty("sumer.jdbc.url"), props.getProperty("sumer.jdbc.user"), props.getProperty("sumer.jdbc.password"));
		try
		{
			File configFile = new File("ibator-config.xml");
			Document doc = ShellHelper.readDocument(configFile);
			
			DatabaseMetaData dmd = conn.getMetaData();
			ResultSet rs = dmd.getTables(null, null, null, null);
			boolean modified = false;
			while (rs.next())
			{
				//		<table tableName="tbl_adm_user" />
				String tableName = rs.getString("TABLE_NAME");
				console.printString("找到表["+tableName+"]");
				console.printNewline();
				Element element = DocumentHelper.createElement("table").addAttribute("tableName", tableName);

				modified |= ShellHelper.mergeDOM(doc, "/ibatorConfiguration/ibatorContext/table[@tableName='"+tableName+"']", element, "/ibatorConfiguration/ibatorContext");
			}
			rs.close();
			
			if (modified)
				ShellHelper.writeDocument(doc, configFile);
		}
		finally
		{
	      conn.close();
		}
	}
}
