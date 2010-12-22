package com.huateng.frame.orm;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;

@SuppressWarnings("unchecked")
public abstract class IbatorDAOImpl<T, K, E extends IbatorExample> extends SqlMapClientDaoSupport implements IbatorDAO<T, K, E> {
	
	private String namespace;
	
    protected IbatorDAOImpl(SqlMapClient client, String namespace) {
        setSqlMapClient(client);
        setNamespace(namespace);
    }

    public int countByExample(E example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject(namespace + ".ibatorgenerated_countByExample", example);
        return count;
    }

    public int deleteByExample(E example) {
        int rows = getSqlMapClientTemplate().delete(namespace + ".ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(K key) {
        int rows = getSqlMapClientTemplate().delete(namespace + ".ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(T record) {
        getSqlMapClientTemplate().insert(namespace + ".ibatorgenerated_insert", record);
    }

    public void insertSelective(T record) {
        getSqlMapClientTemplate().insert(namespace + ".ibatorgenerated_insertSelective", record);
    }
    
    public List<T> selectByExample(E example) {
        List<T> list = getSqlMapClientTemplate().queryForList(namespace + ".ibatorgenerated_selectByExample", example);
        return list;
    }
    
    public List<T> selectByExample(E example, int skipResults, int maxResults) {
        return getSqlMapClientTemplate().queryForList(namespace + ".ibatorgenerated_selectByExample", example, skipResults, maxResults);
    }

	public T selectByPrimaryKey(K key) {
        T record = (T) getSqlMapClientTemplate().queryForObject(namespace + ".ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(T record) {
        int rows = getSqlMapClientTemplate().update(namespace + ".ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(T record) {
        int rows = getSqlMapClientTemplate().update(namespace + ".ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
}
