package com.huateng.frame.gwt.server;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

public abstract class HibernateServerHome<T, K extends Serializable> extends HibernateDaoSupport
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
	
	public void save(T entity)
	{
		Object id = getHibernateTemplate().save(entity);
		System.out.println(id);
	}

	public void saveOrUpdate(T entity)
	{
		getHibernateTemplate().saveOrUpdate(entity);
	}
	
	public T load(K key)
	{
		return getHibernateTemplate().load(entityClass, key);
	}
}