package com.xxl.jms.service;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.xxl.bussiness.MailSender;
import com.xxl.bussiness.MessageObservable;
import com.xxl.facade.JMSTaskRemote;
import com.xxl.jms.bo.JMSTask;
import com.xxl.jms.bo.UserPush;
import com.xxl.jms.bo.UserPushLog;
import com.xxl.jms.bo.UserPushType;
import com.xxl.jms.dao.UserPushDAO;
import com.xxl.jms.dao.UserPushLogDAO;

import common.businessObject.ItModule;
import common.exception.BaseBusinessException;
import common.exception.BaseException;
import common.exception.CommException;
import common.exception.CommonException;
import common.jms.vo.JMSTaskVO;
import common.jms.vo.UserPushLogVO;
import common.jms.vo.UserPushVO;
import common.os.vo.exception.OSBussinessException;
import common.os.vo.exception.OSException;
import common.service.BaseService;
import common.utils.SemAppConstants;
import common.utils.SemAppUtils;
import common.value.MailMessage;
import common.value.MobileMessage;
import common.value.PageList;
import common.value.PageMap;
import common.value.PushMessage;
import common.value.SemMessageObject;

@Service("jmsTaskRemote")
public class JMSTaskService extends BaseService implements JMSTaskRemote {
	public static Log logger = LogFactory.getLog(JMSTaskService.class);

	public static Log dbLogger = SemAppUtils.getDBLog(JMSTaskService.class);

	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate queueTemplate;

	@Autowired
	@Qualifier("jmsTopicTemplate")
	private JmsTemplate topicTemplate;

	@Autowired
	private UserPushDAO userPushDAO;
	@Autowired
	private UserPushLogDAO userPushLogDAO;
	
	
	// for mobile message
	private String smsSenderId;

	 /** 
     * 发送消息队列消息
     * @param queueName 队列目的地 
     * @param message 消息内容 
     */  
    public void send(String topicName,final Serializable message)  throws JMSException{  
    	try {
			queueTemplate.send(topicName, new MessageCreator() {
				@Override
				public Message createMessage(javax.jms.Session session) throws JMSException {
					// TODO Auto-generated method stub
					return session.createObjectMessage(message);
				}
			});
		} catch (JmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}  
    }  
    
