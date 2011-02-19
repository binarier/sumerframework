package com.huateng.frame.gwt.server;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.beanutils.PropertyUtils;

public class MappingHelper
{
	public static Map<String, Serializable> convertToMap(Object bean)
	{
		try
		{
			Map<String, Serializable> result = new TreeMap<String, Serializable>();
			for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(bean.getClass()))	//这个带缓存的
			{
				if (pd.getName().equals("class"))
					continue;
				Object value = PropertyUtils.getProperty(bean, pd.getName());
				if (value instanceof Serializable)
					result.put(pd.getName(), (Serializable)value);
			}
			return result;
		}
		catch (Throwable t)
		{
			throw new IllegalArgumentException(t);
		}
	}
	
	public static void applyToObject(Object bean, Map<String, Serializable> map)
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
