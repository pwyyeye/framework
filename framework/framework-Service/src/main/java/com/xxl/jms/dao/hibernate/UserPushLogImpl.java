package com.xxl.jms.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.xxl.jms.dao.UserPushLogDAO;

import common.dao.impl.BaseDAOImpl;
import common.jms.vo.UserPushVO;
@Repository("userPushLogDAO")
public class UserPushLogImpl extends BaseDAOImpl implements UserPushLogDAO{
	public Map getUnReadPush(final Integer userId) {
		return null;
//		return (Map) getHibernateTemplate().execute(new HibernateCallback() {
//			public Object doInHibernate(Session session) {
//				try {
//					String sql = " select type,count(id) from eia_user_push_log where userId=?  and hasRead='0' group by type";
//					CallableStatement cs = session.connection()
//							.prepareCall(sql);
//
//					cs.setObject(1, userId);
//					ResultSet result = cs.executeQuery();
//					Map map=new HashMap();
//					while (result.next()) {
//						UserPushVO vo=new UserPushVO(null);
//						Integer type=result.getInt(1);
//					    Integer count=result.getInt(2);
//			            map.put(""+type, ""+count);
//					}
//					cs.close();
//					return map;
//				} catch (Exception e) {
//					logger.error("获取在线用户信息失败：", e);
//					throw new RuntimeException(e);
//				}
//			}
//		});
	}
	
}
