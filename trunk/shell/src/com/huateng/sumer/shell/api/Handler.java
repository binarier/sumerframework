package com.huateng.sumer.shell.api;

import java.util.Properties;

import jline.ConsoleReader;

/**
 * 处理Sumer Shell的命令请求的接口
 * @author chenjun.li
 *
 */
public interface Handler {
	void handle(ConsoleReader console, Properties params) throws Exception;
}
