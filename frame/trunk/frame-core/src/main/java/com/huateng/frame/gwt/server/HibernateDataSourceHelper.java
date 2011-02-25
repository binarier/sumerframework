package com.huateng.frame.gwt.server;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;

import com.huateng.frame.gwt.client.datasource.FetchRequest;
import com.huateng.frame.gwt.client.datasource.FetchResponse;

public abstract class HibernateDataSourceHelper
{
	public static FetchResponse processRequest(FetchRequest fetchRequest, Criteria criteria)
	{
		assert criteria != null;
		
		try
		{
			//处理request中的条件
			if (StringUtils.isNotBlank(fetchRequest.getCriteriaJSON()))
			{
				SmartCriteria smartCriteria = SmartCriteria.createFromJSON(fetchRequest.getCriteriaJSON());
				if (smartCriteria.isValid())
					criteria.add(smartCriteria.toHibernateCriterion());
			}
			FetchResponse fetchResponse = new FetchResponse();
			
			//先取总数
			Long countResult = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
			int count = 0;
			if (countResult != null)
				count = countResult.intValue();
			
			fetchResponse.setTotalRows(count);
			
			//还原criteria, 需要先清空projection，然后设置transformer，因为setProjection也会设置transformer
			criteria.setProjection(null);
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			
			//再按分页取数据
			List<?> rows = criteria
				.setFirstResult(fetchRequest.getStartRow())
				.setMaxResults(fetchRequest.getEndRow() - fetchRequest.getStartRow())
				.list();
			
			Serializable result[][] = new Serializable[rows.size()][];
			
			int i=0;
			for (Object row : rows)
			{
				if (fetchResponse.getFields() == null)
				{
					//第一次的时候取字段信息
					PropertyDescriptor pds[] = PropertyUtils.getPropertyDescriptors(row.getClass());
					String fields[] = new String[pds.length - 1];
					int p = 0;
					for (PropertyDescriptor pd : pds)
					{
						if (pd.getName().equals("class"))
							continue;
						fields[p++] = pd.getName();
					}
					fetchResponse.setFields(fields);
				}
				
				String fields[] = fetchResponse.getFields();
				result[i] = new Serializable[fields.length];
				for (int j=0; j<fields.length; j++)
				{
						result[i][j] = (Serializable)PropertyUtils.getProperty(row, fields[j]);
				}
				i++;
			}
			fetchResponse.setData(result);
			return fetchResponse;
		}
		catch (Throwable t)
		{
			throw new IllegalArgumentException(t);
		}
	}
}
