package com.huateng.sumer.runtime.web.meta;

import java.io.Serializable;

public class Pagination implements Serializable {

	private static final long serialVersionUID = 1L;

	private int pageSize = 20;
	
	private int currentPage = 0;

	private int count = -1;

	private String pageEvent = "page";

	public int getPageCount()
	{
		if (count == -1)
			return -1;
		return (count - 1)/pageSize + 1;
	}
	
	public int getSkipResults()
	{
		return getCurrentPage() * getPageSize(); 
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getPageEvent() {
		return pageEvent;
	}

	public void setPageEvent(String pageEvent) {
		this.pageEvent = pageEvent;
	}
}
