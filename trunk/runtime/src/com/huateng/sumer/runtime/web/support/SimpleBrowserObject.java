package com.huateng.sumer.runtime.web.support;

import java.io.Serializable;
import java.util.List;

import com.huateng.sumer.runtime.web.meta.Pagination;

/**
 * 常用浏览对象
 * @author chenjun.li
 *
 * @param <T>
 */
public class SimpleBrowserObject<T extends Serializable> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private List<T> data;
	private Pagination pagination = new Pagination();
	private Object criteria;

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Object getCriteria() {
		return criteria;
	}

	public void setCriteria(Object criteria) {
		this.criteria = criteria;
	}

	
}
