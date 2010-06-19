package com.huateng.sumer.shell.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.huateng.sumer.shell.support.IvyHelper;

/**
 * 添加Ivy配置项，对 {@link IvyHelper}的addDependency做简单包装
 * @author chenjun.li
 *
 */
public class AddConf extends Task {
	
	private String name;
	private String extend;
	
	@Override
	public void execute() throws BuildException {
		try
		{
			IvyHelper.addConf(name, extend);
		}
		catch(Exception e)
		{
			throw new BuildException(e);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}
}
