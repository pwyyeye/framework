package com.xxl.os.dao.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.xxl.os.dao.OrganiseDAO;
import common.dao.impl.BaseDAOImpl;
import common.os.vo.exception.OSException;
import common.value.PageList;

public class OrganiseDAOImpl extends BaseDAOImpl implements OrganiseDAO {

	public Log logger = LogFactory.getLog(this.getClass());

	public List getSubOrganises(final int organise) throws OSException {
		try {
//			return (List) getHibernateTemplate().execute(
//					new HibernateCallback() {
//						public Object doInHibernate(Session session) {
//							String sql = "select {t.*} from sy_organise t where FIND_IN_SET(or_id, getChildOrganise("
//									+ organise + "))";
//							SQLQuery sqlQuery = session.createSQLQuery(sql);
//							List modules = sqlQuery.addEntity("t",
//									SyOrganise.class).list();
//							return modules;
//						}
//					});
			return null;

		} catch (Exception e) {
			logger.error("获取子机构失败：", e);
			throw new OSException("获取子机构失败：", e);
		}

	}
	public PageList getSubOrganiseList(final int organise) throws OSException {
		try {
//			return (PageList) getHibernateTemplate().execute(
//					new HibernateCallback() {
//						public Object doInHibernate(Session session) {
//							String sql = "select {t.*} from sy_organise t where FIND_IN_SET(or_id, getChildOrganise("
//									+ organise + "))";
//							SQLQuery sqlQuery = session.createSQLQuery(sql);
//							List modules = sqlQuery.addEntity("t",
//									SyOrganise.class).list();
//							Iterator iter=modules.iterator();
//							List results=new ArrayList();
//							while(iter.hasNext()){
//								SyOrganise bo=(SyOrganise)iter.next();
//								results.add(bo.toVO());
//							}
//							return new PageList(results);
//						}
//					});
			return null;

		} catch (Exception e) {
			logger.error("获取子机构失败：", e);
			throw new OSException("获取子机构失败：", e);
		}

	}
}
