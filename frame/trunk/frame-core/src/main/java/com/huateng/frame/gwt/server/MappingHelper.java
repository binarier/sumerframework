package com.huateng.frame.gwt.server;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;

public class MappingHelper
{
	public static Map<String, Object> convertToMap(Object bean)
	{
		try
		{
			Map<String, Object> result = new HashMap<String, Object>();
			for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(bean.getClass()))	//这个带缓存的
			{
				if (pd.getName().equals("class"))
					continue;
				Object value = PropertyUtils.getProperty(bean, pd.getName());
				result.put(pd.getName(), value);
			}
			return result;
		}
		catch (Throwable t)
		{
			throw new IllegalArgumentException(t);
		}
	}
	
	public static void applyToObject(Object bean, Map<String, ?> map)
	{
		try
		{
			for (Entry<String, ?> entry : map.entrySet())
			{
				PropertyUtils.setProperty(bean, entry.getKey(), entry.getValue());
			}
		}
		catch (Throwable t)
		{
			throw new IllegalArgumentException(t);
		}
	}
}