    //配置了默认目的地
    public void send(final Serializable message)  throws JMSException{  
    	try {
			queueTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(javax.jms.Session session) throws JMSException {
					// TODO Auto-generated method stub
					return session.createObjectMessage(message);
				}
			});
		} catch (JmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}  
    } 
    
    public void sendAppMessage(Integer type, String users, String title,
			String message,Integer bzId) throws JMSException {
		PushMessage aMessage = new PushMessage(type, users, title, message,bzId);
		logger.debug("start send app message");
		SemMessageObject messageObject = new SemMessageObject(null, aMessage,
				new Integer(SemAppConstants.QUEUE_PUSH_MESSAGE));
		this.send("com.xxl.queue", messageObject);
		logger.debug("start send app message end");
	}
	
	
	public PageList getJMSTaskList(Integer systemID, JMSTaskVO searchVO,
			Integer firstResult, Integer fetchSize) throws CommonException {
		try {
			
			DetachedCriteria criteria = DetachedCriteria.forClass(JMSTask.class);
			int system = systemID.intValue();
			if (this.isSystemModule(system)) {
				system = 0;// ???????????????????????????????????????????????????
			}
			if (searchVO != null) {
				if (system == 0 && searchVO.getModuleID() != null) {
					system = searchVO.getModuleID().intValue();
				}
				if (searchVO.getEmpID() != null) {
					criteria.add(Expression.eq("empID", searchVO.getEmpID()));
				}
				if (searchVO.getMessage() != null) {
					criteria.add(Expression.like("message",
							"%" + searchVO.getMessage() + "%"));
				}
				if (searchVO.getMessageID() != null) {
					criteria.add(Expression.like("messageID",
							"%" + searchVO.getMessageID() + "%"));
				}
				if (searchVO.getQueueName() != null) {
					criteria.add(Expression.like("queueName",
							"%" + searchVO.getQueueName() + "%"));
				}
				if (searchVO.getDealResult() != null) {
					criteria.add(Expression.like("dealDate",
							"%" + searchVO.getDealResult() + "%"));
				}
				if (searchVO.getStatus() != null) {
					criteria.add(Expression.eq("status", searchVO.getStatus()));
				}
				if (searchVO.getDealResult() != null) {
					criteria.add(Expression.eq("dealResult",
							searchVO.getDealResult()));
				}
				if (searchVO.getCreateStartDate() != null) {
					criteria.add(Expression.ge("createDate",
							searchVO.getCreateStartDate()));
				}
				if (searchVO.getDealStartDate() != null) {
					criteria.add(Expression.ge("processDate",
							searchVO.getCreateStartDate()));
				}
				if (searchVO.getCreateEndDate() != null) {
					criteria.add(Expression.le("createDate",
							searchVO.getCreateEndDate()));
				}
				if (searchVO.getDealEndDate() != null) {
					criteria.add(Expression.le("processDate",
							searchVO.getDealEndDate()));
				}
				if (searchVO.getCloseFlag() != null && searchVO.getCloseFlag().intValue()!=0) {
					criteria.add(Expression.le("closeFlag",
							searchVO.getCloseFlag()));
				}
			}
			if (system != 0) {
				ItModule module = new ItModule();
				module.setId(new Integer(system));
				criteria.add(Expression.eq("module", module));

			}
			//System.out.println(SemAppUtils.getJsonFromBean(searchVO));
			//System.out.println("systemID="+systemID+"firstResult="+firstResult+"fetchSize="+fetchSize);
//			Integer rowCount = (Integer) criteria.setProjection(
//					Projections.rowCount()).uniqueResult();
//			criteria.setProjection(null);
//			if (size > 0) {
//				criteria.setFirstResult(firstResult.intValue());
//				criteria.setMaxResults(size);
//			}
			criteria.addOrder(Order.desc("module"));
			PageList pageList=userPushDAO.findByCriteriaByPage(criteria, firstResult, fetchSize);
			System.out.println(pageList.getResults());
//			List list = new ArrayList();
//			Iterator iter = criteria.list().iterator();
//			while (iter.hasNext()) {
//				JMSTask alphaTask = (JMSTask) iter.next();
//				JMSTaskVO vo = (JMSTaskVO) alphaTask.toVO();
//				list.add(vo);
//			}
//			logger.debug("rowCount=" + rowCount + ",list" + list.size());
//			PageList pageList = new PageList();
//			pageList.setResults(rowCount.intValue());
//			pageList.setItems(list);
			return pageList;
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new CommonException("获取jms列表失败" + ee.getMessage());

		}
	}

	public PageList getDoingTaskList(Integer systemID, Integer empID)
			throws CommonException {
		JMSTaskVO searchVO = new JMSTaskVO(null);
		searchVO.setEmpID(empID);
		searchVO.setCloseFlag(new Integer(0));
		return getJMSTaskList(systemID, searchVO, new Integer(0),
				new Integer(0));
	}

	public PageList getDoingTaskList(Integer systemID, String empID)
			throws CommonException, RemoteException {
		return getDoingTaskList(systemID, new Integer(empID));
	}

	public Integer addJMSTask(JMSTaskVO vo) throws CommonException {
		Integer result = null;
		logger.debug("add jms task" + vo);
		try {
			JMSTask task = new JMSTask();
			task.setCreateDate(Calendar.getInstance());
			task.setEmpID(vo.getEmpID());
			task.setMessage(vo.getMessage());
			task.setMessageID(vo.getMessageID());
			task.setQueueName(vo.getQueueName());
			task.setRemark(vo.getRemark());
			task.setStatus(vo.getStatus());
			ItModule module = null;
			if (vo.getModuleID() != null) {
				module = (ItModule) userPushDAO.loadBoById(ItModule.class,
						vo.getModuleID());
			} else {
				module = (ItModule) userPushDAO.loadBoById(ItModule.class,
						new Integer(0));
			}
			task.setModule(module);
			userPushDAO.saveOrUpdate(task);
			result = (Integer) task.getId();
		} catch (HibernateException ee) {
			logger.error("?????????????????????", ee);
			throw new CommonException("?????????????????????", ee);

		}
		return result;
	}

	public void closeJMSTask(Integer id) throws CommonException {
		try {

			JMSTask jmsTask = (JMSTask) userPushDAO.loadBoById(JMSTask.class, id);
			jmsTask.setCloseFlag(new Integer(1));
			userPushDAO.saveOrUpdate(jmsTask);
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new CommonException("IT?????????????????????????" + ee.getMessage());

		} 
	}

	public void updateJMSTask(JMSTaskVO vo) throws CommonException {
		logger.debug("update  alpha Table" + vo);
		try {

			JMSTask jmsTask = (JMSTask) userPushDAO.loadBoById(JMSTask.class,
					(Integer) vo.getId());
			jmsTask.setDealDate(vo.getDealDate());
			jmsTask.setDealResult(vo.getDealResult());
			jmsTask.setStatus(vo.getStatus());
			userPushDAO.saveOrUpdate(jmsTask);
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new CommonException("IT?????????????????????????" + ee.getMessage());

		}
	}

	public PageList getJMSTaskList(Integer systemID) throws CommonException {
		return getJMSTaskList(systemID, null, new Integer(0), new Integer(0));
	}

	public PageList getJMSTaskList() throws CommonException {
		return getJMSTaskList(new Integer(0));
	}

	public void delUserPush(final Integer id) throws OSException,
			OSBussinessException {
		try {
			UserPush bo = (UserPush) userPushDAO.loadById(id, UserPush.class);
			bo.setOn(new Integer(1));
			userPushDAO.save(bo);
		} catch (Exception e) {
			logger.error("???????????????????????????", e);
			throw new OSException("???????????????????????????", e);
		}
	}

	public Integer addUserPush(UserPushVO vo) throws OSException,
			OSBussinessException {
		try {
			logger.debug("add user vo,id=" + vo.getId());
			UserPush bo = new UserPush(vo.getId());
			SemAppUtils.beanCopy(vo, bo);
			logger.debug(bo);
			if (vo.getType() != null) {
				UserPushType uy = new UserPushType();
				uy.setId(vo.getType());
				bo.setType(uy);
			}

			ItModule module = new ItModule();
			;
			if (vo.getModuleID() != null) {

				module.setId(vo.getModuleID());
			} else {
				module.setId(0);
			}
			bo.setModule(module);
			return (Integer) userPushDAO.save(bo);
		} catch (Exception e) {
			logger.error("???????????????????????????", e);
			throw new OSException("???????????????????????????", e);
		}
	}

	public void updateUserPush(final UserPushVO vo) throws OSException,
			OSBussinessException {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(UserPush.class);
			if (vo.getType() != null) {
				UserPushType uy = new UserPushType();
				uy.setId(vo.getType());
				criteria.add(Expression.eq("type", uy));
			}
			if (vo.getUserId() != null)
				criteria.add(Expression.eq("userId", vo.getUserId()));
			List list = userPushDAO.findByCriteria(criteria, new Integer(0),
					new Integer(0));
			logger.debug("get size=" + list.size());
			if (list != null && list.size() > 0) {
				UserPush bo = (UserPush) list.get(0);
				bo.setOn(vo.getOn());
				userPushDAO.save(bo);
			} else {// new a record
				try {
					addUserPush(vo);
				} catch (Exception e) {
					logger.error("??????????????????????????????" + e);
					// throw new OSException("??????????????????????????????", e);
				}
			}
		} catch (Exception e) {
			logger.error("??????????????????????????????" + e);
			throw new OSException("??????????????????????????????", e);
		}
	}

	public Integer addUserPushLog(UserPushLogVO vo) throws OSException,
			OSBussinessException {
		try {
			logger.debug("add user push log vo,id=" + vo.getId());
			UserPushLog bo = new UserPushLog();
			SemAppUtils.beanCopy(vo, bo);
			bo.setRead(0);
			ItModule module = new ItModule();
			;
			if (vo.getModuleID() != null) {

				module.setId(vo.getModuleID());
			} else {
				module.setId(0);
			}
			if (vo.getType() != null) {
				UserPushType uy = new UserPushType();
				uy.setId(vo.getType());
				bo.setType(uy);
			}
			bo.setModule(module);
			logger.debug(bo);
			return (Integer) userPushLogDAO.save(bo);
		} catch (Exception e) {
			logger.error("???????????????????????????", e);
			throw new OSException("???????????????????????????", e);
		}
	}

	public PageList getUserPush(UserPushVO vo, Integer firstResult,
			Integer fetchSize) throws OSException, OSBussinessException {
		logger.debug("getdcp for " + vo);
		try {
			// DetachedCriteria criteria = DetachedCriteria
			// .forClass(UserPush.class);
			// if (vo.getId() != null)
			// criteria.add(Expression.eq("id", vo.getId()));
			// if (vo.getUserId() != null)
			// criteria.add(Expression.eq("userId", vo.getUserId()));
			return userPushDAO.getUserAllPush(vo.getUserId(), vo.getModuleID());
		} catch (Exception e) {
			logger.error("???????????????????????????", e);
			throw new OSException("?????????????????????????????????", e);
		}
	}

	public Boolean isOpenUserPush(final Integer userId, final Integer type)
			throws BaseException, BaseBusinessException {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(UserPush.class);
			UserPushType uy = new UserPushType();
			uy.setId(type);
			criteria.add(Expression.eq("type", uy));
			criteria.add(Expression.eq("userId", userId));
			List list = userPushDAO.findByCriteria(criteria, new Integer(0),
					new Integer(0));
			if (list != null && list.size() > 0) {
				UserPush bo = (UserPush) list.get(0);
				if (bo.getOn() != null && bo.getOn().intValue() == 0) {// is
					// close
					return new Boolean(false);
				} else {
					return new Boolean(true);
				}

			} else {
				return new Boolean(true);
			}
		} catch (Exception e) {
			logger.error("??????????????????????????????" + e);
			throw new OSException("??????????????????????????????", e);
		}

	}

	 public PageMap getUnReadPush(Integer userId)throws BaseException,
	 BaseBusinessException {
	 Map map=userPushLogDAO.getUnReadPush(userId);
	 PageMap pm= new PageMap();
	 pm.setItems(map);
	 return pm;
	 }

	public PageList getUserPushLog(UserPushLogVO vo, Integer firstResult,
			Integer fetchSize) throws OSException, OSBussinessException {
		logger.debug("getdcp for " + vo);
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(UserPushLog.class);
			if (vo.getId() != null)
				criteria.add(Expression.eq("id", vo.getId()));
			if (vo.getUserId() != null)
				criteria.add(Expression.eq("userId", vo.getUserId()));
			if (vo.getRead() != null)
				criteria.add(Expression.eq("read", vo.getRead()));
			if (vo.getType() != null) {
				UserPushType upt = new UserPushType();
				upt.setId(vo.getType());
				criteria.add(Expression.eq("type", upt));
			}
			criteria.addOrder(Order.desc("createDate"));
			criteria.addOrder(Order.asc("read"));

			return userPushLogDAO.findByCriteriaByPage(criteria, firstResult,
					fetchSize);
		} catch (Exception e) {
			logger.error("???????????????????????????", e);
			throw new OSException("?????????????????????????????????", e);
		}
	}

	public void updateUserPushLog(final UserPushLogVO vo) throws BaseException,
			BaseBusinessException {
		try {
			if (vo != null && vo.getId() != null) {// update read by id
				UserPushLog bo = (UserPushLog) userPushLogDAO.loadBoById(
						vo.getId(), UserPushLog.class);
				bo.setRead(vo.getRead());
				bo.setReadDate(Calendar.getInstance());
				userPushLogDAO.save(bo);
			} else if (vo.getType() != null) {// update read by type
				DetachedCriteria criteria = DetachedCriteria
						.forClass(UserPushLog.class);
				UserPushType upType = new UserPushType();
				upType.setId(vo.getType());
				criteria.add(Expression.eq("type", upType));
				criteria.add(Expression.eq("userId", vo.getUserId()));
				List list = userPushLogDAO.findByCriteria(criteria,
						new Integer(0), new Integer(0));
				Iterator iter = list.iterator();
				while (iter.hasNext()) {
					UserPushLog upLog = (UserPushLog) iter.next();
					upLog.setRead(vo.getRead());
					upLog.setReadDate(Calendar.getInstance());
					userPushLogDAO.save(upLog);
				}
			}
		} catch (Exception e) {
			logger.error("??????????????????????????????" + e);
			throw new BaseException("??????????????????????????????", e);
		}
	}

	public void resendUserPush(final Integer id) throws BaseException,
			BaseBusinessException {

		try {
			UserPushLog bo = (UserPushLog) userPushLogDAO.loadBoById(id,
					UserPushLog.class);
			sendAppPushMessage("" + bo.getUserId(), bo.getTitle(),
					bo.getContent());
		} catch (Exception e) {
			logger.error("??????????????????????????????" + e);
			throw new BaseException("??????????????????????????????", e);
		}
	}

	public void sendAppPushMessage(String users, String title, String message)
			throws BaseException, BaseBusinessException {
		// sendAppPushMessage(new Integer(0), users, title, message,new
		// Integer(0));
	}

	private boolean isSystemModule(int systemID) {
		return systemID == 0 || systemID == SemAppConstants.COMMON_MODULE_ID;
	}
	
	/**------------------------------------???????????? ??????--------------------------------------------*/
	
	
	public String sendMessageByMail(String to, String cc, String subject,
			String text, String from, String host) throws CommException {
		logger.debug("send mail from Service:to" + to);
		MailMessage message = new MailMessage(new String[] { to },
				new String[] { cc }, subject, text, from, host, null);
		this.sendMail(message);
		logger.debug("send mail end from Service:to" + to);
		return null;
	}

	public String sendMessageByMail(String[] to, String[] cc, String subject,
			String text, String from, String host) throws CommException {
		logger.debug("send mail from Service:to" + to);
		MailMessage message = new MailMessage(to, cc, subject, text, from,
				host, null);
		this.sendMail(message);
		logger.debug("send mail end from Service:to" + to);
		return null;
	}

	public String sendMessageByMail(String[] to, String[] cc, String subject,
			String text, String from, String host, String[] attachFile)
			throws CommException {
		logger.debug("send mail from Service:to" + to);
		// MailSender sender = MailSender.getTheInstance();
		// sender.sendMail(to, cc, subject, text, from, host,attachFile);
		MailMessage message = new MailMessage(to, cc, subject, text, from,
				host, attachFile);
		this.sendMail(message);
		logger.debug("send mail end from Service:to" + to);
		return null;
	}

	public void sendMessageByMail(MailMessage message) throws CommException {
		MailSender sender = MailSender.getTheInstance();
		sender.sendMail(message);
	}

	
	public void sendMail(MailMessage message) throws CommException {
		logger.debug("start send mail");
		SemMessageObject messageObject = new SemMessageObject(null, message,
				new Integer(SemAppConstants.MAIL_QUEUE));
		try {
			this.send(messageObject);
		} catch (JMSException e) {
			logger.error("发送邮件失败", e);
			throw new CommException(e);
		}
		logger.debug("start send mail end");
	}

	
	public String publishMessage(common.bussiness.Message message) throws CommException {
		logger.debug("publist message,messageID:[" + message.getType() + "]");
		MessageObservable obserable = new MessageObservable();
		obserable.sendMessage(message);
		return "0";
	}

	public void sendMessageByMobile(MobileMessage message) throws CommException {
//		try {
//			MobileDB.init();
//			MobileDB sender = MobileDB.getTheInstance();
//			sender
//					.sentMobileMsg(message.getDestMobiles(), message
//							.getContent());
//		} catch (NamingException ex) {
//			throw new CommException(ex);
//		} catch (SQLException ex) {
//			throw new CommException(ex);
//		} finally {
//			MobileDB.end();
//		}
	}

	public void sendMessageByMobile(String destMobile, String[] message)
			throws BaseException, BaseBusinessException {
		this.sendMessageByMobile(destMobile, message, smsSenderId);
	}

	public void sendMessageByMobile(String destMobile, String[] message,
			String customSenderId) throws BaseException, BaseBusinessException {
		if (SemAppUtils.isEmpty(destMobile)) {
			throw new BaseBusinessException("??????????????????????????????????????????");
		}
//		HashMap result = null;
//		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
//		restAPI.init(smsUrl, smsPort);
//		restAPI.setAccount(smsUsername, smsPassword);
//		restAPI.setAppId(smsAppId);
//		result = restAPI.sendTemplateSMS(destMobile, customSenderId, message);
//		logger.info("SDKTestGetSubAccounts result=" + result);
//		if (!"000000".equals(result.get("statusCode")))
//			throw new BaseException("?????????=" + result.get("statusCode")
//					+ " ????????????= " + result.get("statusMsg"));
	}
}
