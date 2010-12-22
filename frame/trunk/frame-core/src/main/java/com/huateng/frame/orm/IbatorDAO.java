package com.huateng.frame.orm;

import java.util.List;

public interface IbatorDAO<T, K, E extends IbatorExample>
{
	int countByExample(E example);

	int deleteByExample(E example);

	int deleteByPrimaryKey(K key);

	void insert(T record);

	void insertSelective(T record);

	List<T> selectByExample(E example);

    List<T> selectByExample(E example, int skipResults, int maxResults);

	T selectByPrimaryKey(K key);

	int updateByExampleSelective(T record, E example);

	int updateByExample(T record, E example);

	int updateByPrimaryKeySelective(T record);

	int updateByPrimaryKey(T record);
}
