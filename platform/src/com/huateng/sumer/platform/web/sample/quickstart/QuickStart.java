package com.huateng.sumer.platform.web.sample.quickstart;

import org.springframework.stereotype.Component;

import com.huateng.sumer.runtime.web.support.BusinessException;

@Component("sampleQuickStart")
public class QuickStart {
	
	public void demoEditorValidation(EditorInfo info)
	{
		//用来演示代码校验
		throw new BusinessException("代码中校验可以报错，校验填入的值为"+info.getBirthYear());
	}
}
