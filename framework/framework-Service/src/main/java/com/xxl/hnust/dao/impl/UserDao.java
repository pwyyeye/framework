package com.xxl.hnust.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xxl.exception.UserBusinessException;
import com.xxl.exception.UserException;
import com.xxl.hnust.bo.User;
import com.xxl.hnust.dao.IUserDao;
import com.xxl.hnust.vo.UserVo;
import common.dao.impl.BaseDAOImpl;

@Repository
public class UserDao extends BaseDAOImpl<User, java.lang.String> implements IUserDao {

	public UserVo findUserById(Integer userId) {
		Query query = getCurrentSession().createSQLQuery("select u.id, u.user_name, u.password, u.age, u.roles from user_t u where u.id=?");
		query.setParameter(0, userId);
		Object[] object = (Object[]) query.list().get(0);
		UserVo vo = new UserVo();
		vo.setId((Integer) object[0]);
		vo.setUserName((String) object[1]);
		vo.setPassword((String) object[2]);
		vo.setAge((Integer) object[3]);
		vo.setRoles((String) object[4]);
		return vo;
	}
	
	public UserVo findUserByName(String userName) throws UserBusinessException, UserException {
		Query query = getCurrentSession().createSQLQuery("select u.id, u.user_name, u.password, u.age, u.roles from user_t u where u.user_name=?");
		query.setParameter(0, userName);
		Object[] object = (Object[]) query.list().get(0);
		UserVo vo = new UserVo();
		vo.setId((Integer) object[0]);
		vo.setUserName((String) object[1]);
		vo.setPassword((String) object[2]);
		vo.setAge((Integer) object[3]);
		vo.setRoles((String) object[4]);
		return vo;
	}

}
