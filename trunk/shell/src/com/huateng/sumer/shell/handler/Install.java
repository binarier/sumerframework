package com.huateng.sumer.shell.handler;

import java.util.Properties;

import jline.ConsoleReader;

import com.huateng.sumer.shell.api.Handler;
import com.huateng.sumer.shell.support.IvyHelper;

public class Install implements Handler {

	@Override
	public void handle(ConsoleReader console, Properties params) throws Exception{
		IvyHelper.addConf(params.getProperty("conf"), params.getProperty("extends"));
		IvyHelper.addDependency(params.getProperty("org"), params.getProperty("name"), params.getProperty("rev"), params.getProperty("conf"));
	}

}
