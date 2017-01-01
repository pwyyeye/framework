package common.service;

import java.io.Serializable;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.QueueSender;
import javax.jms.QueueSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.xxl.facade.HelperRemote;

import common.exception.BaseBusinessException;
import common.exception.BaseException;
import common.exception.CommonException;
import common.value.SemMessageObject;
import common.web.bean.SessionUserBean;

public abstract class BaseService implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Log logger = LogFactory.getLog(this.getClass());

//	protected AlphaUddi alpha;

	@Autowired
	protected HelperRemote helperRemote;
	
//	@Autowired
//	@Qualifier("jmsQueueTemplate")
//	private JmsTemplate queueTemplate;

	public static final String SESSION_CALLED_TIMES = "SESSION_CALLED_TIMES";

	public static final String TRANSACTION_BINDED_THREAD = "TRANSACTION_BINDED_THREAD";

	private Boolean sessionCreated = Boolean.FALSE;
	
	private static final String QUEUENAME = "com.xxl.queue";

//	public void sendToQueue(java.io.Serializable obj,SessionUserBean user, Integer subQueueID)
//			throws Exception {
//		logger.debug("sendToQueue: " + obj.toString());
//		try {
//			final SemMessageObject messageObject = new SemMessageObject(user, obj,
//					subQueueID);
//			
//			queueTemplate.send(QUEUENAME, new MessageCreator() {
//				@Override
//				public Message createMessage(javax.jms.Session session) throws JMSException {
//					// TODO Auto-generated method stub
//					return session.createObjectMessage(messageObject);
//				}
//			});  
//			logger.debug("Finished sending mnessage to queue");
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
//
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
