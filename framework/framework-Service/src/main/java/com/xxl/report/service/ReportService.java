package com.xxl.report.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

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
import org.springframework.stereotype.Service;

import com.xxl.HibernateUtil;
import com.xxl.baseService.bo.ItModule;
import com.xxl.baseService.bo.ReportModule;
import com.xxl.baseService.bo.ReportSchedule;
import com.xxl.facade.CommonRemote;
import com.xxl.facade.HelperRemote;
import com.xxl.facade.ReportRemote;
import common.exception.CommException;
import common.exception.CommonException;
import common.utils.SemAppConstants;
import common.utils.SemAppUtils;
import common.value.PageList;
import common.value.ReportModuleVO;
import common.value.ReportScheduleVO;
import common.value.SemMessageObject;

@Service("reportRemote")
public class ReportService implements ReportRemote {
//	SessionContext sessionContext;

	public Log logger = LogFactory.getLog(this.getClass());

	Session hibernateSession;

	private HelperRemote helperRemote;

	private CommonRemote commonRemote;

//	private QueueConnectionFactory qcFactory;
//
//	private QueueSession qSession;
//
//	private QueueConnection qConnection;
//
//	private QueueSender qSender;
//
//	private Queue queSJ;

	public void ejbCreate() {

	}


	public List getReportModuleList(Integer moduleID, Integer root)
			throws CommonException {
		List reports = new ArrayList();
		try {
			hibernateSession = HibernateUtil.currentSession();
			Criteria criteria = hibernateSession
					.createCriteria(ReportModule.class);
			if (moduleID != null) {
				ItModule rootModule = (ItModule) hibernateSession.load(
						ItModule.class, moduleID);
				criteria.add(Expression.eq("module", rootModule));
			}
			if (root == null)
				root = new Integer(0);// 0?????????????????????
			criteria.add(Expression.eq("parent", root));
			criteria.addOrder(Order.asc("sortID"));
			Iterator iter = criteria.list().iterator();
			while (iter.hasNext()) {
				ReportModule report = (ReportModule) iter.next();
				reports.add(report.toVO());
			}
			return reports;
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new CommonException("数据库操作失败", ee);

		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
		}
	}

	public PageList getReportModuleList(Integer systemID,
			ReportModuleVO searchVO, Integer firstResult, Integer fetchSize)
			throws CommonException {
		try {
			hibernateSession = HibernateUtil.currentSession();
			Criteria criteria = hibernateSession
					.createCriteria(ReportModule.class);
			int size = fetchSize.intValue();
			int system = systemID.intValue();
			if (this.isSystemModule(system)) {
				system = 0;// ???????????????????????????????????????????????????
			}
			if (searchVO != null) {
				if (system == 0 && searchVO.getModuleID() != null) {
					system = searchVO.getModuleID().intValue();
				}
				if (searchVO.getName() != null) {
					criteria.add(Expression.like("name", "%"
							+ searchVO.getName() + "%"));
				}
				if (searchVO.getReport() != null) {
					criteria.add(Expression.like("report", "%"
							+ searchVO.getReport() + "%"));
				}
				if (searchVO.getRemark() != null) {
					criteria.add(Expression.like("remark", "%"
							+ searchVO.getRemark() + "%"));
				}
			}
			if (system != 0) {
//				List modules = SemAppUtils.getSubmodules(system,
//						hibernateSession);
//				criteria.add(Expression.in("module", getSubmodules(system,
//						hibernateSession)));
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
				ReportModule reportModule = (ReportModule) iter.next();
				ReportModuleVO vo = (ReportModuleVO) reportModule.toVO();
				list.add(vo);
			}
			logger.debug("report admin EJB rowCount=" + rowCount);
			PageList pageList = new PageList();
			pageList.setResults(rowCount.intValue());
			pageList.setItems(list);
			return pageList;
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new CommonException("数据库操作失败" + ee.getMessage());

		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
		}
	}

	public PageList getReportModuleList(Integer systemID)
			throws CommonException {
		return getReportModuleList(systemID, null, new Integer(0), new Integer(
				0));
	}

	public PageList getReportModuleList() throws CommonException {
		return getReportModuleList(new Integer(0));
	}

