package com.huateng.sumer.runtime.service.general;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;

/**
 * 实现一个通用的基于example的浏览服务实现
 * @author chenjun.li
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class DefaultIbatorBrowseService<T> extends AbstractIbatorBrowseService<T> {

	private Class exampleClass;
	
	private boolean excludeNone = true;
	
	private boolean excludeZeroes = false;
	
	private boolean excludeBlank = true;
	
	private Properties orders;
	
	private String contextPath = "criteria";
	
	private Properties criteriaList;
	
	@Override
	protected Object example(Object context) {
		try
		{
			Object example = exampleClass.newInstance();
			
			if (context != null && criteriaList != null)		//如果context或criteria为null，则全查
			{
				//处理contextPath
				if (StringUtils.isNotBlank(contextPath))
					context = PropertyUtils.getNestedProperty(context, contextPath);
				
				//建立criteria
				Object criteria = MethodUtils.invokeExactMethod(example, "createCriteria", null);
				
				for (Entry<Object, Object> entry : criteriaList.entrySet())
				{
					String name = (String)entry.getKey();		//属性名
					Object value = PropertyUtils.getSimpleProperty(context, name);
					
					if (value == null)
					{
						if (!excludeNone)
							MethodUtils.invokeExactMethod(criteria, "and" + StringUtils.capitalize(name) + "IsNull", null);
					}
					else
					{
						boolean skip = 
								((value instanceof String)&&(StringUtils.isBlank((String)value)&&excludeBlank) ||	//排除为空的情况
								((value instanceof Number)&&(((Number)value).longValue() == 0l)&&excludeZeroes))||		//排除为零的情况
								((value instanceof BigDecimal)&&(value.equals(BigDecimal.valueOf(0)))&&excludeZeroes);

						if (!skip)
							MethodUtils.invokeExactMethod(criteria, "and" + StringUtils.capitalize(name) + "EqualTo", value);
						//TODO 支持LIKE
					}
				}
			}
				
			//处理排序
			if (orders != null && !orders.isEmpty())
			{
				StringBuilder clause = new StringBuilder();
				Iterator<Entry<Object, Object>> iter = orders.entrySet().iterator();
				while (iter.hasNext())
				{
					Entry<Object, Object> order = iter.next();
					// 如果指定了"xxx=desc"，则使用降序，否则一律升序。
					if (StringUtils.equalsIgnoreCase("desc", StringUtils.trim((String) order.getValue())))
					{
						clause.append(order.getKey());
						clause.append(" desc");
					}
					else
					{
						clause.append(order.getKey());
						clause.append(" asc");
					}
					if (iter.hasNext())
						clause.append(",");
				}
				
				PropertyUtils.setProperty(example, "orderByClause", clause.toString());
			}
			return example;
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException(e);
		}
	}

	@Required
	public void setExampleClass(Class exampleClass) {
		this.exampleClass = exampleClass;
	}

	public boolean isExcludeNone() {
		return excludeNone;
	}

	public void setExcludeNone(boolean excludeNone) {
		this.excludeNone = excludeNone;
	}

	public boolean isExcludeZeroes() {
		return excludeZeroes;
	}

	public void setExcludeZeroes(boolean excludeZeroes) {
		this.excludeZeroes = excludeZeroes;
	}

	public boolean isExcludeBlank() {
		return excludeBlank;
	}

	public void setExcludeBlank(boolean excludeBlank) {
		this.excludeBlank = excludeBlank;
	}

	public Class getExampleClass() {
		return exampleClass;
	}


	public Properties getOrders() {
		return orders;
	}


	public void setOrders(Properties orders) {
		this.orders = orders;
	}


	public String getContextPath() {
		return contextPath;
	}


	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public Properties getCriteriaList() {
		return criteriaList;
	}

	public void setCriteriaList(Properties criteriaList) {
		this.criteriaList = criteriaList;
	}
}
