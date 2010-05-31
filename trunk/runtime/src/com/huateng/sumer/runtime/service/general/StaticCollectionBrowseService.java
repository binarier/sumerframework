package com.huateng.sumer.runtime.service.general;

import java.util.ArrayList;
import java.util.List;

import com.huateng.sumer.runtime.service.api.BrowseService;
import com.huateng.sumer.runtime.web.meta.Pagination;

/**
 * 使用静态数据作为数据源的浏览服务，通过注入{@link #data}属性来静态确定数据，本类提供分页服务
 * @author chenjun.li
 *
 * @param <T> 数据类型
 */
public class StaticCollectionBrowseService<T> implements BrowseService<T> {

	private List<T> data;
	
	@Override
	public List<T> browse(Object context, Pagination pagination) {
		if (data != null)
		{
			pagination.setCount(data.size());
			ArrayList<T> result = new ArrayList<T>();
			result.addAll(data.subList(
					Math.min(data.size() - 1, pagination.getSkipResults()),
					Math.min(data.size(), pagination.getSkipResults() + pagination.getPageSize())));
			return result;
		}
		else
		{
			return new ArrayList<T>(0);
		}
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
