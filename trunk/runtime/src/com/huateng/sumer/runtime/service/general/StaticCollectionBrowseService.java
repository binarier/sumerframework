package com.huateng.sumer.runtime.service.general;

import java.util.ArrayList;
import java.util.List;

import com.huateng.sumer.runtime.service.api.BrowseService;
import com.huateng.sumer.runtime.web.meta.Pagination;

/**
 * ʹ�þ�̬������Ϊ����Դ���������ͨ��ע��{@link #data}��������̬ȷ�����ݣ������ṩ��ҳ����
 * @author chenjun.li
 *
 * @param <T> ��������
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
