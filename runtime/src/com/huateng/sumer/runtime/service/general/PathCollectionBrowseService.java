package com.huateng.sumer.runtime.service.general;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.huateng.sumer.runtime.service.api.BrowseService;
import com.huateng.sumer.runtime.web.meta.Pagination;

/**
 * ʹ�ð�������Ϊ����Դ���������ͨ��{@link #path}�������ӵ�ǰ������ȡ������
 * @author chenjun.li
 *
 * @param <T> ��������
 */
@SuppressWarnings("unchecked")
public class PathCollectionBrowseService<T> implements BrowseService<T> {

	private String path;
	
	@Override
	public List<T> browse(Object context, Pagination pagination) {
		List<T> data = null;
		
		try {
			data = (List<T>) PropertyUtils.getNestedProperty(context, path);
		} 
		catch (Exception e){
			throw new IllegalArgumentException(e);
		}
		
		if (data != null)
		{
			pagination.setCount(data.size());
			return data.subList(
					Math.min(data.size() - 1, pagination.getSkipResults()),
					Math.min(data.size(), pagination.getSkipResults() + pagination.getPageSize()));
		}
		else
		{
			return new ArrayList<T>(0);
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
