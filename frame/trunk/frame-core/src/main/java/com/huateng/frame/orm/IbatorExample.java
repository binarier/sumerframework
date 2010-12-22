package com.huateng.frame.orm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IbatorExample
{
	protected String orderByClause;

	protected List<IbatorCriteria> oredCriteria;
	
	//TODO 待优化
	protected Map<String, String> fieldNameMap = new HashMap<String, String>(); 

	public IbatorExample()
	{
		oredCriteria = new ArrayList<IbatorCriteria>();
	}

	protected IbatorExample(IbatorExample example)
	{
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}
	
	public void copy(IbatorExample example)
	{
		oredCriteria.addAll(example.oredCriteria);
		orderByClause = example.orderByClause;
	}
	
	public void setOrderByClause(String orderByClause)
	{
		this.orderByClause = orderByClause;
	}

	public String getOrderByClause()
	{
		return orderByClause;
	}

	public List<IbatorCriteria> getOredCriteria()
	{
		return oredCriteria;
	}

	public void or(IbatorCriteria criteria)
	{
		oredCriteria.add(criteria);
	}

	public IbatorCriteria createCriteria()
	{
		IbatorCriteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0)
		{
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	protected IbatorCriteria createCriteriaInternal()
	{
		IbatorCriteria criteria = new IbatorCriteria();
		return criteria;
	}

	public void clear()
	{
		oredCriteria.clear();
	}
	
	public Map<String, String> getFieldNameMap()
	{
		return fieldNameMap;
	}
}
