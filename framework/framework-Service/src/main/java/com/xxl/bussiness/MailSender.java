package com.xxl.bussiness;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.*;
import javax.mail.internet.*;
import javax.mail.Message.RecipientType;
import javax.mail.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.bussiness.CommException;
import common.utils.SemAppUtils;
import common.value.MailMessage;

public class MailSender {
	private String mailHost;

	private String mailFrom;

	private String user;

	private String password;

	private String mailPort;

	private String mailProtocol;

	private String attachPath;

	private static MailSender theInstance;

	public Log logger = LogFactory.getLog(this.getClass());

	public static MailSender getTheInstance() {
		return theInstance;
	}

	public static void init() throws NamingException {
		if (theInstance == null) {
			theInstance = new MailSender();
		}
	}

	public static void end() {
		theInstance = null;
	}

	public MailSender() {
		try {
			InitialContext ic = new InitialContext();
			user = SemAppUtils.getProperty("smtp.mail.user");
			password = SemAppUtils.getProperty("smtp.mail.password");
			mailHost = SemAppUtils.getProperty("smtp.mail.Host");
			mailFrom = SemAppUtils.getProperty("smtp.mail.from");
			mailPort = SemAppUtils.getProperty("smtp.mail.port");
			mailProtocol = SemAppUtils.getProperty("smtp.mail.protocol");
			attachPath = SemAppUtils.getProperty("smtp.mail.attachPath");
			File file = new File(attachPath);
			if (!file.exists())
				file.mkdir();
			// user = "semit";
			// password = "Sys123!";
			// mailHost = "172.16.1.111";
			// mailFrom = "semit@soueast-motor.com";
			// mailPort = "25";
			// mailProtocol = "SMTP";
		} catch (Exception ex) {
			logger.error("初始化MailSender失败", ex);
		}
	}

	public void sendMail(String[] to, String[] cc, String subject, String text,
			String from, String host) throws CommException {
		sendMail(to, cc, subject, text, from, host, null);

	}

	public void sendMail(String to, String cc, String subject, String text,
			String from, String host) throws CommException {

		sendMail(new String[] { to }, new String[] { cc }, subject, text, from,
				host, null);

	}

	public void sendMail(String[] to, String[] cc, String subject, String text,
			String from, String host, String[] filenames) throws CommException {
		String realHost = (host == null || host.trim().length() < 1) ? this.mailHost
				: host;
		Properties properties = new Properties();
		properties.put("mail.smtp.host", realHost);
		properties.put("mail.smtp.port", mailPort);
		properties.put("mail.transport.protocol", mailProtocol);
		properties.put("mail.smtp.auth", "true");
		Session mailSession = Session.getInstance(properties);
		MimeMessage message = new MimeMessage(mailSession);
		MimeBodyPart bodyPart;
		MimeMultipart multiPart = new MimeMultipart();

		for (int i = 0; filenames != null && i < filenames.length; i++) {
			String filename = getFullFilePath(filenames[i]);
			try {
				FileDataSource fds = new FileDataSource(filename);
				bodyPart = new MimeBodyPart();
				bodyPart.setDataHandler(new DataHandler(fds));
				String mailFilename=fds.getName();
				try{
					mailFilename=MimeUtility.encodeWord(fds.getName());
				}catch(Exception ee){
					mailFilename=fds.getName();
				}
				bodyPart.setFileName(mailFilename);
				multiPart.addBodyPart(bodyPart);
			} catch (MessagingException e) {
				throw new CommException("发送邮件附件失败，附件不存在或文件超过10M:" + filename, e);
			}
		}
		if (text != null) {
			bodyPart = new MimeBodyPart();
			try {
				bodyPart.setText(text);
				multiPart.addBodyPart(bodyPart);
			} catch (MessagingException e) {
				throw new CommException("邮件正文发送失败，" + text, e);
			}

		}
		if (to == null || to.length < 1) {
			throw new CommException("该邮件未设接收者，请确认!");
		}
		logger.debug("will send mail to" + to);
		try {
			InternetAddress[] addresses = new InternetAddress[to.length];
			int i;
			for (i = 0; i < to.length; i++) {
				InternetAddress address = new InternetAddress(to[i]);
				addresses[i] = address;
			}
			if (i != 0) {
				message.setRecipients(RecipientType.TO, addresses);
			}
		} catch (AddressException ex) {
			throw new CommException("设置接收者失败:" + to);
		} catch (MessagingException ex1) {
			throw new CommException("设置接收者失败" + to);
		}
		try {

			if (cc != null) {
				InternetAddress[] addresses = new InternetAddress[cc.length];
				int i;
				for (i = 0; i < cc.length && SemAppUtils.isNotEmpty(cc[i]); i++) {
					InternetAddress address = new InternetAddress(cc[i]);
					addresses[i] = address;
				}
				if (i != 0) {
					message.setRecipients(RecipientType.CC, addresses);
				}
			}
		} catch (Exception ex) {
			logger.error("设置抄送者", ex);
		}

		try {
			String realFrom = (from == null || from.trim().length() < 1) ? this.mailFrom
					: from;
			InternetAddress fromAddress = new InternetAddress(realFrom);
			message.setFrom(fromAddress);
		} catch (Exception ex2) {
			logger.error("设置发送者失败", ex2);
		}
		try {
			message.setContent(multiPart);
			message.setSubject(subject);
		} catch (MessagingException ex3) {
			logger.error("生成邮件内容失败", ex3);
		}
		try {
			Transport transport = mailSession.getTransport("smtp");
			logger.debug("connect to [" + realHost + "],user[" + user
					+ "],password[" + password + "],port[" + mailPort + "]");
			transport.connect(realHost, user, password);
			logger.debug("MailSender start send mail");

			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			logger.debug("MailSender end send mail");
		} catch (MessagingException ex3) {
			logger.error("发送邮件失败", ex3);
			throw new CommException("发送邮件失败" + ex3.getMessage());
		}
	}

	public void sendMail(MailMessage message) throws CommException {

		sendMail(message.getTo(), message.getCc(), message.getSubject(),
				message.getText(), message.getFrom(), message.getHost(),
				message.getFilenames());
	}

	private String getFullFilePath(String filename) {
		return (filename != null && filename.indexOf(":") == -1) ? attachPath
				+ "/" + filename : filename;
	}

}
