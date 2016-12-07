package com.xxl.task.service;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.SessionContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;

import com.xxl.HibernateUtil;
import com.xxl.task.bo.Task;
import com.xxl.task.bo.TaskLog;

import common.businessObject.ItModule;
import common.exception.BaseBusinessException;
import common.exception.CommonException;
import common.service.BaseService;
import common.task.vo.TaskLogVO;
import common.task.vo.TaskVO;
import common.utils.SemAppConstants;
import common.utils.SemAppUtils;
import common.value.PageList;
import common.value.SemMessageObject;


public class TaskEJB  extends BaseService  {
	SessionContext sessionContext;

	public Log logger = LogFactory.getLog(this.getClass());
	public static final int ERROR_MESSAGE_ID = 101;

//	private AlphaUddi alphaUddi;

	Session hibernateSession;
	
	private QueueSession qSession;


	private QueueSender qSender;



	public PageList getTaskList(Integer systemID, TaskVO searchVO,
			Integer firstResult, Integer fetchSize) throws CommonException {
		try {
			hibernateSession = HibernateUtil.currentSession();
			Criteria criteria = hibernateSession.createCriteria(Task.class);
			int size = fetchSize.intValue();
			int system = systemID.intValue();
			if (searchVO != null) {
				if (system == 0 && searchVO.getModuleID() != null) {
					system = searchVO.getModuleID().intValue();
				}
				if (searchVO.getName() != null) {
					criteria.add(Expression.like("name", "%"
							+ searchVO.getName() + "%"));
				}
				if (searchVO.getRemark() != null) {
					criteria.add(Expression.like("remark", "%"
							+ searchVO.getRemark() + "%"));
				}
				if (searchVO.getType() != null) {
					criteria.add(Expression.eq("type", searchVO.getType()));
				}
				if (searchVO.getStatus() != null) {
					criteria.add(Expression.eq("status", searchVO.getStatus()));
				}
			}
			if (system != 0) {
//				List modules = SemAppUtils.getSubmodules(system,
//						hibernateSession);
//				criteria.add(Expression.in("module", modules));
				ItModule module=new ItModule();
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
				Task bo = (Task) iter.next();
				TaskVO vo = (TaskVO) bo.toVO();
				list.add(vo);
			}
			logger.debug("rowCount=" + rowCount);
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

	public PageList getTaskList(Integer systemID) throws CommonException {
		return getTaskList(systemID, null, new Integer(0), new Integer(0));
	}

	public PageList getTaskList() throws CommonException {
		return getTaskList(new Integer(0));
	}

	public void updateTask(TaskVO vo) throws CommonException {
		logger.debug("update  reportModule" + vo);
		Transaction tx = null;
		try {
			hibernateSession = HibernateUtil.currentSession();
			tx = hibernateSession.beginTransaction();
			Task bo = (Task) hibernateSession.load(Task.class, vo.getId());
			bo.setArgments(vo.getArgments());
			bo.setLastRunDate(vo.getLastRunDate());
			bo.setLastRunRemark(vo.getLastRunRemark());
			bo.setLastRunResult(vo.getLastRunResult());
			bo.setMethod(vo.getMethod());
			bo.setName(vo.getName());
			bo.setRemark(vo.getRemark());
			bo.setRunTime(vo.getRunTime());
			bo.setType(vo.getType());
			bo.setStatus(vo.getStatus());
			hibernateSession.update(bo);
			tx.commit();
		} catch (HibernateException ee) {
			tx.rollback();
			logger.error(ee);
			throw new CommonException("数据库操作失败" + ee.getMessage());

		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
		}
	}

	public void deleteTask(Integer id) throws CommonException {
		Transaction tx = null;
		try {

			hibernateSession = HibernateUtil.currentSession();
			Task bo = (Task) hibernateSession.load(Task.class, id);
			tx = hibernateSession.beginTransaction();
			hibernateSession.delete(bo);
			tx.commit();
		} catch (HibernateException ee) {

			logger.error(ee);
			throw new CommonException("删除定时任务失败" + ee.getMessage());

		} finally {
			if (tx != null)
				tx.rollback();
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
		}
	}

	public Integer addTask(TaskVO vo) throws CommonException {
		Integer result = null;
		logger.debug("add it UABinding" + vo);
		Transaction tx = null;
		try {
			hibernateSession = HibernateUtil.currentSession();
			tx = hibernateSession.beginTransaction();
			Task bo = new Task();
			bo.setId((Integer)vo.getId());
			bo.setArgments(vo.getArgments());
			bo.setLastRunDate(vo.getLastRunDate());
			bo.setLastRunRemark(vo.getLastRunRemark());
			bo.setLastRunResult(vo.getLastRunResult());
			bo.setMethod(vo.getMethod());
			bo.setName(vo.getName());
			bo.setRemark(vo.getRemark());
			bo.setRunTime(vo.getRunTime());
			bo.setType(vo.getType());
			bo.setStatus(vo.getStatus());
			ItModule module = (ItModule) hibernateSession.load(ItModule.class,
					vo.getModuleID());
			bo.setModule(module);
			hibernateSession.save(bo);
			tx.commit();
			result =(Integer) bo.getId();
		} catch (HibernateException ee) {
			tx.rollback();
			logger.error(ee);
			throw new CommonException("数据库操作失败" + ee.getMessage());

		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
		}
		return result;
	}

	public PageList getTaskLogList(Integer systemID, TaskLogVO searchVO,
			Integer firstResult, Integer fetchSize) throws CommonException {
		try {
			hibernateSession = HibernateUtil.currentSession();
			Criteria criteria = hibernateSession.createCriteria(TaskLog.class);
			int size = fetchSize.intValue();
			int system = systemID.intValue();
			if (searchVO != null) {
				if (system == 0 && searchVO.getModuleID() != null) {
					system = searchVO.getModuleID().intValue();
				}
				if (searchVO.getTaskID() != null) {
					Task task = (Task) hibernateSession.load(Task.class,
							searchVO.getTaskID());
					criteria.add(Expression.eq("task", task));
				}
				if (searchVO.getBeginDate() != null) {
					criteria.add(Expression.ge("beginDate", searchVO
							.getBeginDate()));
				}
				if (searchVO.getEndDate() != null) {
					criteria.add(Expression
							.lt("endDate", searchVO.getEndDate()));
				}
				if (searchVO.getRunResult() != null) {
					criteria.add(Expression.eq("runResult", searchVO
							.getRunResult()));
				}
			}
			if (system != 0) {
//				List modules = SemAppUtils.getSubmodules(system,
//						hibernateSession);
//				criteria.add(Expression.in("module", modules));
				ItModule module=new ItModule();
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
			criteria.addOrder(Order.desc("beginDate"));
			List list = new ArrayList();
			Iterator iter = criteria.list().iterator();
			while (iter.hasNext()) {
				TaskLog bo = (TaskLog) iter.next();
				TaskLogVO vo = (TaskLogVO) bo.toVO();
				list.add(vo);
			}
			logger.debug("rowCount=" + rowCount);
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

	public void excuteTask(Integer taskID) throws CommonException {
		/**
		hibernateSession = HibernateUtil.currentSession();
		
		Task task=(Task)hibernateSession.load(Task.class,taskID);
		logger.debug("schedule task" + task.getName());
		TaskLog taskLog = new TaskLog();
		taskLog.setModule(task.getModule());
		taskLog.setTask(task);
		taskLog.setBeginDate(Calendar.getInstance());
		taskLog.setRunResult(new Integer(SemAppConstants.TASK_RUN_RESULT_INIT));
		ItModule module = task.getModule();
		try {
			String ejbServer = module.getDeployServer();
			String jndi = module.getServiceJndi();
			String homeClass = module.getServiceHome();
			String remoteClass = module.getServiceRemote();
			String method = task.getMethod();
			if (null == method || null == jndi || null == homeClass
					|| null == remoteClass) {
				throw new CommonException(
						"调用的EJB服务配置不存在,method/jndi/servicesHome/servicesRemote不能为空");
			}
			ServiceLocator locator = ServiceLocator.getInstance();
			Object remote = (Object) locator.getObj(jndi, Class
					.forName(homeClass));
			Method invokeMethod = remote.getClass().getMethod(method, null);
			String result = (String) invokeMethod.invoke(remote, null);
			Calendar cal = Calendar.getInstance();
			taskLog.setRunResult(new Integer(
					SemAppConstants.TASK_RUN_RESULT_SUCCESS));
			taskLog.setEndDate(cal);
			taskLog.setRunContent(result);
			task.setLastRunDate(cal);
			task.setLastRunResult(new Integer(
					SemAppConstants.TASK_RUN_RESULT_SUCCESS));
			task.setLastRunRemark(result);			
		} catch (Exception ee) {
			logger.error("任务[" + task.getName() + "]执行失败", ee);
			hibernateSession = HibernateUtil.currentSession();
		
			Calendar cal = Calendar.getInstance();
			taskLog.setRunResult(new Integer(
					SemAppConstants.TASK_RUN_RESULT_FAILER));
			taskLog.setRunContent(ee.getMessage());
			taskLog.setEndDate(cal);
		
			task.setLastRunDate(cal);
			task.setLastRunResult(new Integer(
					SemAppConstants.TASK_RUN_RESULT_FAILER));
			task.setLastRunRemark(ee.getMessage());
//			this.publishMessage(ERROR_MESSAGE_ID, "ALPHA后台任务["
//					+ task.getName() + "]执行失败", "ALPHA后台任务["
//					+ task.getName() + "]执行失败/N任务ID[" + task.getId()
//					+ "],请及时处理");
		}
		if(task.getType().intValue()<0){//如果为自定义执行时间
		    Calendar nextRunTime=Calendar.getInstance();
		    nextRunTime.add(Calendar.MINUTE,0-task.getType().intValue());
		    task.setRunTime(nextRunTime);
		}
		hibernateSession = HibernateUtil.currentSession();
		Transaction tx = hibernateSession.beginTransaction();
		hibernateSession.save(taskLog);
		hibernateSession.update(task);
		tx.commit();
		*/
	}
//	protected boolean publishMessage(int messageID, String subject,
//			String content) {
//		return SemAppUtils.publishMessage(messageID, subject, content);
//	}
	public void excuteTask() throws CommonException {
		Transaction tx = null;
		try {
			hibernateSession = HibernateUtil.currentSession();
			String sql = "FROM Task T WHERE (T.status=0)" 
				   +"and ((T.type=1 AND date_format(T.runTime,'%i') = date_format(now(),'%i')) "
					+ "OR (T.type=0 AND date_format(T.runTime,'%H:%i') = date_format(now(),'%H:%i')) "
					+ "OR (T.type=2 AND date_format(T.runTime,'%d %H:%i') = date_format(now(),'%d %H:%i')) "
					+ "OR (T.type<0) "
					+ "OR (T.type=3 AND date_format(T.runTime,'%d %H:%i') = date_format(now(),'%d %H:%i')))";
			Query query = hibernateSession.createQuery(sql);
			Iterator iter = query.list().iterator();
			while (iter.hasNext()) {
				
				Task task = (Task) iter.next();
				logger.info("excute task["+task.getId()+"("+task.getName()+")]");
				Integer taskID = (Integer)task.getId();
				logger.debug("notice running TASK schedule[" + taskID
						+ "]");
				SemMessageObject messageObject = new SemMessageObject(null,
						taskID, new Integer(
								SemAppConstants.QUEUE_TASK_SCHEDULE));
				ObjectMessage objectMessage;
				try {
					objectMessage = qSession.createObjectMessage(messageObject);
					qSender.send(objectMessage);
				} catch (JMSException e) {
					logger.error("后台发送邮件失败", e);
					throw new CommonException(e);
				}
			}

		} catch (HibernateException ee) {
			if (tx != null)
				tx.rollback();
			logger.error("数据库操作异常", ee);
			throw new CommonException("数据库操作异常", ee);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
		}
	}



	public void excuteAlphaTask() throws CommonException, BaseBusinessException {
		logger.debug("alpha background task execute!!");
//		try {
//			alphaUddi.backgroundExecute();
//		} catch (RemoteException e) {
//			logger.error("创建UDDI BEAN失败",e);
//			throw new CommonException("创建UDDI BEAN失败");
//		}
	}



}
