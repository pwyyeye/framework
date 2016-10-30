/*
 * @(#)BaseDAO.java	2009-10-15 10:14:23
 *
 * Copyright 2009 SOUEAST MOTOR coporation, Inc. All rights reserved.
 * coded by chelson. Use is subject to license terms.
 */
package common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import common.businessObject.BaseBusinessObject;
import common.value.BaseVO;
import common.value.PageList;

/**
 * 
 * @author Chelson
 * @version 1.0 2009-10-15 10:14:23
 * @see common.dao.hibernate.BaseDAOImpl.
 */
public interface BaseDAO<T, ID extends Serializable> {
	/**
	 * findByExample方法中Map[]类型参数中关联表在POJO中对应的变量名
	 */
	public static final String CHILD_TABLE_FIELD = "CHILD_TABLE_FIELD";
	/**
	 * findByExample方法中Map[]类型参数中关联表在POJO中对应的变量值
	 */
	public static final String CHILD_TABLE_VALUE = "CHILD_TABLE_VALUE";
	/**
	 * 是否模糊查询
	 */
	public static final String ENABLE_LIKE = "ENABLE_LIKE";

	public List findByExample(final T instance);
	//public List findByExample(final BaseBusinessObject instance);
	

	//public List loadAll(Class tableClass);

	//public void deleteAll(Class tableClass);

	public void deleteById(Serializable id, Class pojoClass);
	public void deleteById(Integer id, Class pojoClass);
	

	public void deleteByExample(T instance);
	//public void deleteByExample(BaseBusinessObject instance);

	public PageList findByExample(final T instance,
			final Integer firstResult, final Integer fetchSize);
	//public PageList findByExample(final BaseBusinessObject instance,
	//		final Integer firstResult, final Integer fetchSize);

	public PageList findByExample(final T instance,
			final List childTableMap, final Integer firstResult,
			final Integer fetchSize);
	/*public PageList findByExample(final BaseBusinessObject instance,
			final List childTableMap, final Integer firstResult,
			final Integer fetchSize);*/
	

	public Integer getRowCount(final DetachedCriteria dc);

	public List findByCriteria(DetachedCriteria criteria, int firstResult,
			int maxResults);

	public PageList findByCriteriaByPage(final DetachedCriteria dcriteria,
			final Integer firstResult, final Integer maxResults);

	public String save(T transientInstance);
	public Integer save(BaseBusinessObject transientInstance);
	public void saveOrUpdate(T transientInstance);
	//public String save(BaseBusinessStringObject transientInstance) ;
	public void delete(T persistentInstance);
	//public void delete(BaseBusinessObject persistentInstance);

	public T findById(Integer id, Class tableClass);

	public T loadById(Integer id, Class tableClass);
	public T findBoById(Serializable id, Class tableClass);

	public T loadBoById(Serializable id, Class tableClass);

	public List findByHql(String hql);

	public List findByHql(String hql, Object value);

	public List findByHql(String hql, Object[] value);

	public BaseVO loadVoById(final Integer id, final Class tableClass) ;

	public void saveOrUpdateAll(Collection entities);

	public void deleteAll(Collection entities);

	//public List findByNamedParam(String queryName, String paramName,
	//		Object value);

	//public List findByNamedParam(String queryString, String[] paramNames,
	//		Object[] values);

	//public List findByValueBean(String queryString, Object valueBean);

	public Integer updateOrDeleteBySql(final String sql);

	public void bulkUpdateOrDeleteBySql(final String[] sql);

	public Integer updateOrDeleteBySql(final String sql, final Object[] params);

	public void bulkUpdateOrDeleteBySql(final String[] sql,
			final Object[][] params);

	public int[] batchUpdateOrDeleteBySql(final String sql,
			final Object[][] params);
	
	public List<Map<String, Object>> callQueryProcedure(final String sql,
			final Object[] params) ;
	/**
	 * 调用update和delete操作的存储过程.
	 * @param sql 存储过程名
	 * @param params 传入存储过程的参数，如果参数为空存入null
	 * @return 返回命中的记录数.
	 * @throws RuntimeException
	 */
	public Integer callUpdateProcedure(final String sql,
			final Object[] params);
}
