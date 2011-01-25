package com.huateng.frame.gwt.server;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class HibernateServerHome<T> extends HibernateDaoSupport
{
	private Class<T> entityClass;
	
	protected HibernateServerHome(Class<T> entityClass)
	{
		this.entityClass = entityClass;
	}

	public Class<T> getEntityClass()
	{
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass)
	{
		this.entityClass = entityClass;
	}
	
	public Criteria createCriteria()
	{
		return getSession().createCriteria(entityClass);
	}
	
	public DetachedCriteria createDetachedCriteria()
	{
		return DetachedCriteria.forClass(entityClass);
	}

}
