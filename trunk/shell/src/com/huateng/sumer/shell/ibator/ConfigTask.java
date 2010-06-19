package com.huateng.sumer.shell.ibator;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.huateng.sumer.shell.support.ShellHelper;

public class ConfigTask extends Task {
	private String driver;
	private String url;
	private String user;
	private String password;
	
	@Override
	public void execute() throws BuildException {
		try
		{
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, password);
			File configFile = new File("ibator-config.xml");
			Document doc = ShellHelper.readDocument(configFile);
			
			DatabaseMetaData dmd = conn.getMetaData();
			ResultSet rs = dmd.getTables(null, null, null, null);
			boolean modified = false;
			while (rs.next())
			{
				//		<table tableName="tbl_adm_user" />
				String tableName = rs.getString("TABLE_NAME");
				System.out.println("找到表["+tableName+"]");
				Element element = DocumentHelper.createElement("table").addAttribute("tableName", tableName);

				modified |= ShellHelper.mergeDOM(doc, "/ibatorConfiguration/ibatorContext/table[@tableName='"+tableName+"']", element, "/ibatorConfiguration/ibatorContext");
			}
			rs.close();
			
			if (modified)
				ShellHelper.writeDocument(doc, configFile);
		}
		catch (Exception e)
		{
			throw new BuildException(e);
		}
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
