package com.huateng.frame.gwt.server;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.huateng.frame.gwt.client.datasource.FetchResult;
import com.huateng.frame.orm.IbatorDAO;
import com.huateng.frame.orm.IbatorExample;

public class StandardSmartDataSource<T, K, E extends IbatorExample> implements FetchOperation<T, SmartCriteria>
{
	private IbatorDAO<T, K, E> ibatorDAO;

	private Class<E> exampleClass;

	public StandardSmartDataSource(IbatorDAO<T, K, E> ibatorDAO, Class<E> exampleClass)
	{
		this.ibatorDAO = ibatorDAO;
		this.exampleClass = exampleClass;
	}

	@Transactional(readOnly = true)
	public FetchResult<T> fetch(SmartCriteria criteria, int startRow, int endRow, String sortBy)
	{
		try
		{
			FetchResult<T> rlt = new FetchResult<T>();
			E example = exampleClass.newInstance();
			Map<String, String> mapping = example.getFieldNameMap();
			Assert.notNull(mapping);
			if (StringUtils.isNotBlank(sortBy))
			{
				String direction = " ASC";
				if (sortBy.startsWith("-"))
				{
					direction = " DESC";
					sortBy = sortBy.substring(1);
				}
				String col = mapping.get(sortBy);
				Assert.notNull(col);
				
				example.setOrderByClause(col + direction);
			}
			if (criteria != null)
			{
				example.getOredCriteria().addAll(criteria.toIbatorCriteria(mapping));
			}
			List<T> rows = ibatorDAO.selectByExample(example, startRow, endRow - startRow);
			int count = ibatorDAO.countByExample(example);

			rlt.setData(rows);
			rlt.setStartRow(startRow);
			rlt.setEndRow(startRow + rlt.getData().size());
			rlt.setTotalRows(count);
			return rlt;
		}
		catch(Throwable t)
		{
			throw new IllegalArgumentException(t);
		}
	}

}
