package com.xxl.baseService.service;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Properties;

import javax.ejb.SessionContext;
import javax.jms.ObjectMessage;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.ejb.support.AbstractStatelessSessionBean;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.xxl.facade.HelperRemote;

import common.exception.BaseBusinessException;
import common.exception.BaseException;
import common.exception.CommonException;
import common.utils.SemAppConstants;
import common.utils.SemAppUtils;
import common.web.bean.SessionUserBean;

public abstract class BaseFrameWorkService{
	public Log logger = LogFactory.getLog(this.getClass());

//	protected AlphaUddi alpha;

	@Autowired
	protected HelperRemote helperRemote;

	public static final String SESSION_CALLED_TIMES = "SESSION_CALLED_TIMES";

	public static final String TRANSACTION_BINDED_THREAD = "TRANSACTION_BINDED_THREAD";

	private Boolean sessionCreated = Boolean.FALSE;


//	public String sendToQueue(QueueSession qSession, java.io.Serializable obj,
//			QueueSender qSender, SessionUserBean user, Integer subQueueID)
//			throws Exception {
//		logger.debug("sendToQueue: " + obj.toString());
//		try {
//			SemMessageObject messageObject = new SemMessageObject(user, obj,
//					subQueueID);
//			ObjectMessage message = qSession.createObjectMessage(messageObject);
//			qSender.send(message);
//			logger.debug("Finished sending mnessage to queue");
//			return message.getJMSMessageID();
//		} catch (Exception e) {
//			logger.error(e.getLocalizedMessage(), e);
//			throw e;
//		}
//	}
//
//	public Document invokeXmlServices(Integer serviceId, String argments)
//			throws CommonException, BaseBusinessException {
//		Document doc = null;
//		try {
//			if (alpha == null)
//				alpha = JNDILookupUtils.getAlphaUddi();
//			doc = (alpha.invokeXmlServices(serviceId, argments));
//		} catch (NamingException e) {
//			doException("创建COMMON对象失败", e);
//		} catch (RemoteException e) {
//			doException("调用ALPHA UDDI失败", e);
//		}
//		return doc;
//	}

	public Serializable invokeEjbServices(Integer serviceId,
			Serializable[] argments) throws CommonException,
			BaseBusinessException {
		Serializable returnObj = null;
//		try {
//			if (alpha == null)
//				alpha = JNDILookupUtils.getAlphaUddi();
//			returnObj = (alpha.invokeEjbServices(serviceId, argments));
//
//		} catch (BaseBusinessException ee) {
//			logger.debug("catch by business" + ee.getClass());
//			throw new BaseBusinessException(ee.getMessage());
//		} catch (NamingException e) {
//			doException("创建COMMON对象失败", e);
//		} catch (RemoteException e) {
//			logger.debug("" + e.getClass());
//			doException("调用ALPHA UDDI失败", e);
//		}
		return returnObj;
	}

	public Serializable invokeWebServices(Integer serviceId,
			Serializable[] argments) throws CommonException,
			BaseBusinessException {
		Serializable returnObj = null;
//		try {
//			if (alpha == null)
//				alpha = JNDILookupUtils.getAlphaUddi();
//			returnObj = (alpha.invokeWebServices(serviceId, argments));
//		} catch (NamingException e) {
//			doException("创建COMMON对象失败", e);
//		} catch (RemoteException e) {
//			doException("调用ALPHA UDDI失败", e);
//		}
		return returnObj;
	}

