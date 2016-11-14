package com.xxl.jms.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.xxl.jms.dao.UserPushDAO;

import common.dao.impl.BaseDAOImpl;
import common.jms.vo.UserPushVO;
import common.value.PageList;

@Repository("userPushDAO")
public class UserPushImpl extends BaseDAOImpl implements UserPushDAO{
	private static final Log logger = LogFactory.getLog(UserPushImpl.class);
	public PageList getUserAllPush(final Integer userId, final Integer moduleID) {
		return null;
//		return (PageList) getHibernateTemplate().execute(new HibernateCallback() {
//			public Object doInHibernate(Session session) {
//				try {
//					//PageList results = new PageList();
//					String sql = " select t.id,eia_user_push_type.id,t.onFlag,t.systemid,eia_user_push_type.name from eia_user_push_type " +
//							"left join  (select * from eia_user_push where eia_user_push.userId=?) t  " +
//							"on t.type = eia_user_push_type.id   " +
//							"where eia_user_push_type.custom=1 ";
//					if(moduleID!=null && moduleID.intValue()!=0) {
//						sql = sql + "and eia_user_push_type.SYSTEMID='" + moduleID + "'";
//					}
//					CallableStatement cs = session.connection()
//							.prepareCall(sql);
//
//					cs.setObject(1, userId);
//					ResultSet result = cs.executeQuery();
//					List list=new ArrayList();
//					while (result.next()) {
//						UserPushVO vo=new UserPushVO(null);
//						Integer id=result.getInt(1);
//						vo.setId(id);
//						//Integer userId=result.getInt(2);
//						vo.setUserId(userId);
//						Integer type=result.getInt(2);
//						vo.setType(type);
//						Integer onFlag=result.getInt(3);
//						if(onFlag==null||(id!=null&&id.intValue()==0)){
//							onFlag=new Integer(1);							
//						}
//						vo.setOn(onFlag);
//						Integer moduleID=result.getInt(4);
//						vo.setModuleID(moduleID);
//						String name=result.getString(5);
//						vo.setTypeName(name);
//			
//						list.add(vo);
//					}
//					cs.close();
//					return new PageList(list);
//				} catch (Exception e) {
//					logger.error("获取在线用户信息失败：", e);
//					throw new RuntimeException(e);
//				}
//			}
//		});
	}
}
