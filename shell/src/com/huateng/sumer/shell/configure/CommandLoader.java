package com.huateng.sumer.shell.configure;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;

import com.huateng.sumer.shell.ant.CommandProcessor;

/**
 * 用于提供静态的从classloader中获取、解析并缓存各jar包中的命令配置
 * @author chenjun.li
 *
 */
@SuppressWarnings("unchecked")
public class CommandLoader {

	private static Map<String, Object> commandTreeRoot;
	private static List<Command> commands;

	public static Map<String, Object> getCommandTree()
	{
		if (commandTreeRoot == null)
			loadTree();
		return commandTreeRoot;
	}
	
	private static void loadTree()
	{
		//在启动时解析并缓存命令列表
		try
		{
			
			Enumeration<URL> enumUrl = CommandProcessor.class.getClassLoader().getResources("META-INF/sumer.shell.xml");
			Digester digester = new Digester();
			digester.addObjectCreate("shell/command", Command.class);
			digester.addSetProperties("shell/command");
			digester.addSetNext("shell/command", "add");

			digester.addObjectCreate("shell/command/dependency", Dependency.class);
			digester.addSetProperties("shell/command/dependency");
			digester.addSetNext("shell/command/dependency", "addDependency");
			
			commands = new ArrayList<Command>();
			
			digester.push(commands);
			while (enumUrl.hasMoreElements())
			{
				URL url = enumUrl.nextElement();
				digester.parse(url);
			}
			
			//生成命令树，命令树的每一个结点也是个Map
			commandTreeRoot = new HashMap<String, Object>();
			for (Command cmd : commands)
			{
				putCommand(cmd.getName(), cmd, commandTreeRoot);
			}
		}catch (Exception e)
		{
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 一个标准的尾递归调用，把命令解析并放到命令树上<br/>
	 * 对于每个结点：
	 * key="command"，value=Command对象
	 * key="children", value=Map<String, Map<String, Object>>，即一棵子树
	 * TODO 在放树上时需要检测重复
	 * @param text
	 * @param cmd
	 * @param node
	 */
	private static void putCommand(String text, Command cmd, Map<String, Object> node)
	{
		if (StringUtils.isBlank(text))
		{
			//解析到头了，把命令放在当前结点就可以了
			node.put("command", cmd);
			return;
		}
		
		//要把当前命令分成两部分，以第一个空格为界
		text = StringUtils.strip(text);
		int space = StringUtils.indexOf(text, ' ');
		
		String left, right;
		if (space == -1)
		{
			//没找到空格，整个就是个命令了
			left = text;
			right = "";
		}
		else
		{
			left = StringUtils.left(text, space);
			right = StringUtils.substring(text, space);
		}
		
		//放入子结点
		Map<String, Map<String, Object>> subtree;
		if (node.containsKey("children"))
		{
			subtree = (Map<String, Map<String, Object>>)node.get("children");
		}
		else
		{
			subtree = new HashMap<String, Map<String,Object>>();
			node.put("children", subtree);
		}

		Map<String, Object> subnode;
		if (subtree.containsKey(left))
		{
			subnode = subtree.get(left);
		}
		else
		{
			subnode = new HashMap<String, Object>();
			subtree.put(left, subnode);
		}
		putCommand(right, cmd, subnode);
	}
	
	public static Command parse(String text, Properties params)
	{
		String parts[] = StringUtils.split(StringUtils.trim(text), ' ');
		
		Map<String, Object> node = getCommandTree();
		Command cmd = null;
		
		//开始解析
		int count = 0;		//记录命令本身用了几个词
		for (int i=0; i<parts.length; i++)
		{
			Map<String, Map<String, Object>> subtree = (Map<String, Map<String, Object>>) node.get("children");
			
			if (subtree == null ||!subtree.containsKey(parts[i]))
				break;
			
			node = subtree.get(parts[i]);
			if (node.containsKey("command"))
			{
				cmd = (Command) node.get("command");
				count = i + 1;
			}
		}
		
		if (cmd == null)
			return null;
		
		//解析参数
		if (params != null)
		{
			for (int i = count; i<parts.length; i++)
			{
				if (StringUtils.contains(parts[i], "="))
				{
					int index = StringUtils.indexOf(parts[i], "=");
					String param = StringUtils.left(parts[i], index);
					String value = StringUtils.substring(parts[i], index + 1);
					params.put(param, value);
				}
				else
				{
					params.put(parts[i], true);
				}
			}
		}
		return cmd;
	}

	public static List<Command> getCommands() {
		return commands;
	}
}
