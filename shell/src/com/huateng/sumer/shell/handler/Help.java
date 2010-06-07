package com.huateng.sumer.shell.handler;

import java.util.Properties;

import jline.ConsoleReader;

import org.apache.commons.lang.StringUtils;

import com.huateng.sumer.shell.api.Handler;
import com.huateng.sumer.shell.configure.Command;
import com.huateng.sumer.shell.configure.CommandLoader;

public class Help implements Handler {

	@Override
	public void handle(ConsoleReader console, Properties params) {
		try
		{
			for (Command cmd : CommandLoader.getCommands())
			{
				console.printString(StringUtils.rightPad(cmd.getName(), 20)+"- "+cmd.getDescription());
				console.printNewline();
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}
