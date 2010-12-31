package com.huateng.frame.gwt.server;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class HibernateSmartCriteriaService<T> implements FetchOperation<T, SmartCriteria>
{
	@Autowired
	private SessionFactory sessionFactory;
	
	private Class<T> clazz;
	
	public HibernateSmartCriteriaService(Class<T> clazz)
	{
		this.clazz = clazz;
	}
	
	@Transactional
	public FetchResult<T> fetch(SmartCriteria criteria, int startRow, int endRow, String sortBy)
	{
		Criterion criterion = null;
		if (criteria != null)
			criterion = criteria.toHibernateCriterion();
		Criteria cri = sessionFactory.getCurrentSession()
			.createCriteria(clazz)
			.setMaxResults(endRow - startRow)
			.setFirstResult(startRow);
		if (criterion != null)
			cri.add(criterion);
		if (StringUtils.isNotBlank(sortBy))
			if (sortBy.startsWith("-"))
				cri.addOrder(Order.desc(sortBy.substring(1)));
			else
				cri.addOrder(Order.asc(sortBy));

		List<T> list = (List<T>)cri.list();
		cri = sessionFactory.getCurrentSession()
			.createCriteria(clazz)
			.setProjection(Projections.rowCount());
		if (criterion != null)
			cri.add(criterion);
		int count = ((Long)cri.uniqueResult()).intValue();
		FetchResult<T> result = new FetchResult<T>();
		result.setData(list);
		result.setTotalRows(count);
		result.setStartRow(startRow);
		result.setEndRow(startRow + list.size());
		
		return result;
	}
}
