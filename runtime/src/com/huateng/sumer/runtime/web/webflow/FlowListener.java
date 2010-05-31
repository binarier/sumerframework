package com.huateng.sumer.runtime.web.webflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.execution.FlowExecutionException;
import org.springframework.webflow.execution.FlowExecutionListenerAdapter;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.View;

import com.huateng.sumer.runtime.annotation.WebComponent;
import com.huateng.sumer.runtime.web.meta.AbstractField;
import com.huateng.sumer.runtime.web.meta.AbstractLayout;
import com.huateng.sumer.runtime.web.meta.FormDefinition;
import com.huateng.sumer.runtime.web.support.ConventionHelper;

@WebComponent
public class FlowListener extends FlowExecutionListenerAdapter {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired(required=false)
	@Qualifier("convention")
	private Map<String, Object> conventionBeans;
	
	@Autowired
	private ConventionHelper conventionHelper;

	@SuppressWarnings("unchecked")
	@Override
	public void viewRendering(RequestContext context, View view, StateDefinition viewState) {
		try
		{
			//按惯例取表单定义列表
			List<FormDefinition> sumer_forms = conventionHelper.getViewStateFormDefinitions(context);		//保证可序列化
	
			//加入按惯例注入的bean
			context.getRequestScope().asMap().putAll(conventionBeans);
			
			//处理参考数据
			Map<AbstractLayout, Map<AbstractField, Object>> sumer_reference = new HashMap<AbstractLayout, Map<AbstractField, Object>>();
			for (FormDefinition fd : sumer_forms)
			{
				for (AbstractLayout al : fd.getLayouts())
				{
					Map<AbstractField, Object> map = al.getReference(context);
					sumer_reference.put(al, map);
				}
			}
			
			//把sumer打头的惯例变量放入ViewScope
			context.getFlashScope().put("sumer_forms", sumer_forms);
			context.getFlashScope().put("sumer_model_name", context.getCurrentState().getAttributes().get("model"));
			context.getFlashScope().put("sumer_reference", sumer_reference);
		}
		catch(Exception e)
		{
			logger.error("View Render预处理出错", e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void exceptionThrown(RequestContext context,
			FlowExecutionException exception) {
		logger.error("flow error", exception);
		
		context.getMessageContext().addMessage(new MessageBuilder().defaultText(exception.getMessage()+" error").error().build());
	}
	
	public ConventionHelper getConventionHelper() {
		return conventionHelper;
	}

	public void setConventionHelper(ConventionHelper conventionHelper) {
		this.conventionHelper = conventionHelper;
	}
}
