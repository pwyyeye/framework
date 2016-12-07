/*
 * @(#)BaseDAOImpl.java	2009-10-13 10:27:36
 *
 * Copyright 2009 SOUEAST MOTOR coporation, Inc. All rights reserved.
 * coded by chelson. Use is subject to license terms.
 */
package common.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import common.businessObject.BaseAbstractBo;
import common.businessObject.BaseBusinessObject;
import common.dao.BaseDAO;
import common.utils.SemAppUtils;
import common.value.BaseVO;
import common.value.PageList;

/**
 * 
 * @author Chelson
 * @version 1.0 2009-10-13 10:27:36
 * @see
 */
@Repository
public class BaseDAOImpl<T, ID extends Serializable> implements BaseDAO<T, ID> {

	private static final Log logger = LogFactory.getLog(BaseDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	protected final Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 注意该方法只能对父表以及子表进行条件查询，对子表的关联设置没有作用
	 */
	public List findByExample(final T instance) {
		logger.debug("finding" + instance.getClass() + " instance by example");
		Criteria criteria = getCurrentSession().createCriteria(
				instance.getClass()).add(Example.create(instance));
		handleReferenceExample(criteria, instance);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);

		List results = criteria.list();
		logger.debug("find by example successful, result size: " + results.size());
		return results;
	}

	/*public List findByExample(final BaseBusinessObject instance) {
		log.debug("finding" + instance.getClass() + " instance by example");
		Criteria criteria = getCurrentSession().createCriteria(
				instance.getClass()).add(Example.create(instance));
		handleReferenceExample(criteria, instance);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);

		List results = criteria.list();
		log.debug("find by example successful, result size: " + results.size());
		return results;
	}*/

	/**
	 * 预取延迟加载字段，凡是需要用到的延迟加载字段全部放入数组prefetchDelayedFields中
	 * 
	 * @param instance
	 *            查询实例
	 * @param prefetchDelayedFields
	 *            延迟加载的字段
	 * @deprecated
	 */
	public List findByExample(final T instance,
			final String[] prefetchDelayedFields) {

		Criteria criteria = getCurrentSession().createCriteria(
				instance.getClass()).add(Example.create(instance));
		handleReferenceExample(criteria, instance);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);

