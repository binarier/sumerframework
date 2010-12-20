package com.huateng.frame.gwt.server;

import org.springframework.transaction.annotation.Transactional;

import com.huateng.frame.orm.IbatorDAO;

public class StandardSmartDataSource<T, K, E> implements
	FetchOperation<T> 
{
	private IbatorDAO<T, K, E> ibatorDAO;
	
	public StandardSmartDataSource(IbatorDAO<T, K, E> ibatorDAO) {
		this.ibatorDAO = ibatorDAO;
	}
	
	@Transactional(readOnly=true)
	public FetchResult<T> fetch(Object criteria, int startRow, int endRow) {
		
		FetchResult<T> rlt = new FetchResult<T>();
		rlt.setData(ibatorDAO.selectByExample(null, startRow, endRow - startRow));
		rlt.setStartRow(startRow);
		rlt.setEndRow(startRow + rlt.getData().size());
		rlt.setTotalRows(rlt.getData().size());
		return rlt;
	}

}
