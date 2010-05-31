package com.huateng.sumer.tools.shell.ant;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * 
 * <p>定义一种命令行格式，并且进行相应解析</p>
 * <p><b>[command] [action] param1=value1 param2=value2 ...</b></p>
 * <p>这个Task使用时，用property属性指定解析结果存放的位置，比如：</p>
 * <p>&lt;taskdef name="parse" classpathref="shell.classpath" classname="com.huateng.sumer.tools.shell.ant.CommandParser"/&gt;</p>
 * <p>&lt;parse property="cmd" commandLine="${xxx}"/&gt;</p>
 * <p>这样，${cmd.command}就是[command]，${cmd.action}为[action], 参数也同样加上cmd前缀。</p>
 * 
 * @author chenjun.li
 *
 */
public class CommandParser extends Task {
	
	private String commandLine = "";
	
	private String property;

	@Override
	public void execute() throws BuildException {
		
		if (StringUtils.isBlank(property))
			throw new BuildException("必须指定property属性");
		
		String parts[] = StringUtils.split(StringUtils.trim(commandLine), " ");
		
		if (parts.length < 2)
			throw new BuildException("命令格式不正确");
		
		//command
		getProject().setUserProperty(property+".command", parts[0]);
		//action
		getProject().setUserProperty(property+".action", parts[1]);
		
		//参数
		for (int i = 2; i<parts.length; i++)
		{
			if (StringUtils.contains(parts[i], "="))
			{
				int index = StringUtils.indexOf(parts[i], "=");
				String param = StringUtils.left(parts[i], index);
				String value = StringUtils.substring(parts[i], index + 1);
				getProject().setUserProperty(property+"."+param, value);
			}
			else
			{
				getProject().setUserProperty(property+"."+parts[i], "true");
			}
		}
	}

	public String getCommandLine() {
		return commandLine;
	}

	public void setCommandLine(String commandLine) {
		this.commandLine = commandLine;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
}
