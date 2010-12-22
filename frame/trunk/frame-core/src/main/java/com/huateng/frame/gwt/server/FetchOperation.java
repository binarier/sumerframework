package com.huateng.frame.gwt.server;

public interface FetchOperation<T, C>
{
	FetchResult<T> fetch(C criteria, int startRow, int endRow, String sortBy);
}
