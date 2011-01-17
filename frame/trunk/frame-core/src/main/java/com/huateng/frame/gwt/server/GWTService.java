package com.huateng.frame.gwt.server;

import javax.servlet.ServletConfig;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletConfigAware;

import com.google.gwt.rpc.server.RpcServlet;

/**
 * @author chenjun.li
 * RPC调用服务端基类，可以使用 {@link org.springframework.web.servlet.handler.SimpleServletHandlerAdapter}来进行Adapter，解决了其初始化问题
 */
public class GWTService extends RpcServlet implements ServletConfigAware, InitializingBean
{
	private static final long serialVersionUID = 1L;
	private ServletConfig servletConfig;

	public void afterPropertiesSet() throws Exception
	{
		init(servletConfig);
	}
	
	public void setServletConfig(ServletConfig servletConfig)
	{
		this.servletConfig = servletConfig;
	}

}
