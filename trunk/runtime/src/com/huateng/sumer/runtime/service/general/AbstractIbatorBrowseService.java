package com.huateng.sumer.runtime.service.general;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huateng.sumer.runtime.service.api.BrowseService;
import com.huateng.sumer.runtime.web.meta.Pagination;
import com.ibatis.sqlmap.client.SqlMapClient;

public abstract class AbstractIbatorBrowseService<T> implements BrowseService<T> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private String table;
	private boolean doCount = true;

	@Autowired
	private SqlMapClient sqlMapClient;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> browse(Object context, Pagination pagination) {
		try
		{
			List<T> result;
			Object example = example(context);
			if (doCount)
				pagination.setCount((Integer)sqlMapClient.queryForObject(table + ".ibatorgenerated_countByExample", example));
			result = (List<T>)sqlMapClient.queryForList(table + ".ibatorgenerated_selectByExample", example, pagination.getSkipResults(), pagination.getPageSize());
			return result;
		}
		catch (SQLException e)
		{
			logger.error("数据库执行出错", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 子类接口，用于确实确定example对象
	 * @param context 由 {@link BrowseService}接口传来的context对象
	 * @return example对象
	 */
	protected abstract Object example(Object context);

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public boolean isDoCount() {
		return doCount;
	}

	public void setDoCount(boolean doCount) {
		this.doCount = doCount;
	}
}