	public Serializable invokeWebServicesByAxis(Integer serviceId,
			Serializable[] argments) throws CommonException,
			BaseBusinessException {
		Serializable returnObj = null;
//		try {
//			if (alpha == null)
//				alpha = JNDILookupUtils.getAlphaUddi();
//			returnObj = (alpha.invokeWebServicesByAxis(serviceId, argments));
//		} catch (NamingException e) {
//			doException("创建COMMON对象失败", e);
//		} catch (RemoteException e) {
//			doException("调用ALPHA UDDI失败", e);
//		}
		return returnObj;
	}

//	public Serializable invokeWebServicesByAxis(Integer serviceId,
//			Serializable[] argments, OperationDesc oper)
//			throws CommonException, BaseBusinessException {
//		Serializable returnObj = null;
//		try {
//			if (alpha == null)
//				alpha = JNDILookupUtils.getAlphaUddi();
//			returnObj = (alpha.invokeWebServicesByAxis(serviceId, argments,
//					oper));
//		} catch (NamingException e) {
//			doException("创建COMMON对象失败", e);
//		} catch (RemoteException e) {
//			doException("调用ALPHA UDDI失败", e);
//		}
//		return returnObj;
//	}

//	protected boolean publishMessage(int messageID, String subject,
//			String content) {
//		return SemAppUtils.publishMessage(messageID, subject, content);
//	}
//
//	protected boolean publishMessage(int messageID, String subject,
//			String content, String[] attachs) {
//		return SemAppUtils.publishMessage(messageID, subject, content, attachs);
//	}
//
//	protected void sendAppPushMessage(Integer type, String users, String title,
//			String message,Integer bzId) throws BaseException, BaseBusinessException {
//		SemAppUtils.sendAppMessage(type, users, title, message,bzId);
//	}
//
//	public void sendAppBroadcast(String title, String message)
//			throws BaseException, BaseBusinessException {
//		SemAppUtils.sendAppBroadcast(title, message);
//	}

	protected Properties getProperties() {
		Properties properties = null;
		try {
		
			properties = helperRemote.getProperties();

		} catch (Exception e) {
			doException("创建Helper对象失败", e);
		}
		return properties;
	}

	public String getProperty(String name) {
		String property = null;
		try {
			
			property = helperRemote.getProperty(name);

		} catch (Exception e) {
			doException("创建Helper对象失败", e);
		}
		return property;
	}

	protected void doException(String errorMsg, Exception e)
			throws CommonException {
		logger.error(errorMsg, e);
		throw new CommonException(errorMsg);
	}

	/**
	 * 打开�?个session并绑定到当前线程.
	 * 
	 * @deprecated 该方法存在事务管理漏�?,建议改用doInTransaction方法.
	 */
//	public Session openHibernateSession() {
//		logger.debug("open session before dao operation");
//		SessionFactory sessionFactory = (SessionFactory) getBeanFactory()
//				.getBean("sessionFactory");
//		if (!TransactionSynchronizationManager.hasResource(sessionFactory)) {
//			Session session = getSession(sessionFactory);
//			logger
//					.debug("open a new session and bind to current thread, session's hashcode is "
//							+ session.hashCode());
//			TransactionSynchronizationManager.bindResource(sessionFactory,
//					new SessionHolder(session));
//			Transaction tx = session.beginTransaction();
//			TransactionSynchronizationManager.bindResource(
//					TRANSACTION_BINDED_THREAD, tx);
//			TransactionSynchronizationManager.bindResource(
//					SESSION_CALLED_TIMES, new Integer(1));
//			return session;
//		} else {
//			logger.debug("fetch a thread binded session");
//			Integer calledTimes = (Integer) unbindObjectFromTransactionSynchronizationManager(SESSION_CALLED_TIMES);
//			if (calledTimes != null)
//				TransactionSynchronizationManager.bindResource(
//						SESSION_CALLED_TIMES, new Integer(calledTimes
//								.intValue() + 1));
//			else
//				logger
//						.warn("add session called times failed, for the lost of the callTimes binded with this session");
//		}
//		return getSession(sessionFactory);
//	}

	/**
	 * 关闭当前线程中的session
	 * 
	 * @deprecated 该方法存在事务管理漏�?,建议改用doInTransaction方法.
	 */
//	public void closeHibernateSession() {
//		try {
//			Integer calledTimes = (Integer) unbindObjectFromTransactionSynchronizationManager(SESSION_CALLED_TIMES);
//			if (calledTimes != null && calledTimes.intValue() == 1) {
//				logger
//						.debug("session created by current thread, commit the transaction");
//				SessionFactory sessionFactory = (SessionFactory) getBeanFactory()
//						.getBean("sessionFactory");
//				logger.debug("execute releaseHibernateSession()");
//				Session session = getSession(sessionFactory);
//				unbindObjectFromTransactionSynchronizationManager(sessionFactory);
//				if (session != null && session.isOpen()) {
//					logger.debug("Closing single Hibernate Session");
//					Transaction tx = (Transaction) unbindObjectFromTransactionSynchronizationManager(TRANSACTION_BINDED_THREAD);
//					tx.commit();
//					closeSession(session, sessionFactory);
//					logger.debug("Session " + session.hashCode()
//							+ " has been close, current session status is "
//							+ session);
//				}
//			} else {
//				logger
//						.debug("current thread fetch a existed session, defer transaction commit to thread which session binded");
//				if (calledTimes != null)
//					TransactionSynchronizationManager.bindResource(
//							SESSION_CALLED_TIMES, new Integer(calledTimes
//									.intValue() - 1));
//			}
//		} catch (RuntimeException e) {
//			Transaction tx = (Transaction) unbindObjectFromTransactionSynchronizationManager(TRANSACTION_BINDED_THREAD);
//			if (tx != null) {
//				logger.debug("start to rollback transaction");
//				tx.rollback();
//			} else {
//				logger
//						.warn("the transaction has lost, so commit transaction action haven't complete.");
//			}
//			throw e;
//		}
//	}