		List tempResult = criteria.list();
		logger.debug("find by example successful, result size: "
				+ tempResult.size());
		try {
			Iterator iter = tempResult.iterator();
			while (iter.hasNext()) {
				Object s = iter.next();
				Class classe = s.getClass();
				for (int i = 0; i < prefetchDelayedFields.length; i++) {
					logger.debug("变量" + prefetchDelayedFields[i] + "被预取");
					Method method = classe.getMethod(SemAppUtils
							.getGetMethodName(prefetchDelayedFields[i]), null);
					Object o = method.invoke(s, null);
					o.toString();
				}
			}
		} catch (NoSuchMethodException re) {
			logger.error("指定的字段不存在", re);
			throw new RuntimeException(re);
		} catch (IllegalArgumentException e) {
			logger.error("IllegalArgumentException", e);
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			logger.error("IllegalAccessException", e);
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			logger.error("IllegalAccessException", e);
			throw new RuntimeException(e);
		}
		return tempResult;

	}

	/**
	 * 取得表tableClass所有的记录
	 * 
	 * @param tableClass
	 *            表对应的pojo
	 */
	/*
	 * public List loadAll(Class tableClass) {
	 * 	log.debug("start to load all lines of table" + tableClass); List result =
	 * 	getCurrentSession().get(tableClass);
	 * 	log.debug("load all lines of table finished"); return result; 
	 * }
	 */

	/**
	 * 删除表tableClass所有的记录
	 * 
	 * @param tableClass
	 *            表对应的pojo
	 */
	/*
	 * public void deleteAll(Class tableClass) {
	 * 	log.debug("start to delete all lines of table" + tableClass);
	 * 	getHibernateTemplate().deleteAll(loadAll(tableClass));
	 * 	log.debug("delete all lines of table finished"); 
	 * }
	 */

	/**
	 * 删除指定的记录，instance中存储delete sql中where子句的内容
	 * 如果instance中所有字段均为空则不会删除任何记录，如果pojo类中存在基本类型的数据则会抛出异常.
	 */
	public void deleteByExample(T instance) {
		logger.debug("start to delete " + instance.getClass() + "by example");
		if (SemAppUtils.isAllFieldsNull(instance)) {
			logger.debug("all fields of instance " + instance.getClass()
					+ "is null");
			return;
		}
		getCurrentSession().delete(findByExample(instance));
		logger.debug("delete by Example success");
	}

	/**
	 * 分页查找
	 */
	public PageList findByExample(final T instance,
			final Integer firstResult, final Integer fetchSize) {
		logger.debug("finding" + instance.getClass() + " instance by example");
		try {
			Criteria criteria = getCurrentSession().createCriteria(
					instance.getClass()).add(Example.create(instance));

			int rowCount = ((Integer) criteria.setProjection(
					Projections.rowCount()).uniqueResult()).intValue();
			criteria.setProjection(null);
			if (fetchSize.intValue() > 0) {
				criteria.setFirstResult(firstResult.intValue());
				criteria.setMaxResults(fetchSize.intValue());
			}
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);

			handleReferenceExample(criteria, instance);

			List results = criteria.list();
			logger.debug("find by example successful, result size: "
					+ results.size());
			List voResults = new ArrayList(results.size());
			for (int i = 0; i < results.size(); i++) {
				BaseAbstractBo bbo = (BaseAbstractBo) results.get(i);
				Object vo = bbo.toVO();
				voResults.add(vo);
			}
			PageList pageList = new PageList();
			pageList.setResults(rowCount);
			pageList.setItems(voResults);
			return pageList;
		} catch (RuntimeException re) {
			logger.error("find by example failed", re);
			throw re;
		}
	}

	public void deleteById(Integer id, Class pojoClass) {
		deleteById((Serializable) id, pojoClass);
	}

	public void deleteById(Serializable id, Class pojoClass) {
		logger.debug("delete" + pojoClass + " by id" + id);
		try {
			/*BaseAbstractBo o = (BaseAbstractBo) pojoClass.newInstance();
			// String setMethod="setId";
			Method setMethod = pojoClass.getMethod("setId",
					new Class[] { Serializable.class });
			setMethod.invoke(o, new Object[] { id });
			// o.setId(id);
			getCurrentSession().delete(o);*/
			
			/*Session session = sessionFactory.openSession();
	        Object object = (Object) session.get(pojoClass, id);
	        session.beginTransaction();
	        if (null != object) {
	            session.delete(object);
	        }
	        session.getTransaction().commit();
	        session.close();*/
			
			Session session = getCurrentSession();
	        Object object = (Object) session.get(pojoClass, id);
	        if (null != object) {
	            session.delete(object);
	        }
			
		} /*catch (InstantiationException e) {
			log.error("initialize " + pojoClass + "failed", e);
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			log.error("initialize " + pojoClass + "failed", e);
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			log.error("initialize " + pojoClass + "failed", e);
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			log.error("initialize " + pojoClass + "failed", e);
			throw new RuntimeException(e);
		}*/ catch (IllegalArgumentException e) {
			logger.error("initialize " + pojoClass + "failed", e);
			throw new RuntimeException(e);
		}

		logger.debug("delete by id success");
	}

	/**
	 * 在public List findByExample(final Object instance, final Map[]
	 * childTableMap) 的基础上添加分页查询功能
	 */
	public PageList findByExample(final T instance,
			final List childTableMap, final Integer firstResult,
			final Integer fetchSize) {
		logger.debug("finding" + instance.getClass() + " instance by example");
		try {
			Criteria criteria = getCurrentSession().createCriteria(
					instance.getClass()).add(Example.create(instance));

			int rowCount = ((Integer) criteria.setProjection(
					Projections.rowCount()).uniqueResult()).intValue();
			criteria.setProjection(null);
			for (int i = 0; i < childTableMap.size(); i++) {
				String childTableField = (String) ((Map) childTableMap.get(i))
						.get(BaseDAO.CHILD_TABLE_FIELD);
				Object queryValue = ((Map) childTableMap.get(i))
						.get(BaseDAO.CHILD_TABLE_VALUE);
				if (queryValue != null)
					criteria.createCriteria(childTableField).add(
							Example.create(queryValue));
			}
			if (fetchSize.intValue() > 0) {
				criteria.setFirstResult(firstResult.intValue());
				criteria.setMaxResults(fetchSize.intValue());
			}
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			List results = criteria.list();
			logger.debug("find by example successful, result size: "
					+ results.size());
			List voResults = new ArrayList(results.size());
			for (int i = 0; i < results.size(); i++) {
				BaseAbstractBo bbo = (BaseAbstractBo) results.get(i);
				Object vo = bbo.toVO();
				voResults.add(vo);
			}
			PageList pageList = new PageList();
			pageList.setResults(rowCount);
			pageList.setItems(voResults);
			return pageList;
		} catch (RuntimeException re) {
			logger.error("find by example failed", re);
			throw re;
		}
	}

	public PageList findByCriteriaByPage(final DetachedCriteria dcriteria,
			final Integer firstResult, final Integer maxResults) {
		Criteria criteria = dcriteria
				.getExecutableCriteria(getCurrentSession());
		int rowCount = ((Long) criteria
				.setProjection(Projections.rowCount()).uniqueResult())
				.intValue();
		criteria.setProjection(null);
		if (maxResults.intValue() > 0) {
			criteria.setFirstResult(firstResult.intValue());
			criteria.setMaxResults(maxResults.intValue());
		}
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		List results = criteria.list();
		logger.debug("find by example successful, result size: " + results.size());
		List voResults = new ArrayList(results.size());
		for (int i = 0; i < results.size(); i++) {
			BaseAbstractBo bbo = (BaseAbstractBo) results.get(i);
			Object vo = bbo.toVO();
			voResults.add(vo);
		}
		PageList pageList = new PageList();
		pageList.setResults(rowCount);
		pageList.setItems(voResults);
		return pageList;
	}

	public List findByCriteria(DetachedCriteria dcriteria, int firstResult,
			int maxResults) {
		dcriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		Criteria criteria = dcriteria
				.getExecutableCriteria(getCurrentSession());
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResults);
		return criteria.list();
	}

	public Integer getRowCount(final DetachedCriteria dc) {
		Criteria criteria = dc.getExecutableCriteria(getCurrentSession());
		Integer count = (Integer) criteria
				.setProjection(Projections.rowCount()).uniqueResult();
		criteria.setProjection(null);
		return count;
	}

	public T findById(Integer id, Class tableClass) {
		logger.debug("getting" + tableClass.getName() + " instance with id: " + id);
		try {
			T instance = (T) getCurrentSession().get(tableClass, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("getting instance error id[" + id + "]", re);
			throw re;
		}
	}

	public T loadById(Integer id, Class tableClass) {
		logger.debug("loading" + tableClass.getName() + " instance with id: " + id);
		try {
			T instance = (T) getCurrentSession().load(tableClass, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public T loadBoById( Class tableClass,Serializable id){
		return this.loadBoById(id, tableClass);
	}

	public BaseVO loadVoById(final Integer id, final Class tableClass) {
		logger.debug("loading" + tableClass.getName() + " instance with id: " + id);
		try {
			BaseBusinessObject instance = (BaseBusinessObject) getCurrentSession()
					.load(tableClass, id);
			return (BaseVO) instance.toVO();
		} catch (RuntimeException re) {
			logger.error("find by example failed", re);
			throw re;
		}

	}

	public T findBoById(Serializable id, Class tableClass) {
		logger.debug("getting" + tableClass.getName() + " instance with id: " + id);
		try {
			T instance = (T) getCurrentSession().get(tableClass, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public T loadBoById(Serializable id, Class tableClass) {
		logger.debug("loading" + tableClass.getName() + " instance with id: " + id);
		try {
			T instance = (T) getCurrentSession().load(tableClass, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void delete(T persistentInstance) {
		logger.debug("deleting" + persistentInstance.getClass() + " instance");
		try {
			getCurrentSession().delete(persistentInstance);
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public String save(T transientInstance) {
		logger.debug("saving " + transientInstance.getClass() + " instance");
		try {
			String id = String.valueOf(getCurrentSession().save(transientInstance));
			logger.debug("save successful");
			return id;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Integer save(BaseBusinessObject transientInstance) {
		logger.debug("saving " + transientInstance.getClass() + " instance");
		try {
			getCurrentSession().save(transientInstance);
			logger.debug("save successful");
			return transientInstance.getId();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public void saveOrUpdate(T transientInstance) {
		logger.debug("saving " + transientInstance.getClass() + " instance");
		try {
			getCurrentSession().saveOrUpdate(transientInstance);
			logger.debug("save successful");
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/*public String save(BaseBusinessStringObject transientInstance) {
		log.debug("saving " + transientInstance.getClass() + " instance");
		try {
			getCurrentSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
			return ID;
		} catch (RuntimeException re) {
			throw re;
		}
	}*/

	/**
	 * Execute a query for persistent instances.
	 * 
	 * @param queryString
	 *            a query expressed in Hibernate's query language
	 * @return a List containing 0 or more persistent instances
	 * @throws org.springframework.dao.DataAccessException
	 *             in case of Hibernate errors
	 * @see org.hibernate.Session#createQuery
	 */
	public List findByHql(String hql) {
		logger.debug("find by Hql, hql is " + hql);
		Query query = getCurrentSession().createQuery(hql);
		return query.list();
	}

	/**
	 * Execute a query for persistent instances, binding one value to a "?"
	 * parameter in the query string.
	 * 
	 * @param queryString
	 *            a query expressed in Hibernate's query language
	 * @param value
	 *            the value of the parameter
	 * @return a List containing 0 or more persistent instances
	 * @throws org.springframework.dao.DataAccessException
	 *             in case of Hibernate errors
	 * @see org.hibernate.Session#createQuery
	 */
	public List findByHql(String hql, Object value) {
		logger.debug("find by Hql, hql is " + hql + ", value is " + value);
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter(0, value);
		return query.list();
	}

	/**
	 * Execute a query for persistent instances, binding a number of values to
	 * "?" parameters in the query string.
	 * 
	 * @param queryString
	 *            a query expressed in Hibernate's query language
	 * @param values
	 *            the values of the parameters
	 * @return a List containing 0 or more persistent instances
	 * @throws org.springframework.dao.DataAccessException
	 *             in case of Hibernate errors
	 * @see org.hibernate.Session#createQuery
	 */
	public List findByHql(String hql, Object[] value) {
		logger.debug("find by Hql, hql is " + hql + ", value is " + value);
		Query query = getCurrentSession().createQuery(hql);
		for (int i = 0; i < value.length; i++) {
			query.setParameter(i + 1, value[i]);
		}
		return query.list();
	}

	/**
	 * Update/delete all objects according to the given query. Return the number
	 * of entity instances updated/deleted.
	 * 
	 * @param queryString
	 *            an update/delete query expressed in Hibernate's query language
	 * @return the number of instances updated/deleted
	 * @throws org.springframework.dao.DataAccessException
	 *             in case of Hibernate errors
	 * @see org.hibernate.Session#createQuery
	 * @see org.hibernate.Query#executeUpdate
	 */
	// public int bulkUpdate(String queryString) {
	// log.debug("bulkUpdate queryString is " + queryString);
	// return getHibernateTemplate().bulkUpdate(queryString);
	// }

	/**
	 * Update/delete all objects according to the given query. Return the number
	 * of entity instances updated/deleted.
	 * 
	 * @param queryString
	 *            an update/delete query expressed in Hibernate's query language
	 * @param value
	 *            the value of the parameter
	 * @return the number of instances updated/deleted
	 * @throws org.springframework.dao.DataAccessException
	 *             in case of Hibernate errors
	 * @see org.hibernate.Session#createQuery
	 * @see org.hibernate.Query#executeUpdate
	 */
	// public int bulkUpdate(String queryString, Object value) {
	// log.debug("bulkUpdate queryString is " + queryString + ", value is "
	// + value);
	// return getHibernateTemplate().bulkUpdate(queryString, value);
	// }

	/**
	 * Update/delete all objects according to the given query. Return the number
	 * of entity instances updated/deleted.
	 * 
	 * @param queryString
	 *            an update/delete query expressed in Hibernate's query language
	 * @param values
	 *            the values of the parameters
	 * @return the number of instances updated/deleted
	 * @throws org.springframework.dao.DataAccessException
	 *             in case of Hibernate errors
	 * @see org.hibernate.Session#createQuery
	 * @see org.hibernate.Query#executeUpdate
	 */
	// public int bulkUpdate(String queryString, Object[] value) {
	// log.debug("bulkUpdate queryString is " + queryString + ", value is "
	// + value);
	// return getHibernateTemplate().bulkUpdate(queryString, value);
	// }

	/**
	 * Save or update all given persistent instances, according to its id
	 * (matching the configured "unsaved-value"?). Associates the instances with
	 * the current Hibernate Session.
	 * 
	 * @param entities
	 *            the persistent instances to save or update (to be associated
	 *            with the Hibernate Session)
	 * @throws DataAccessException
	 *             in case of Hibernate errors
	 * @see org.hibernate.Session#saveOrUpdate(Object)
	 */
	public void saveOrUpdateAll(Collection entities) {
		getCurrentSession().saveOrUpdate(entities);
	}

	/**
	 * Delete all given persistent instances.
	 * <p>
	 * This can be combined with any of the find methods to delete by query in
	 * two lines of code.
	 * 
	 * @param entities
	 *            the persistent instances to delete
	 * @throws org.springframework.dao.DataAccessException
	 *             in case of Hibernate errors
	 * @see org.hibernate.Session#delete(Object)
	 */
	public void deleteAll(Collection entities) {
		getCurrentSession().delete(entities);
	}

	/**
	 * Execute a query for persistent instances, binding one value to a ":"
	 * named parameter in the query string.
	 * 
	 * @param queryName
	 *            the name of a Hibernate query in a mapping file
	 * @param paramName
	 *            the name of parameter
	 * @param value
	 *            the value of the parameter
	 * @return a List containing 0 or more persistent instances
	 * @throws org.springframework.dao.DataAccessException
	 *             in case of Hibernate errors
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	/*
	 * public List findByNamedParam(String queryName, String paramName, Object
	 * value) { return getHibernateTemplate().findByNamedParam(queryName,
	 * paramName, value); }
	 */

	/**
	 * Execute a query for persistent instances, binding a number of values to
	 * ":" named parameters in the query string.
	 * 
	 * @param queryString
	 *            a query expressed in Hibernate's query language
	 * @param paramNames
	 *            the names of the parameters
	 * @param values
	 *            the values of the parameters
	 * @return a List containing 0 or more persistent instances
	 * @throws org.springframework.dao.DataAccessException
	 *             in case of Hibernate errors
	 * @see org.hibernate.Session#getNamedQuery(String)
	 */
	/*
	 * public List findByNamedParam(String queryString, String[] paramNames,
	 * Object[] values) { return
	 * getHibernateTemplate().findByNamedParam(queryString, paramNames, values);
	 * }
	 */

	/**
	 * Execute a query for persistent instances, binding the properties of the
	 * given bean to <i>named</i> parameters in the query string.
	 * 
	 * @param queryString
	 *            a query expressed in Hibernate's query language
	 * @param valueBean
	 *            the values of the parameters
	 * @return a List containing 0 or more persistent instances
	 * @throws org.springframework.dao.DataAccessException
	 *             in case of Hibernate errors
	 * @see org.hibernate.Query#setProperties
	 * @see org.hibernate.Session#createQuery
	 */
	/*
	 * public List findByValueBean(String queryString, Object valueBean) {
	 * return getHibernateTemplate().findByValueBean(queryString, valueBean); }
	 */

	/**
	 * update or delete database with a dml.
	 * 
	 * @param sql
	 *            sql to be execute.
	 */
	public Integer updateOrDeleteBySql(final String sql) {
		logger.debug("updateOrDeleteBySql sql is " + sql);
		if (sql == null || sql.indexOf("select") != -1)
			throw new RuntimeException(
					"method updateOrDeleteBySql could not accept a query sql");
		Query query = getCurrentSession().createSQLQuery(sql);
		Integer resultSize = query.executeUpdate();
		return resultSize;
	}

	/**
	 * bulk update or delete data by sql
	 * 
	 * @param sql
	 *            sql to be execute.
	 */
	public void bulkUpdateOrDeleteBySql(final String[] sql) {
		logger.debug("buldUpdateOrDeleteBySql sql is " + sql);
		for (int i = 0; i < sql.length; i++) {
			if (sql[i] == null || sql[i].indexOf("select") != -1)
				throw new RuntimeException(
						"method updateOrDeleteBySql could not accept a query sql");
		}
		for (int i = 0; i < sql.length; i++) {
			Query query = getCurrentSession().createSQLQuery(sql[i]);
			query.executeUpdate();
		}
	}

	/**
	 * update or delete database with a dml.
	 * 
	 * @param sql
	 *            sql to be execute.
	 * @param params
	 *            named param of the sql.
	 */
	public Integer updateOrDeleteBySql(final String sql, final Object[] params) {
		logger.debug("updateOrDeleteBySql sql is " + sql + "params is " + params);
		if (sql == null || sql.indexOf("select") != -1)
			throw new RuntimeException(
					"method updateOrDeleteBySql could not accept a query sql param");
		Query query = getCurrentSession().createSQLQuery(sql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		Integer resultSize = query.executeUpdate();
		return resultSize;
	}

	/**
	 * execute all sql by PreparedStatement.
	 * 
	 * @param sql
	 *            sql to be execute.
	 * @param params
	 *            every top dimension refer to a sql.
	 */
	public void bulkUpdateOrDeleteBySql(final String[] sql,
			final Object[][] params) {
		logger.debug("bulkUpdateOrDeleteBySql sql is " + sql + "params is "
				+ params);
		for (int i = 0; i < sql.length; i++) {
			if (sql[i] == null || sql[i].indexOf("select") != -1)
				throw new RuntimeException(
						"method updateOrDeleteBySql could not accept a query sql as param");
		}
		for (int i = 0; i < sql.length; i++) {
			Query query = getCurrentSession().createSQLQuery(sql[i]);
			for (int j = 0; j < params[i].length; j++) {
				query.setParameter(j + 1, params[i][j]);
			}
			query.executeUpdate();
		}
	}

	/**
	 * execute sql by PreparedStatement, params as it's batch param.
	 * 
	 * @param sql
	 *            sql to be execute.
	 * @param params
	 *            every top dimension refer to a sql.
	 */
	public int[] batchUpdateOrDeleteBySql(final String sql,
			final Object[][] params) {
		logger.debug("batchUpdateOrDeleteBySql, sql is " + sql + ", params is "
				+ params);
		if (sql == null || sql.indexOf("select") != -1)
			throw new RuntimeException(
					"method batchUpdateOrDeleteBySql could not accept a query sql param");
		Query query = getCurrentSession().createSQLQuery(sql);
		int[] resultSize = new int[params.length];
		for (int i = 0; i < params.length; i++) {
			for (int j = 0; j < params[i].length; j++) {
				query.setParameter(j + 1, params[i][j]);
			}
			resultSize[i] = query.executeUpdate();
		}
		return resultSize;
	}

	public Integer callUpdateProcedure(final String sql, final Object[] params) {
		logger.debug("start to call procedure" + sql + ", params is " + params);
		final ArrayList<Integer> returnHitCount = new ArrayList<Integer>();
		getCurrentSession().doWork(new Work() {
			public void execute(Connection conn) throws SQLException {
				try {
					CallableStatement cs = conn.prepareCall(sql);
					if (params != null) {
						logger.debug("params is not null it's members is "
								+ Arrays.asList(params));
						for (int i = 0; i < params.length; i++) {
							cs.setObject(i + 1, params[i]);
						}
					} else
						logger.debug("params is null");
					int hitCount = cs.executeUpdate();
					cs.close();
					logger.debug("call procedure ended, hitted record counts is "
							+ hitCount);
					returnHitCount.add(new Integer(hitCount));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		return returnHitCount.get(0);
	}

	public List<Map<String, Object>> callQueryProcedure(final String sql,
			final Object[] params) {
		logger.debug("start to call procedure" + sql + ", params is " + params);
		final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		getCurrentSession().doWork(new Work() {
			public void execute(Connection conn) throws SQLException {
				CallableStatement cs = conn.prepareCall(sql);
				if (params != null) {
					logger.debug("params is not null it's members is "
							+ Arrays.asList(params));
					for (int i = 0; i < params.length; i++) {
						cs.setObject(i + 1, params[i]);
					}
				} else
					logger.debug("params is null");
				ResultSet rs = cs.executeQuery();
				ResultSetMetaData metaData = rs.getMetaData();
				int colCount = metaData.getColumnCount();
				while (rs.next()) {
					Map<String, Object> map = new HashMap<String, Object>();
					for (int i = 1; i <= colCount; i++) {
						String colName = metaData.getColumnName(i);
						map.put(colName, rs.getObject(colName));
					}
					result.add(map);
				}
				rs.close();
				cs.close();
			}
		});
		return result;
	}
	
	/**
	 2      * 通过SQL查询，将结果集转换成Map对象，列名作为键(适用于有返回结果集的存储过程或查询语句)
	 3      * @param queryString
	 4      * @param params
	 5      * @return
	 6      */
	public List<Map<String,Object>> find_sql_toMap(String queryString,Object[] params){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(queryString);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if(null!=params){
            for(int i=0;i<params.length;i++){
                query.setParameter(i, params[i]);
            }
        }
        List<java.util.Map<String,Object>> list = query.list();
        return list;
    }
	
	/**
     * 通过SQL执行无返回结果的语句，执行修改、删除、添加等(适用于无结果集返回SQL语句，不能用于存储过程)
     * @param queryString
     * @param params
     */
    public void executeSql(String queryString,Object[] params){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(queryString);
        if(null!=params){
            for(int i=0;i<params.length;i++){
                query.setParameter(i, params[i]);
            }
        }
        query.executeUpdate();
    }
    
    /**
     * 通过SQL执行无返回结果的存储过程(仅限于存储过程)
     * @param queryString
     * @param params
     */
    public void executeVoidProcedureSql(final String queryString,final Object[] params) throws Exception{
        Session session = sessionFactory.getCurrentSession();
        session.doWork(new Work(){
          public void execute(Connection conn) throws SQLException {
            ResultSet rs = null;
            CallableStatement call = conn.prepareCall("{" + queryString + "}");
            if (null != params) {
                for (int i = 0; i <params.length; i++) {
                    call.setObject(i+1, params[i]);
                }
            }
            rs = call.executeQuery();
            call.close();
            rs.close();
          }
       });
    }

	/**
	 * handle reference table example.
	 * 
	 * @param instance
	 *            query instance.
	 */
	private void handleReferenceExample(Criteria criteria,
			T instance) {
		logger.debug("handleReferenceExample start to handle "
				+ instance.getClass() + " reference table query field");
		try {
			Field[] fields = instance.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				logger.debug("start to handle field " + fields[i].getName());
				Object refTableField = (Object) instance
						.getClass()
						.getDeclaredMethod(
								SemAppUtils.getGetMethodName(fields[i]
										.getName()), null)
						.invoke(instance, null);
				if ((refTableField != null)
						&& (refTableField instanceof BaseAbstractBo)) {
					logger.debug(fields[i].getName()
							+ " is a BaseAbstractBo, its type is "
							+ refTableField.getClass() + ", start to handle it");
					criteria.createCriteria(fields[i].getName()).add(
							Example.create(refTableField));

				} else if (refTableField != null
						&& (refTableField instanceof Collection)) {
					logger.debug(fields[i].getName()
							+ " is a Collection, its type is "
							+ refTableField.getClass() + ", start to handle it");
					Collection col = (Collection) refTableField;
					if (col.size() > 0) {
						Object refField = (Object) col.iterator().next();
						criteria.createCriteria(fields[i].getName()).add(
								Example.create(refField));
					}
				}
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}

		logger.debug("handleReferenceExample end");
	}

	/*public void deleteByExample(BaseBusinessObject instance) {
		deleteByExample((BaseAbstractBo) instance);

	}*/

	/*public PageList findByExample(BaseBusinessObject instance,
			Integer firstResult, Integer fetchSize) {
		return findByExample((BaseAbstractBo) instance, firstResult, fetchSize);
	}*/

	/*public PageList findByExample(BaseBusinessObject instance,
			List childTableMap, Integer firstResult, Integer fetchSize) {
		return findByExample((BaseAbstractBo) instance, childTableMap,
				firstResult, fetchSize);
	}*/

	/*public void delete(BaseBusinessObject persistentInstance) {
		log.debug("deleting" + persistentInstance.getClass() + " instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			throw re;
		}

	}*/
}
