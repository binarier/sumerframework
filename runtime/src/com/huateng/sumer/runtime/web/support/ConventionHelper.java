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
 * �ṩ���ֹ����ļ��й���ĵط�
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
						
						//�����ȼ���͵�����
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
							//����ҵ���ӦBrowseService, ����е���
							BrowseService<?> bs = ctx.getBean(browseServiceId, BrowseService.class);
							BrowserLayout bl = (BrowserLayout) al;
							Object model = getModelObject(context);
							Object obj = model;
							if (StringUtils.isNotBlank(bl.getNestedPath()))//����nestedPath
								obj = PropertyUtils.getNestedProperty(obj, bl.getNestedPath());
							//ȡ��ҳ����
							Pagination pagination = (Pagination)PropertyUtils.getNestedProperty(obj, bl.getPaginationPath());
							Assert.notNull(pagination, "��"+bl.getNestedPath()+"/"+bl.getPaginationPath()+"��û�ҵ�Pagination����");
	
							List<?> list = bs.browse(obj, pagination);
							Assert.notNull(list, "BrowseService����nullֵ");
							
							PropertyUtils.setProperty(obj, bl.getDataPath(), list);
						}
					}
				}
			}
			return success();
		}
		catch (Exception e)
		{
			logger.fatal("BrowseSerivice�������", e);
			return error();
		}
	}

	@SuppressWarnings("unchecked")
	public List<FormDefinition> getViewStateFormDefinitions(RequestContext context)
	{
		//Ĭ��forms bean������Ϊ[flowId]#[stateId]
		String defaultBeanId = context.getActiveFlow().getId()+"#"+context.getCurrentState().getId()+"#forms";
		
		String id = getCurrentViewState(context).getAttributes().getString("forms", defaultBeanId);

		//��������ȡ����
		Object tmp;
		if (ctx.containsBean(id))
		{
			tmp = ctx.getBean(id);
			//����������
			List<FormDefinition> forms = new ArrayList<FormDefinition>();		//��֤�����л�
			if (tmp instanceof List<?>)
				forms.addAll((List<FormDefinition>)tmp);
			else if (tmp instanceof FormDefinition)
				forms.add((FormDefinition)tmp);
			else
				Assert.isInstanceOf(FormDefinition.class, tmp, "��Ч����");
			return forms;
		}
		else
			return new ArrayList<FormDefinition>(0);		//û�еĻ��ͷ���һ���յ�
	}
	
	private ViewState getCurrentViewState(RequestContext context)
	{
		StateDefinition sd = context.getCurrentState();
		Assert.isInstanceOf(ViewState.class, sd, "������ViewState�²��ܵ���");
		
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
