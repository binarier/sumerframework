package com.huateng.sumer.runtime.web.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.expression.spel.SpringELExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.webflow.action.MultiAction;
import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.huateng.sumer.runtime.annotation.WebComponent;
import com.huateng.sumer.runtime.service.api.BrowseService;
import com.huateng.sumer.runtime.web.meta.AbstractLayout;
import com.huateng.sumer.runtime.web.meta.BrowserLayout;
import com.huateng.sumer.runtime.web.meta.FormDefinition;
import com.huateng.sumer.runtime.web.meta.Pagination;

/**
 * 提供各种惯例的集中管理的地方
 * @author chenjun.li
 *
 */
@WebComponent
public class ConventionHelper extends MultiAction {

	@Autowired
	private ApplicationContext ctx;
	
	public Event retrieveBrowseData(RequestContext context)
	{
		try
		{
			ViewState viewState = getCurrentViewState(context);
			int nBrowse = 0;
			for (FormDefinition fd : getViewStateFormDefinitions(context))
			{
				for (AbstractLayout al : fd.getLayouts())
				{
					if (al instanceof BrowserLayout)
					{
						String browseServiceId = null;
						
						//从优先级最低的找起
						String prefix = context.getActiveFlow().getId() + "#" + context.getCurrentState().getId()+"#browse";
						
						String probe = prefix; 
						if (ctx.containsBean(probe))
							browseServiceId = probe;
	
						probe = prefix + "#" + nBrowse;
						if (ctx.containsBean(probe))
							browseServiceId = probe;
						
						if (StringUtils.isNotBlank(al.getNestedPath()))
						{
							probe = ctx.containsBean(prefix) + "#" + al.getNestedPath();
							browseServiceId = probe;
						}
	
						browseServiceId = viewState.getAttributes().getString("browse", browseServiceId);
						browseServiceId = viewState.getAttributes().getString("browse#" + nBrowse, browseServiceId);
						if (StringUtils.isNotBlank(al.getNestedPath()))
						{
							browseServiceId = viewState.getAttributes().getString("browse#" + al.getNestedPath(), browseServiceId);
						}
						
						if (browseServiceId != null)
						{
							//如果找到对应BrowseService, 则进行调用
							BrowseService<?> bs = ctx.getBean(browseServiceId, BrowseService.class);
							BrowserLayout bl = (BrowserLayout) al;
							Object model = getModelObject(context);
							Object obj = model;
							if (StringUtils.isNotBlank(bl.getNestedPath()))//处理nestedPath
								obj = PropertyUtils.getNestedProperty(obj, bl.getNestedPath());
							//取分页对象
							Pagination pagination = (Pagination)PropertyUtils.getNestedProperty(obj, bl.getPaginationPath());
							Assert.notNull(pagination, "在"+bl.getNestedPath()+"/"+bl.getPaginationPath()+"下没找到Pagination对象");
	
							List<?> list = bs.browse(obj, pagination);
							Assert.notNull(list, "BrowseService返回null值");
							
							PropertyUtils.setProperty(obj, bl.getDataPath(), list);
						}
					}
				}
			}
			return success();
		}
		catch (Exception e)
		{
			logger.fatal("BrowseSerivice处理出错", e);
			return error();
		}
	}

	@SuppressWarnings("unchecked")
	public List<FormDefinition> getViewStateFormDefinitions(RequestContext context)
	{
		//默认forms bean定义名为[flowId]#[stateId]
		String defaultBeanId = context.getActiveFlow().getId()+"#"+context.getCurrentState().getId()+"#forms";
		
		String id = getCurrentViewState(context).getAttributes().getString("forms", defaultBeanId);

		//从容器里取定义
		Object tmp;
		if (ctx.containsBean(id))
		{
			tmp = ctx.getBean(id);
			//处理集合类型
			List<FormDefinition> forms = new ArrayList<FormDefinition>();		//保证可序列化
			if (tmp instanceof List<?>)
				forms.addAll((List<FormDefinition>)tmp);
			else if (tmp instanceof FormDefinition)
				forms.add((FormDefinition)tmp);
			else
				Assert.isInstanceOf(FormDefinition.class, tmp, "无效类型");
			return forms;
		}
		else
			return new ArrayList<FormDefinition>(0);		//没有的话就返回一个空的
	}
	
	private ViewState getCurrentViewState(RequestContext context)
	{
		StateDefinition sd = context.getCurrentState();
		Assert.isInstanceOf(ViewState.class, sd, "必须在ViewState下才能调用");
		
		return (ViewState)sd;
	}
	
	private Object getModelObject(RequestContext context)
	{
		return ((SpringELExpression) context.getCurrentState().getAttributes().getRequired("model")).getValue(context);
	}


	public void setCtx(ApplicationContext ctx) {
		this.ctx = ctx;
	}
}
