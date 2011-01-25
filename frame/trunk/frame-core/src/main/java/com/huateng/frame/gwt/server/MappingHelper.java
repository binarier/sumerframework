package com.huateng.frame.gwt.server;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;

import com.huateng.frame.gwt.client.datasource.FetchResponse;

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
	
	public static FetchResponse convertToResponseData(Criteria criteria, int startRow, int endRow)
	{
		assert criteria != null;

		FetchResponse fetchResponse = new FetchResponse();
		
		criteria.
		
		criteria.setFirstResult(startRow).setMaxResults(endRow - startRow);
		
		
		Serializable result[][] = new Serializable[rows.length][];
		
		for (int i=0; i<result.length; i++)
		{
			if (response.getFields() == null)
			{
				PropertyDescriptor pds[] = PropertyUtils.getPropertyDescriptors(rows[i].getClass());
				String fields[] = new String[pds.length - 1];
				int p = 0;
				for (PropertyDescriptor pd : pds)
				{
					if (pd.getName().equals("class"))
						continue;
					fields[p++] = pd.getName();
				}
				response.setFields(fields);
			}
			
			String fields[] = response.getFields();
			result[i] = new Serializable[fields.length];
			for (int j=0; j<fields.length; j++)
			{
				try
				{
					result[i][j] = (Serializable)PropertyUtils.getProperty(rows[i], fields[j]);
				}
				catch (IllegalAccessException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InvocationTargetException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (NoSuchMethodException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		response.setData(result);
	}
}
