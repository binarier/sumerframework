package com.huateng.sumer.shell.ant;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Properties;

import jline.ConsoleReader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Ant;

import com.huateng.sumer.shell.api.Handler;
import com.huateng.sumer.shell.configure.Command;
import com.huateng.sumer.shell.configure.CommandLoader;

/**
 * 
 * <p>定义一种命令行格式，并且进行相应解析</p>
 * <p><b>&lt;command&gt; [options] ...</b></p>
 * <p>&lt;taskdef name="parse" classpathref="shell.classpath" classname="com.huateng.sumer.tools.shell.ant.CommandParser"/&gt;</p>
 * <p>&lt;parse property="cmd" commandLine="${xxx}"/&gt;</p>
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
				
				if (StringUtils.isNotBlank(cmd.getAnt()))
				{
					File dir = new File(getProject().getBaseDir(), "shell");
					String antFilename = StringUtils.remove(WordUtils.capitalizeFully(cmd.getName()), ' ') + ".xml";
					File antFile = new File(dir, antFilename);
					
					FileUtils.forceMkdir(dir);
					
					//TODO 按参数来判断是否覆盖，开发时先默认覆盖
					System.out.println(cmd.getAnt());
					InputStream is = CommandProcessor.class.getClassLoader().getResourceAsStream(cmd.getAnt());
					OutputStream os = new FileOutputStream(antFile);
					IOUtils.copy(is, os);
					IOUtils.closeQuietly(is);
					IOUtils.closeQuietly(os);
					
					//执行调用
					Ant ant = new Ant();
					ant.setAntfile(antFile.getAbsolutePath());
					ant.setTarget(cmd.getTarget());
					ant.setProject(getProject());
					
					ant.execute();
				}
				
				if (cmd.isQuit())
					break;
			}
		}
		catch (Exception e)
		{
			throw new BuildException(e);
		}
	}
}
