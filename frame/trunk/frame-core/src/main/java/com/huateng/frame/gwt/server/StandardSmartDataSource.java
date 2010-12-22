package com.huateng.frame.gwt.server;

import org.springframework.transaction.annotation.Transactional;

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
	public FetchResult<T> fetch(SmartCriteria criteria, int startRow, int endRow)
	{
		try
		{
			FetchResult<T> rlt = new FetchResult<T>();
			E example = null;
			if (criteria != null)
			{
				example = exampleClass.newInstance();
				example.getOredCriteria().addAll(criteria.toIbatorCriteria(example.getFieldNameMap()));
			}
			rlt.setData(ibatorDAO.selectByExample(example, startRow, endRow - startRow));
			rlt.setStartRow(startRow);
			rlt.setEndRow(startRow + rlt.getData().size());
			rlt.setTotalRows(rlt.getData().size());
			return rlt;
		}
		catch(Throwable t)
		{
			throw new IllegalArgumentException(t);
		}
	}

}
