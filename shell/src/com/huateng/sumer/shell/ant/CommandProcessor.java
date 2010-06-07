package com.huateng.sumer.shell.ant;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Properties;

import jline.ConsoleReader;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Ant;

import com.huateng.sumer.shell.api.Handler;
import com.huateng.sumer.shell.configure.Command;
import com.huateng.sumer.shell.configure.CommandLoader;

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
public class CommandProcessor extends Task {
	
	@Override
	public void execute() throws BuildException {
		try
		{
			ConsoleReader reader = new ConsoleReader(new FileInputStream(FileDescriptor.in),
	        		new PrintWriter(
	        				new OutputStreamWriter(new FileOutputStream(FileDescriptor.out),
	        						System.getProperty("jline.WindowsTerminal.output.encoding",System.getProperty("file.encoding")))));
			
			//开始命令循环
			while (true)
			{
				String line = reader.readLine("sumer>");
				
				if (StringUtils.isBlank(line))
					continue;
				
				Properties params = new Properties();		//用于接收参数
				Command cmd = CommandLoader.parse(line, params);
				if (cmd == null)
				{
					reader.printString("无效命令");
					reader.printNewline();
					continue;
				}
				
				if (StringUtils.isNotBlank(cmd.getHandler()))
				{
					Handler handler = (Handler)Class.forName(cmd.getHandler()).newInstance();
					handler.handle(reader, params);
				}
				
				if (cmd.isQuit())
					break;
			}
			
			
			Ant ant = new Ant();
			ant.setAntfile("build.xml");
			ant.setTarget("clean");
			ant.setProject(getProject());
			
			ant.execute();
		}
		catch (Exception e)
		{
			throw new BuildException(e);
		}
	}
}