	private Object unbindObjectFromTransactionSynchronizationManager(Object id) {
		Object o = TransactionSynchronizationManager.getResource(id);
		if (o != null)
			return TransactionSynchronizationManager.unbindResource(id);
		else
			return o;
	}

	private void closeSession(Session session, SessionFactory sessionFactory) {
		SessionFactoryUtils.releaseSession(session, null);
	}

	private Session getSession(SessionFactory sessionFactory)
			throws DataAccessResourceFailureException {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		return session;
	}

	/**
	 * 事务管理功能,callback中的方法doInHibernate内所有的数据库操作被看做�?个事�?,发生异常回滚�?有数据库操作.
	 */
//	public Object doInTransaction(HibernateCallback callback) throws Exception {
//		Transaction tx = null;
//		Session session = null;
//		SessionFactory sessionFactory = (SessionFactory) getBeanFactory()
//				.getBean("sessionFactory");
//		boolean sessionBindedToThread = false;
//		try {
//			if (!TransactionSynchronizationManager.hasResource(sessionFactory)
//					&& !sessionCreated.booleanValue()) {
//				session = getSession(sessionFactory);
//				sessionCreated = Boolean.TRUE;
//				logger
//						.debug("open a new session and bind to current thread, session's hashcode is "
//								+ session.hashCode());
//				TransactionSynchronizationManager.bindResource(sessionFactory,
//						new SessionHolder(session));
//				tx = session.beginTransaction();
//			} else {
//				logger.debug("fetch a thread binded session");
//				sessionBindedToThread = true;
//			}
//
//			Object result = callback.doInHibernate(session);
//
//			if (!sessionBindedToThread) {
//				logger.debug("Closing single Hibernate Session");
//				tx.commit();
//
//				unbindObjectFromTransactionSynchronizationManager(sessionFactory);
//				Connection conn = session.connection();
//				closeSession(session, sessionFactory);
//				sessionCreated = Boolean.FALSE;
//				logger.debug("Session " + session.hashCode()
//						+ "  has been close, current session status is "
//						+ session + ", connection status is " + conn
//						+ ", connection close is " + conn.isClosed());
//			} else {
//				logger
//						.debug("current thread fetch a existed session, defer transaction commit to thread which session binded");
//			}
//			return result;
//		} catch (Exception e) {
//			if (!sessionBindedToThread && tx != null) {
//				logger.debug("start to rollback transaction");
//				tx.rollback();
//				logger.debug("rollback transaction success");
//			} else {
//				logger
//						.debug("session binded by parent thread, defer to rollback in parent thread");
//			}
//			throw e;
//		} finally {
//			if (!sessionBindedToThread && session != null && session.isOpen()) {
//				unbindObjectFromTransactionSynchronizationManager(sessionFactory);
//				if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
//					tx.rollback();
//				}
//				closeSession(session, sessionFactory);
//				sessionCreated = Boolean.FALSE;
//				logger.debug("Session " + session.hashCode()
//						+ "  has been close, current session status is "
//						+ session);
//			}
//		}
//	}

	public void handleException(Exception ee) throws BaseException,
			BaseBusinessException {
		if (ee instanceof BaseException) {
			throw (BaseException) ee;
		} else if (ee instanceof BaseBusinessException) {
			throw (BaseBusinessException) ee;
		} else {
			logger.error("服务器异常", ee);
			throw new BaseException("服务器异常", ee);
		}
	}
}
