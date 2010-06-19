package com.huateng.sumer.shell.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.huateng.sumer.shell.support.IvyHelper;

/**
 * 添加Ivy依赖项，对 {@link IvyHelper}的addDependency做简单包装
 * @author chenjun.li
 *
 */
public class AddDependency extends Task {
	
	private String org;
	private String name;
	private String conf;
	private String rev;
	
	@Override
	public void execute() throws BuildException {
		try
		{
			IvyHelper.addDependency(org, name, rev, conf);
		}
		catch(Exception e)
		{
			throw new BuildException(e);
		}
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConf() {
		return conf;
	}

	public void setConf(String conf) {
		this.conf = conf;
	}

	public String getRev() {
		return rev;
	}

	public void setRev(String rev) {
		this.rev = rev;
	}
}
