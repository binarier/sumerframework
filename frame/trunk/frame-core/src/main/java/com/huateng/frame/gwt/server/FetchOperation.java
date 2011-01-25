package com.huateng.frame.gwt.server;

import com.huateng.frame.gwt.client.datasource.FetchResult;

public interface FetchOperation<T, C>
{
	FetchResult<T> fetch(C criteria, int startRow, int endRow, String sortBy);
}
