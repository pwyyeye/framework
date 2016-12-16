package com.xxl.jms.service;

import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xxl.facade.CommonRemote;
import com.xxl.facade.JMSTaskRemote;
import com.xxl.facade.ReportRemote;

import common.utils.SemAppConstants;
import common.value.MailMessage;
import common.value.SemMessageObject;
@Component("queueReceiver")
public class QueueReceiver  implements MessageListener {
	public Log logger = LogFactory.getLog(this.getClass());

	private MessageDrivenContext ctx;

	@Autowired
	private CommonRemote commonRemote;

	private ReportRemote reportRemote;

//	private TaskRemote taskRemote;
	@Autowired
	private JMSTaskRemote jMSTaskRemote;

	public void onMessage(Message msg) {
		ObjectMessage message = (javax.jms.ObjectMessage) msg;
		SemMessageObject messageObject = null;
		try {
			messageObject = (SemMessageObject) message.getObject();
		} catch (JMSException e1) {
			logger.error("Message处理失败", e1);
		}
		int subQueue = messageObject.getSubQueueID().intValue();
		logger.debug("subQUEQUE=[" + subQueue + "]");
		switch (subQueue) {
		case SemAppConstants.MAIL_QUEUE: {
			try {
				MailMessage mailMessage = (MailMessage) messageObject
						.getContentObj();
				jMSTaskRemote.sendMessageByMail(mailMessage);
			} catch (Exception e) {
				logger.error("异步发送邮件失败", e);
			}
			break;
		}
		case SemAppConstants.QUEUE_TASK_SCHEDULE: {
			try {
				Integer reportScheduleID = (Integer) messageObject
						.getContentObj();
//				taskRemote.excuteTask(reportScheduleID);
			} catch (Exception e) {
				logger.error("报表调度失败", e);
			}
			break;
		}
		case SemAppConstants.QUEUE_REPORT_SCHEDULE: {
			try {
				Integer reportScheduleID = (Integer) messageObject
						.getContentObj();
//				reportAdmin.scheduleOneReport(reportScheduleID, true);
			} catch (Exception e) {
				logger.error("报表调度失败", e);
			}
			break;
		}
		case SemAppConstants.QUEUE_PUSH_MESSAGE: {
			try {
//				PushMessage pushMessage = (PushMessage) messageObject
//						.getContentObj();
//				commonRemote.sendAppPushMessage(pushMessage);
			} catch (Exception e) {
				logger.error("报表调度失败", e);
			}
			break;
		}
		}

	}
}
