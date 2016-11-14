package com.xxl.jms.service;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.xxl.HibernateUtil;
import com.xxl.facade.JMSTaskRemote;
import com.xxl.jms.bo.JMSTask;
import com.xxl.jms.bo.UserPush;
import com.xxl.jms.bo.UserPushLog;
import com.xxl.jms.bo.UserPushType;
import com.xxl.jms.dao.UserPushDAO;
import com.xxl.jms.dao.UserPushLogDAO;
import common.businessObject.ItModule;
import common.bussiness.CommException;
import common.exception.BaseBusinessException;
import common.exception.BaseException;
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
import common.value.PageList;
import common.value.PageMap;
import common.value.PushMessage;
import common.value.SemMessageObject;

@Service("jMSTaskRemote")
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

	Session hibernateSession;

	
	 /** 
     * 发送一条消息到指定的队列（目标） 
     * @param queueName 队列名称 
     * @param message 消息内容 
     */  
    public void send(String topicName,final Serializable message)  throws JMSException{  
    	queueTemplate.send(topicName, new MessageCreator() {
			@Override
			public Message createMessage(javax.jms.Session session) throws JMSException {
				// TODO Auto-generated method stub
				return session.createObjectMessage(message);
			}
		});  
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
	
    public void sendMail(MailMessage message) throws CommException {
		logger.debug("start send mail");
		SemMessageObject messageObject = new SemMessageObject(null, message,
				new Integer(SemAppConstants.MAIL_QUEUE));
		try {
			this.send("com.xxl.queue", messageObject);
		} catch (JMSException e) {
			logger.error("后台发送邮件失败", e);
			throw new CommException(e);
		}
		logger.debug("start send mail end");
	}

	
	public PageList getJMSTaskList(Integer systemID, JMSTaskVO searchVO,
			Integer firstResult, Integer fetchSize) throws CommonException {
		try {
			hibernateSession = HibernateUtil.currentSession();
			Criteria criteria = hibernateSession.createCriteria(JMSTask.class);
			int size = fetchSize.intValue();
			int system = systemID.intValue();
			if (this.isSystemModule(system)) {
				system = 0;// 系统管理模块可以管理所有模块的权限
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
				if (searchVO.getCloseFlag() != null) {
					criteria.add(Expression.le("closeFlag",
							searchVO.getCloseFlag()));
				}
			}
			if (system != 0) {
				// List
				// modules=SemAppUtils.getSubmodules(system,hibernateSession);
				// criteria.add(Expression.in("module", modules));
				ItModule module = new ItModule();
				module.setId(new Integer(system));
				criteria.add(Expression.eq("module", module));

			}
			Integer rowCount = (Integer) criteria.setProjection(
					Projections.rowCount()).uniqueResult();
			criteria.setProjection(null);
			if (size > 0) {
				criteria.setFirstResult(firstResult.intValue());
				criteria.setMaxResults(size);
			}
			criteria.addOrder(Order.desc("module"));
			List list = new ArrayList();
			Iterator iter = criteria.list().iterator();
			while (iter.hasNext()) {
				JMSTask alphaTask = (JMSTask) iter.next();
				JMSTaskVO vo = (JMSTaskVO) alphaTask.toVO();
				list.add(vo);
			}
			logger.debug("rowCount=" + rowCount + ",list" + list.size());
			PageList pageList = new PageList();
			pageList.setResults(rowCount.intValue());
			pageList.setItems(list);
			return pageList;
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new CommonException("����ϵͳ������" + ee.getMessage());

		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
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
		Transaction tx = null;
		try {
			hibernateSession = HibernateUtil.currentSession();
			tx = hibernateSession.beginTransaction();
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
				module = (ItModule) hibernateSession.load(ItModule.class,
						vo.getModuleID());
			} else {
				module = (ItModule) hibernateSession.load(ItModule.class,
						new Integer(0));
			}
			task.setModule(module);
			hibernateSession.save(task);
			tx.commit();
			result = (Integer) task.getId();
		} catch (HibernateException ee) {
			tx.rollback();
			logger.error("数据库操作失败", ee);
			throw new CommonException("数据库操作失败", ee);

		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
		}
		return result;
	}

	public void closeJMSTask(Integer id) throws CommonException {
		Transaction tx = null;
		try {
			hibernateSession = HibernateUtil.currentSession();
			tx = hibernateSession.beginTransaction();
			JMSTask jmsTask = (JMSTask) hibernateSession
					.load(JMSTask.class, id);
			jmsTask.setCloseFlag(new Integer(1));
			hibernateSession.update(jmsTask);
			tx.commit();
		} catch (HibernateException ee) {
			tx.rollback();
			logger.error(ee);
			throw new CommonException("ITϵͳ数据库操作失败" + ee.getMessage());

		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
		}
	}

	public void updateJMSTask(JMSTaskVO vo) throws CommonException {
		logger.debug("update  alpha Table" + vo);
		Transaction tx = null;
		try {
			hibernateSession = HibernateUtil.currentSession();
			tx = hibernateSession.beginTransaction();
			JMSTask jmsTask = (JMSTask) hibernateSession.load(JMSTask.class,
					(Integer) vo.getId());
			jmsTask.setDealDate(vo.getDealDate());
			jmsTask.setDealResult(vo.getDealResult());
			jmsTask.setStatus(vo.getStatus());
			hibernateSession.update(jmsTask);
			tx.commit();
		} catch (HibernateException ee) {
			tx.rollback();
			logger.error(ee);
			throw new CommonException("ITϵͳ数据库操作失败" + ee.getMessage());

		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
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
			logger.error("删除用户资料失败：", e);
			throw new OSException("删除用户资料失败：", e);
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
			logger.error("新增用户资料失败：", e);
			throw new OSException("新增用户资料失败：", e);
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
					logger.error("新增用户推送失败错误" + e);
					// throw new OSException("保存用户资料失败错误", e);
				}
			}
		} catch (Exception e) {
			logger.error("保存用户资料失败错误" + e);
			throw new OSException("保存用户资料失败错误", e);
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
			logger.error("新增用户资料失败：", e);
			throw new OSException("新增用户资料失败：", e);
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
			logger.error("获取用户失败错误：", e);
			throw new OSException("获取用户资料失败错误：", e);
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
			logger.error("保存用户资料失败错误" + e);
			throw new OSException("保存用户资料失败错误", e);
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
			logger.error("获取用户失败错误：", e);
			throw new OSException("获取用户资料失败错误：", e);
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
			logger.error("保存用户资料失败错误" + e);
			throw new BaseException("保存用户资料失败错误", e);
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
			logger.error("保存用户资料失败错误" + e);
			throw new BaseException("保存用户资料失败错误", e);
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
}