	public ReportModuleVO getReportModuleByID(Integer reportModuleID)
			throws CommonException {
		ReportModuleVO reportVO = null;
		try {
			hibernateSession = HibernateUtil.currentSession();
			ReportModule reportModule = (ReportModule) hibernateSession.load(
					ReportModule.class, reportModuleID);
			reportVO = (ReportModuleVO) reportModule.toVO();
		} catch (HibernateException ee) {
			logger.error("数据库操作异常", ee);
			throw new CommonException("数据库操作异常" + ee.getMessage());

		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
		}
		return reportVO;
	}

	public void updateReportModule(ReportModuleVO vo) throws CommonException {
		logger.debug("update  reportModule" + vo);
		if (((Integer)vo.getId()).intValue() == vo.getParent().intValue()) {
			throw new CommonException("父报表选择有误，ID号与子报表ID号一样");
		}
		Transaction tx = null;
		try {
			hibernateSession = HibernateUtil.currentSession();
			tx = hibernateSession.beginTransaction();
			ReportModule reportModule = (ReportModule) hibernateSession.load(
					ReportModule.class, vo.getId());
			reportModule.setName(vo.getName());
			reportModule.setReport(vo.getReport());
			reportModule.setNeedDatekey(vo.getNeedDateKey());
			reportModule.setNeedLine(vo.getNeedLine());
			reportModule.setNeedModel(vo.getNeedModel());
			reportModule.setNeedSeries(vo.getNeedSeries());
			reportModule.setNeedWorkshop(vo.getNeedWorkshop());
			reportModule.setDateKeyType(vo.getDateKeyType());
			reportModule.setNeedLog(vo.getNeedLog());
			reportModule.setNeedColor(vo.getNeedColor());
			reportModule.setNeedTimekey(vo.getNeedTimekey());
			reportModule.setCustomKey(vo.getCustomKey());
			reportModule.setOtherDatekeyMode(vo.getOtherDatekeyMode());
			reportModule.setParent(vo.getParent());
			reportModule.setRemark(vo.getRemark());
			reportModule.setTimekeyMode(vo.getTimekeyMode());
			reportModule.setNeedControlPoint(vo.getNeedControlPoint());
			reportModule.setSortID(vo.getSortID());
			reportModule.setNeedDepartment(vo.getNeedDepartment());
			reportModule.setValid(vo.getValid());
			reportModule.setOldVersion(vo.getOldVersion());
			reportModule.setParameterModule(vo.getParameterModule());
			reportModule.setJavascript(vo.getJavascript());
			reportModule.setSubmitScript(vo.getSubmitScript());
			reportModule.setExportType(vo.getExportType());
			hibernateSession.update(reportModule);
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

	public void deleteReportModule(Integer id) throws CommonException {
		Transaction tx = null;
		try {

			hibernateSession = HibernateUtil.currentSession();
			ReportModule reportModule = (ReportModule) hibernateSession.load(
					ReportModule.class, id);
			tx = hibernateSession.beginTransaction();
			hibernateSession.delete(reportModule);
			tx.commit();
		} catch (HibernateException ee) {

			logger.error(ee);
			throw new CommonException("数据库操作失败" + ee.getMessage());

		} finally {
			if (tx != null)
				tx.rollback();
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
		}
	}

	public void closeReportModule(Integer oper) throws CommonException {
		Transaction tx = null;
//		try {

//			hibernateSession = HibernateUtil.currentSession();
//			tx = hibernateSession.beginTransaction();
//
//			Connection con = hibernateSession.connection();
//			PreparedStatement stmt = con
//					.prepareStatement("update RP_REPORT_MODULE set VALID="
//							+ oper);
//			stmt.executeUpdate();
//
//			tx.commit();
//		} catch (SQLException ee) {
//			logger.error("?????????????????????", ee);
//			throw new CommonException("?????????????????????", ee);
//		} finally {
//			if (tx != null)
//				tx.rollback();
//			try {
//				HibernateUtil.closeSession();
//			} catch (HibernateException e) {
//			}
//		}
	}

	public Integer addReportModule(ReportModuleVO vo) throws CommonException {
		Integer result = null;
		logger.debug("add it UABinding" + vo);
		Transaction tx = null;
		try {
			hibernateSession = HibernateUtil.currentSession();
			tx = hibernateSession.beginTransaction();
			ReportModule report = new ReportModule();
			report.setId((Integer)vo.getId());
			report.setName(vo.getName());
			report.setReport(vo.getReport());
			report.setNeedDatekey(vo.getNeedDateKey());
			report.setNeedLine(vo.getNeedLine());
			report.setNeedModel(vo.getNeedModel());
			report.setNeedSeries(vo.getNeedSeries());
			report.setNeedWorkshop(vo.getNeedWorkshop());
			report.setDateKeyType(vo.getDateKeyType());
			report.setNeedLog(vo.getNeedLog());
			report.setNeedColor(vo.getNeedColor());
			report.setNeedTimekey(vo.getNeedTimekey());
			report.setNeedDepartment(vo.getNeedDepartment());
			report.setCustomKey(vo.getCustomKey());
			report.setOtherDatekeyMode(vo.getOtherDatekeyMode());
			report.setParent(vo.getParent());
			report.setRemark(vo.getRemark());
			report.setTimekeyMode(vo.getTimekeyMode());
			report.setNeedControlPoint(vo.getNeedControlPoint());
			report.setSortID(vo.getSortID());
			report.setValid(vo.getValid());
			report.setOldVersion(vo.getOldVersion());
			report.setParameterModule(vo.getParameterModule());
			report.setJavascript(vo.getJavascript());
			report.setSubmitScript(vo.getSubmitScript());
			report.setExportType(vo.getExportType());
			ItModule module = (ItModule) hibernateSession.load(ItModule.class,
					vo.getModuleID());
			report.setModule(module);
			hibernateSession.save(report);
			tx.commit();
			result = (Integer)report.getId();
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
//
//	public IReport createReport(Integer id) throws CommonException {
//		return createReport(id, null);
//	}
//
//	public IReport createReport(Integer id, String parameters)
//			throws CommonException {
//		try {
//			String reportPath = helperRemote.getProperty("REPORT_RAQ_DIR");
//			String lic = helperRemote.getProperty("RAQ_LICENCE");
//			ReportModuleVO vo = this.getReportModuleByID(id);
//
//			String moduleName = vo.getModule();
//			String link = vo.getReport();
//			String reportFilePath = reportPath + moduleName + "/" + link;
//			logger.debug("get report path[" + reportFilePath + "]");
//			Context cxt = new Context();
//			ExtCellSet.setLicenseFileName(lic);
//			ReportDefine rd = (ReportDefine) ReportUtils.read(reportFilePath);
//			boolean containDateKey = false;
//			logger.debug("create report define ok,parameters=" + parameters);
//			if (SemAppUtils.isNotEmpty(parameters)) {
//				String[] parameter = parameters.split("&");
//				logger.debug("parameter.length=" + parameter.length);
//				for (int i = 0; i < parameter.length; i++) {
//					if (SemAppUtils.isNotEmpty(parameter[i])) {
//
//						String[] args = parameter[i].split("=");
//						if (args.length >= 2) {
//							if (!containDateKey)
//								containDateKey = "dateKey".equals(args[0]);
//							logger
//									.debug("parameter:" + args[0] + "="
//											+ args[1]);
//							cxt.setParamValue(args[0], args[1]);
//						}
//					}
//				}
//			}
//
//			if (!containDateKey) {
//				String dateKeyFormat = vo.getNeedDateKeyName();
//				logger.debug("dateKeyFormat=" + dateKeyFormat);
//				DateFormat format = new SimpleDateFormat(dateKeyFormat);
//				String dateKey = format
//						.format(Calendar.getInstance().getTime());
//				cxt.setParamValue("dateKey", dateKey);
//				logger.debug("dateKey=" + dateKey);
//			}
//			Engine engine = new Engine(rd, cxt);
//			logger.debug("create engine ok");
//			IReport iReport = engine.calc();
//			logger.debug("create report ok");
//			return iReport;
//		} catch (RemoteException e) {
//			logger.error("????????????[REPORT_RAQ_DIR]????????????", e);
//			throw new CommonException("????????????[REPORT_RAQ_DIR]????????????");
//		} catch (Exception e) {
//			logger.error("??????????????????", e);
//			throw new CommonException("????????????[REPORT_RAQ_DIR]????????????");
//		}
//	}

	public void scheduleReport() throws CommonException {
		logger.debug("start run report schedult!");
		try {
			hibernateSession = HibernateUtil.currentSession();
			String sql = "FROM ReportSchedule T WHERE (T.valid=0 ) and  date_format(T.nextExcuteDate,'%Y-%m-%d %H')=date_format(now(),'%Y-%m-%d %H') ";
			Query query = hibernateSession.createQuery(sql);
			Iterator iter = query.list().iterator();
			while (iter.hasNext()) {
				// ??????????????????????????????
				ReportSchedule reportSchedule = (ReportSchedule) iter.next();
				Integer scheduleID = (Integer)reportSchedule.getId();
				logger.debug("notice running report schedule[" + scheduleID
						+ "]");
				SemMessageObject messageObject = new SemMessageObject(null,
						scheduleID, new Integer(
								SemAppConstants.QUEUE_REPORT_SCHEDULE));
//				ObjectMessage objectMessage;
//				try {
//					objectMessage = qSession.createObjectMessage(messageObject);
//					qSender.send(objectMessage);
//				} catch (JMSException e) {
//					logger.error("??????????????????????????????", e);
//					throw new CommonException(e);
//				}
			}

		} catch (HibernateException ee) {
			logger.error("?????????????????????", ee);
			throw new CommonException("?????????????????????", ee);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
		}

	}

	public void scheduleOneReport(Integer reportScheduleID)
			throws CommonException {
		scheduleOneReport(reportScheduleID, false);

	}

	public void scheduleOneReport(Integer reportScheduleID,
			boolean refreshNextRunningTime) throws CommonException {
		Transaction tx = null;
		try {
			hibernateSession = HibernateUtil.currentSession();
			ReportSchedule reportSchedule = (ReportSchedule) hibernateSession
					.load(ReportSchedule.class, reportScheduleID);
			logger.debug("start schedule create report");

			// ????????????
			int type = 0;// default run each hour
			try {
				type = Integer.parseInt(helperRemote
						.getProperty("REPORT_SCHEDULE_TYPE"));
			} catch (Exception ee) {
				type = 0;
			}
			reportSchedule.setLastExcuteDate(Calendar.getInstance());
			try {
				if (reportSchedule.getRecipientsSources().intValue() == 0) {// ??????????????????????????????
					createAndSendReport((Integer)reportSchedule.getReportModule()
							.getId(), reportSchedule.getParameter(),
							reportSchedule.getReportName(), reportSchedule
									.getRecipients(), ""+reportSchedule
									.getRecipientsEmpID(),""+ reportSchedule
									.getRecipientsDpno(), reportSchedule
									.getSubject(), reportSchedule.getContent());
				} else {// ??????????????????????????????
					ItModule module = reportSchedule.getModule();
					String home = module.getServiceHome();
					String jndi = module.getServiceJndi();
					String remoteClass = module.getServiceRemote();
					String ejbServer = module.getDeployServer();
					String method = reportSchedule
							.getRecipientsImplementMethod();
					if (SemAppUtils.isEmpty(home) || SemAppUtils.isEmpty(jndi)
							|| SemAppUtils.isEmpty(remoteClass)) {
						throw new CommonException("调用的EJB服务配置不存在,method["
								+ method + "]jndi[" + jndi + "]servicesHome["
								+ home + "]servicesRemote[" + remoteClass
								+ "]不能为空");
					}
					Object remote = null;
//					try {
//						ServiceLocator locator = ServiceLocator.getInstance();
//						remote = (Object) locator.getObj(jndi, Class
//								.forName(home));
//						Method invokeMethod = remote.getClass().getMethod(
//								method, null);
//						PageMap result = (PageMap) invokeMethod.invoke(remote,
//								null);
//						Set key = result.getItems().keySet();
//						for (Iterator it = key.iterator(); it.hasNext();) {
//							String s = (String) it.next();
//							String value = (String) result.getItems().get(s);
//							logger.debug("key=" + s + ",value=" + value);
//							try{
//							if (SemAppUtils.isNotEmpty(value)) {
//								createAndSendReport((Integer)reportSchedule
//										.getReportModule().getId(), s,
//										reportSchedule.getReportName(), value,
//										null, null,
//										reportSchedule.getSubject(),
//										reportSchedule.getContent());
//							}
//							}catch(Exception ee){
//								logger.error("??????????????????????????????["+s+"],?????????["+value+"]",ee);
//							}
//						}
//
//					} catch (Exception ee) {
//						logger
//								.error(
//										"?????????EJB?????????????????????,method/jndi/servicesHome/servicesRemote????????????",
//										ee);
//						throw new CommonException(
//								"?????????EJB?????????????????????,method/jndi/servicesHome/servicesRemote????????????");
//					}
				}
				logger.debug("report schedule running id[" + reportScheduleID
						+ "]");
				reportSchedule.setLastExcuteResult(new Integer(0));
				// calculate the next run date
				if (refreshNextRunningTime) {
					reportSchedule.setNextExcuteDate(this
							.getNextRunningDate(reportSchedule.getCircleType()
									.intValue()));
				}

			} catch (Exception ee) {
				logger.error("create report failed", ee);
				reportSchedule.setLastExcuteResult(new Integer(1));
				reportSchedule.setLastExcuteResultRemark("error message["
						+ ee.getMessage() + "]");
//				SemAppUtils.publishMessage(SemAppConstants.MESSAGE_SYSTEM_ID,
//						"?????????????????????????????????", "??????["
//								+ reportSchedule.getReportModule().getName()
//								+ "],??????[" + reportSchedule.getParameter()
//								+ "],error message[" + ee.getMessage() + "]");
//
			}
			hibernateSession = HibernateUtil.currentSession();
			tx = hibernateSession.beginTransaction();
			hibernateSession.update(reportSchedule);
			tx.commit();
		} catch (HibernateException ee) {
			logger.error("数据库操作失败", ee);
			if (tx != null)
				tx.rollback();
			throw new CommonException("数据库操作失败" + ee.getMessage());

		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
		}
	}

	private void createAndSendReport(Integer reportModuleID, String parameter,
			String reportName, String recipients, String empid, String dpno,
			String subject, String content) throws RemoteException,
			CommException {
		// ???????????????????????????
//		IReport iReport = this.createReport(reportModuleID, parameter);
//		ParamMetaData paras = iReport.getParamMetaData();
//		//replace parameter to subject/content/reportName
//		for(int i=0;i<paras.getParamCount();i++){
//			Param param = paras.getParam(i);
//			String p="@"+param.getParamName();			
//			String value=param.getValue();
//			logger.debug("p=["+p+"] replace to ["+value+"]");
//			subject=subject.replaceAll(p,value);
//			content=content.replaceAll(p,value);
//			reportName=reportName.replaceAll(p,value);
//		}
//		String filename = helperRemote.getProperty("smtp.mail.attachPath")
//				+ File.separator + reportName + ".xls";
//		logger.debug("create report temp filename=" + filename);
//		ExcelReport exReport = new ExcelReport();
//		
//		exReport.export(iReport);
//		exReport.saveTo(filename);
//		// ??????mail
//		List toList = new ArrayList();
//		if (SemAppUtils.isNotEmpty(recipients)) {
//			String[] to = recipients.split(";");
//			for (int i = 0; i < to.length; i++) {
//				toList.add(to[i]);
//			}
//		}
//		if (SemAppUtils.isNotEmpty(empid)) {
//			String[] to = empid.split(",");
//			for (int i = 0; i < to.length; i++) {
//				if (SemAppUtils.isNotEmpty(to[i])) {
//					UsersVO user = SemAppUtils.getUserInfo(new Integer(to[i]));
//					if (user != null && SemAppUtils.isNotEmpty(user.getEmail())) {
//						toList.add(user.getEmail());
//					}
//				}
//			}
//		}
//		if (SemAppUtils.isNotEmpty(dpno)) {
//			String[] to = dpno.split(",");
//			for (int i = 0; i < to.length; i++) {
//				if (SemAppUtils.isNotEmpty(to[i])) {
//					//DepartmentVO department = SemAppUtils.getDeptInfo(to[i]);
//					//if (department != null) {
//					//	toList.add(department.getId() + "@soueast-motor.com");
//					//}
//				}
//			}
//		}
//		logger.debug("send report to users,contain " + toList.size() + " user");
//		if (!toList.isEmpty()) {// ?????????????????????
//			MailMessage message = new MailMessage(SemAppUtils
//					.list2Strings(toList), null, subject, content, null, null,
//					new String[] { filename });
//			commonRemote.sendMessageByMail(message);
//		}

	}

	private Calendar getNextRunningDate(int type) {
		Calendar cal = Calendar.getInstance();
		switch (type) {
		case SemAppConstants.REPORT_SCHEDULE_ONCE: {
			break;
		}
		case SemAppConstants.REPORT_SCHEDULE_HOUR: {
			cal.add(Calendar.HOUR, 1);
			break;
		}
		case SemAppConstants.REPORT_SCHEDULE_WEEK: {
			cal.add(Calendar.WEEK_OF_MONTH, 1);
			break;
		}
		case SemAppConstants.REPORT_SCHEDULE_DAY: {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			break;
		}
		case SemAppConstants.REPORT_SCHEDULE_MONTH: {
			cal.add(Calendar.MONTH, 1);
			break;
		}
		case SemAppConstants.REPORT_SCHEDULE_YEAR: {
			cal.add(Calendar.YEAR, 1);
			break;
		}
		}
		return cal;
	}

	public PageList getReportScheduleList(Integer systemID,
			ReportScheduleVO searchVO, Integer firstResult, Integer fetchSize)
			throws CommonException {
		try {
			hibernateSession = HibernateUtil.currentSession();
			Criteria criteria = hibernateSession
					.createCriteria(ReportSchedule.class);
			int size = fetchSize.intValue();
			int system = systemID.intValue();
			if (this.isSystemModule(system)) {
				system = 0;// ???????????????????????????????????????????????????
			}
			if (searchVO != null) {
				if (system == 0 && searchVO.getModuleID() != null) {
					system = searchVO.getModuleID().intValue();
				}
				if (searchVO.getReportModuleID() != null) {
					ReportModule reportModule = (ReportModule) hibernateSession
							.load(ReportModule.class, searchVO
									.getReportModuleID());
					criteria.add(Expression.eq("reportModule", reportModule));
				}
				if (searchVO.getReportName() != null) {
					criteria.add(Expression.like("reportName", "%"
							+ searchVO.getReportName() + "%"));
				}
				if (searchVO.getRemark() != null) {
					criteria.add(Expression.like("remark", "%"
							+ searchVO.getRemark() + "%"));
				}
				if (searchVO.getValid() != null) {
					criteria.add(Expression.eq("valid", searchVO.getValid()));
				}
				if (searchVO.getLastExcuteResult() != null) {
					criteria.add(Expression.eq("lastExcuteResult", searchVO
							.getLastExcuteResult()));
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
				ReportSchedule reportSchedule = (ReportSchedule) iter.next();
				ReportScheduleVO vo = (ReportScheduleVO) reportSchedule.toVO();
				list.add(vo);
			}
			logger.debug("report admin EJB rowCount=" + rowCount);
			PageList pageList = new PageList();
			pageList.setResults(rowCount.intValue());
			pageList.setItems(list);
			return pageList;
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new CommonException("数据库操作失败" + ee.getMessage());

		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
		}
	}

	public PageList getReportScheduleList(Integer systemID)
			throws CommonException {
		return getReportScheduleList(systemID, null, new Integer(0),
				new Integer(0));
	}

	public PageList getReportScheduleList() throws CommonException {
		return getReportScheduleList(new Integer(0));
	}

	public ReportModuleVO getReportScheduleByID(Integer reportScheduleID)
			throws CommonException {
		ReportModuleVO reportVO = null;
		try {
			hibernateSession = HibernateUtil.currentSession();
			ReportSchedule reportSchedule = (ReportSchedule) hibernateSession
					.load(ReportSchedule.class, reportScheduleID);
			reportVO = (ReportModuleVO) reportSchedule.toVO();
		} catch (HibernateException ee) {
			logger.error("数据库操作失败", ee);
			throw new CommonException("数据库操作失败" + ee.getMessage());

		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
		}
		return reportVO;
	}

	public void updateScheduleModule(ReportScheduleVO vo)
			throws CommonException {
		logger.debug("update  reportModule" + vo);
		Transaction tx = null;
		try {
			hibernateSession = HibernateUtil.currentSession();
			tx = hibernateSession.beginTransaction();
			ReportSchedule reportSchedule = (ReportSchedule) hibernateSession
					.load(ReportSchedule.class, vo.getId());
			reportSchedule.setCircleType(vo.getCircleType());
			reportSchedule.setContent(vo.getContent());
			reportSchedule.setFirstExcuteDate(vo.getFirstExcuteDate());
			// reportSchedule.setLastExcuteDate(vo.getLastExcuteDate());
			// reportSchedule.setLastExcuteResult(vo.getLastExcuteResult());
			// reportSchedule.setLastExcuteResultRemark(vo
			// .getLastExcuteResultRemark());

			reportSchedule.setNextExcuteDate(vo.getNextExcuteDate());
			reportSchedule.setParameter(vo.getParameter());
			reportSchedule.setRecipients(vo.getRecipients());
			reportSchedule.setRecipientsEmpID(vo.getRecipientsEmpID());
			reportSchedule.setRecipientsDpno(vo.getRecipientsDpno());
			reportSchedule.setRecipientsImplementMethod(vo
					.getRecipientsImplementMethod());
			reportSchedule.setRecipientsSources(vo.getRecipientsSources());
			reportSchedule.setRecipientsType(vo.getRecipientsType());
			reportSchedule.setRemark(vo.getRemark());
			reportSchedule.setReportName(vo.getReportName());
			reportSchedule.setSubject(vo.getSubject());
			reportSchedule.setValid(vo.getValid());
			hibernateSession.update(reportSchedule);
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

	public void deleteReportSchedule(Integer id) throws CommonException {
		Transaction tx = null;
		try {

			hibernateSession = HibernateUtil.currentSession();
			ReportSchedule reportSchedule = (ReportSchedule) hibernateSession
					.load(ReportSchedule.class, id);
			tx = hibernateSession.beginTransaction();
			hibernateSession.delete(reportSchedule);
			tx.commit();
		} catch (HibernateException ee) {

			logger.error(ee);
			throw new CommonException("数据库操作失败" + ee.getMessage());

		} finally {
			if (tx != null)
				tx.rollback();
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
			}
		}
	}

	public Integer addReportSchedule(ReportScheduleVO vo)
			throws CommonException {
		Integer result = null;
		logger.debug("add it UABinding" + vo);
		Transaction tx = null;
		try {
			hibernateSession = HibernateUtil.currentSession();
			tx = hibernateSession.beginTransaction();
			ReportSchedule reportSchedule = new ReportSchedule();
			reportSchedule.setCircleType(vo.getCircleType());
			reportSchedule.setContent(vo.getContent());
			reportSchedule.setFirstExcuteDate(vo.getFirstExcuteDate());
			reportSchedule.setLastExcuteDate(vo.getLastExcuteDate());
			reportSchedule.setLastExcuteResult(vo.getLastExcuteResult());
			reportSchedule.setLastExcuteResultRemark(vo
					.getLastExcuteResultRemark());
			reportSchedule.setNextExcuteDate(vo.getNextExcuteDate());
			reportSchedule.setParameter(vo.getParameter());
			reportSchedule.setRecipients(vo.getRecipients());
			reportSchedule.setRecipientsDpno(vo.getRecipientsDpno());
			reportSchedule.setRecipientsEmpID(vo.getRecipientsEmpID());
			reportSchedule.setRecipientsImplementMethod(vo
					.getRecipientsImplementMethod());
			reportSchedule.setRecipientsSources(vo.getRecipientsSources());
			reportSchedule.setRecipientsType(vo.getRecipientsType());
			reportSchedule.setRemark(vo.getRemark());
			reportSchedule.setReportName(vo.getReportName());
			reportSchedule.setSubject(vo.getSubject());
			reportSchedule.setValid(vo.getValid());
			ItModule module = (ItModule) hibernateSession.load(ItModule.class,
					vo.getModuleID());
			reportSchedule.setModule(module);
			ReportModule reportModule = (ReportModule) hibernateSession.load(
					ReportModule.class, vo.getReportModuleID());
			reportSchedule.setReportModule(reportModule);
			hibernateSession.save(reportSchedule);
			tx.commit();
			result = (Integer)reportSchedule.getId();
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

	

	private List getSubmodules(int system, Session hibernateSession) {
//		return SemAppUtils.getSubmodules(system, hibernateSession);
		return null;
	}
	private boolean isSystemModule(int systemID) {
		return systemID == 0 || systemID == SemAppConstants.COMMON_MODULE_ID;
	}
}
