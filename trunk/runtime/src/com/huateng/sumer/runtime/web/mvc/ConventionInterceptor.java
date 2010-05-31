package com.huateng.sumer.runtime.web.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ConventionInterceptor extends HandlerInterceptorAdapter {
	@Autowired(required=false)
	@Qualifier("convention")
	private Map<String, Object> conventionBeans;

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		modelAndView.addAllObjects(conventionBeans);
		
		//处理默认值
		if (!modelAndView.getModel().containsKey("sumer_title"))
			modelAndView.addObject("sumer_title", "无标题");
	}

	public Map<String, Object> getConventionBeans() {
		return conventionBeans;
	}

	public void setConventionBeans(Map<String, Object> conventionBeans) {
		this.conventionBeans = conventionBeans;
	}
}
