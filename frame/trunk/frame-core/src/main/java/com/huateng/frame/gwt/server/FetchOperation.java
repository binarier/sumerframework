package com.huateng.frame.gwt.server;


public interface FetchOperation<T> {
	FetchResult<T> fetch(Object criteria, int startRow, int endRow);
}
