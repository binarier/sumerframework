package com.huateng.sumer.runtime.web.support;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.expression.spel.SpringELExpression;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageContextErrors;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.webflow.action.MultiAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.validation.WebFlowMessageCodesResolver;

import com.huateng.sumer.runtime.annotation.WebComponent;
import com.huateng.sumer.runtime.web.meta.AbstractField;
import com.huateng.sumer.runtime.web.meta.AbstractInputField;
import com.huateng.sumer.runtime.web.meta.AbstractLayout;
import com.huateng.sumer.runtime.web.meta.FormDefinition;
import com.huateng.sumer.runtime.web.meta.PanelLayout;
import com.huateng.sumer.runtime.web.meta.TextField;

/**
 * ����ʵ��WebFlow�����е�У��淶�͹���
 * @author chenjun.li
 *
 */
@WebComponent
public class ValidationHelper extends MultiAction {
	
	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
	private ConventionHelper conventionHelper;
	
	private MessageCodesResolver messageCodesResolver = new WebFlowMessageCodesResolver();
	
	private String charset = "GBK";
	private String excludingCharacters = "|'\",";

	
	protected Object getModelObject(RequestContext context) {
		return ((SpringELExpression) context.getCurrentState().getAttributes().getRequired("model")).getValue(context);
	}
	protected String getModelName(RequestContext context) {
		return ((SpringELExpression) context.getCurrentState().getAttributes().getRequired("model")).getExpressionString();
	}

	public Event invoke(RequestContext context, Validator validator)
	{
		Object model = getModelObject(context);
		MessageContextErrors mce = new MessageContextErrors(
				context.getMessageContext(),
				getModelName(context),
				model,
				null,
				messageCodesResolver,
				null);
		ValidationUtils.invokeValidator(validator, model, mce);
		if (mce.hasErrors())
			return error();
		return success();
	}
	
	public Event validateForms(RequestContext context, List<FormDefinition> fds)
	{
		for (FormDefinition fd : fds)
		{
			validateForms(context, fd);
		}
		return context.getMessageContext().hasErrorMessages() ? error() : success();
	}

	public Event validateForms(RequestContext context, FormDefinition fd)
	{
		Assert.notNull(fd, "FormDefinition����Ϊ��");

		MessageContext mc = context.getMessageContext();
		Object model = getModelObject(context);
		
		for (AbstractLayout al : fd.getLayouts())
		{
			String nestedPath = "";
			if (StringUtils.isNotBlank(al.getNestedPath()))
			{
				nestedPath = al.getNestedPath()+".";
			}
			if (al instanceof PanelLayout)
			{
				PanelLayout pl = (PanelLayout)al;
				
				for (AbstractField af : pl.getFields())
				{
					if (af instanceof AbstractInputField)
					{
						AbstractInputField aif = (AbstractInputField)af;
						//�����������ֶ�Ҫȡֵ
						Object value;
						String fullPath = nestedPath + aif.getPath();
						try {
							value = PropertyUtils.getNestedProperty(model, fullPath);
						} catch (Exception e) {
							throw new IllegalArgumentException("����" + fullPath + "ʧ��", e);
						}

						//���й�������
						if (aif.isMandatory()&&
							((value == null)||
							((value instanceof String)&&StringUtils.isBlank((String)value))))
						{
							mc.addMessage(new MessageBuilder()
								.error()
								.source(fullPath)
								.defaultText("����д")
								.build());
						}
						
						//���и������ֶ�����
						
						//�ı��ֶ�
						if (aif instanceof TextField)
						{
							TextField tf = (TextField)aif;
							if (value instanceof String)
							{
								String str = (String)value;
								try
								{
									if (str.getBytes(charset).length > tf.getMaxLength())
										mc.addMessage(new MessageBuilder()
											.error()
											.source(fullPath)
											.defaultText("��󳤶�Ϊ"+tf.getMaxLength())
											.build());
								}
								catch (Exception e)
								{
									throw new RuntimeException("ϵͳ�������", e);
								}

								//�������ַ�
								if (StringUtils.containsAny(str, excludingCharacters))
								{
									mc.addMessage(new MessageBuilder()
										.error()
										.source(fullPath)
										.defaultText("�����в��ܳ����ַ�[{0}]")
										.arg(excludingCharacters)
										.build());
								}
							}
						}
					}
				}
			}
		}
		
		return mc.hasErrorMessages() ? error() : success();
	}
	
	/**
	 * ������У�飬���������&lt;evaluate expression="validationHelper.validateForms"/&gt;ֱ�ӵ�
	 * @param context
	 * @return
	 */
	public Event validateForms(RequestContext context)
	{
		return validateForms(context, conventionHelper.getViewStateFormDefinitions(context));
	}
	
	public ApplicationContext getCtx() {
		return ctx;
	}
	public void setCtx(ApplicationContext ctx) {
		this.ctx = ctx;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getExcludingCharacters() {
		return excludingCharacters;
	}
	public void setExcludingCharacters(String excludingCharacters) {
		this.excludingCharacters = excludingCharacters;
	}
	public ConventionHelper getConventionHelper() {
		return conventionHelper;
	}
	public void setConventionHelper(ConventionHelper conventionHelper) {
		this.conventionHelper = conventionHelper;
	}
}
