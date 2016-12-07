package com.xxl;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HibernateUtil {

  private static Log log = LogFactory.getLog(HibernateUtil.class);

  private static final SessionFactory sessionFactory;

  static {
    try {
      // Create the SessionFactory
      sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    catch (Throwable ex) {
      // Make sure you log the exception, as it might be swallowed
      log.error("Initial SessionFactory creation failed.", ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static final ThreadLocal session = new ThreadLocal();

  public static Session currentSession() throws HibernateException {
    Session s = (Session) session.get();
    // Open a new Session, if this Thread has none yet
    if (s == null) {
      s = sessionFactory.openSession();
      session.set(s);
    }
    return s;
  }

  public static void closeSession() throws HibernateException {
    Session s = (Session) session.get();
    session.set(null);
    if (s != null)
      s.close();
  }
}