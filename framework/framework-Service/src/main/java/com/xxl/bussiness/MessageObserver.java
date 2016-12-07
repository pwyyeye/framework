package com.xxl.bussiness;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.xxl.HibernateUtil;
import common.businessObject.MessageEvent;
import common.businessObject.MessageSubscibe;
import common.bussiness.Message;
import common.os.vo.UsersVO;
import common.utils.SemAppUtils;
import common.web.utils.SemWebAppConstants;

public class MessageObserver implements Observer {
	public static Log logger = LogFactory.getLog(MessageObserver.class);

	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Message) {
			Message message = (Message) arg1;
			List mailList = new ArrayList();
			List appPushList = new ArrayList();
			List mobileList = new ArrayList();
			try {
				Session session = HibernateUtil.currentSession();
				MessageEvent event = (MessageEvent) session.load(
						MessageEvent.class, new Integer(message.getType()));
				Iterator iter = event.getSubscibes().iterator();
				while (iter.hasNext()) {
					MessageSubscibe subscibe = (MessageSubscibe) iter.next();
					Calendar now = Calendar.getInstance();
					if (now.after(subscibe.getBeginDate())
							&& now.before(subscibe.getEndDate())) {
						switch (subscibe.getRoute()) {
						case SemWebAppConstants.MESSAGE_ROUTE_OA: {
							appPushList.add(subscibe.getEmpID());
							break;
						}
						case SemWebAppConstants.MESSAGE_ROUTE_MAIL: {
							mailList.add(subscibe.getEmpID());
							break;
						}
						case SemWebAppConstants.MESSAGE_ROUTE_MOBILE_NOTE: {
							mobileList.add(subscibe.getEmpID());
							break;
						}
						}
					}

				}
			} catch (HibernateException ee) {
				logger.error("get MessageObserver fail", ee);
			} finally {
				try {
					HibernateUtil.closeSession();
				} catch (HibernateException e) {
				}
			}
			Iterator iter = mailList.iterator();
			while (iter.hasNext()) {
				Integer empID = (Integer) iter.next();
				try {
					UsersVO user = SemAppUtils.getUserInfo(empID);
					String email = user.getEmail();
					MailSender.init();
					MailSender.getTheInstance().sendMail(
							new String[] { email }, null, message.getSubject(),
							message.getContent(), null, null,
							message.getAttachs());
				} catch (Exception ee) {
					logger.error("send mail message failer[" + empID + "]", ee);
				}
			}

			iter = appPushList.iterator();
			while (iter.hasNext()) {
				Integer empID = (Integer) iter.next();
				// UsersVO user = SemAppUtils.getUserInfo(empID);
				// if(user!=nu)
				try {
//					SemAppUtils.sendAppPushMessage(new Integer(0),"" + empID, message
//							.getSubject(), message.getContent(),new Integer(0));
				} catch (Exception e) {
					logger.error("send app push message failer[" + empID + "]",
							e);
				}
			}
			
			iter = mobileList.iterator();
			while (iter.hasNext()) {
				Integer empID = (Integer) iter.next();
				 UsersVO user = SemAppUtils.getUserInfo(empID);
				 if(user!=null&&SemAppUtils.isNotEmpty(user.getMobile())){
				try {
//					SemAppUtils.sendMessageByMobile(user.getMobile(), new String[]{message.getContent()});
				} catch (Exception e) {
					logger.error("send app push message failer[" + empID + "]",
							e);
				}
				 }else{
					 logger.error("user not exist or user's mail not exist [" + empID + "]");
				 }
			}
		}

	}
}
