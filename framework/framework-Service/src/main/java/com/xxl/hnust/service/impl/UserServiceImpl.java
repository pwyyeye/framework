package com.xxl.hnust.service.impl;  
  
import java.util.Calendar;

import javax.annotation.Resource;  

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;  

import com.common.value.PageList;
import com.common.utils.SemAppUtils;
import com.xxl.exception.UserBusinessException;
import com.xxl.exception.UserException;
import com.xxl.hnust.bo.User;
import com.xxl.hnust.dao.IUserDao;
import com.xxl.hnust.service.IUserService;
import com.xxl.hnust.vo.UserVo;
  
@Service("userService")  
public class UserServiceImpl implements IUserService {
	
	public static Log logger = LogFactory.getLog(UserServiceImpl.class);
	
    @Resource
    private IUserDao userDao;
    
    public UserVo findById(Integer id) {
    	User user = (User) userDao.findById(id, User.class);
    	UserVo vo = null;
    	if(user!=null) {
    		vo = (UserVo) user.toVO();
    	}
    	return vo;
    }
    
    
    public PageList findByPage(UserVo vo, Integer firstResult, Integer fetchSize) {
    	DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
    	criteria.addOrder(Order.asc("id"));
    	PageList pageList = userDao.findByCriteriaByPage(criteria, firstResult, fetchSize);
    	return pageList;
    }
    
    public String addUser(UserVo vo) {
    	User bo = new User();
    	SemAppUtils.beanCopy(vo, bo);
    	String id = userDao.save(bo);
    	return id;
    }
    
    public void updateUser(UserVo vo) {
    	User bo = (User) userDao.findById(vo.getId(), User.class);
    	SemAppUtils.beanCopy(vo, bo);
    	userDao.saveOrUpdate(bo);
    }
    
    public void deleteUser(Integer id) throws UserBusinessException, UserException {
    	//try {
    		userDao.deleteById(id, User.class);
    		
    		// 打开测试事务
        	/*User bo = (User) userDao.findById(id, User.class);
        	bo.setUserName("abcdefg");
        	userDao.saveOrUpdate(bo);*/
    	/*} catch(Exception e) {
    		logger.error("更新用户发生异常!", e);
    		throw new UserBusinessException("更新用户发生异常!");
    	}*/
    }
    
    public UserVo findUser(Integer userId) {
    	UserVo vo = userDao.findUserById(userId);
    	return vo;
    }
    
    public UserVo findUser(String userName) throws UserBusinessException, UserException {
    	UserVo vo = userDao.findUserByName(userName);
    	return vo;
    }

	public void testTaskJob0() throws UserException {
		System.out.println("测试是否能调用到service里的方法000");
		throw new UserException("定时任务执行失败！！！！！！"+SemAppUtils.getFullTime(Calendar.getInstance()));
	}
	
	public void testTaskJob1() {
		System.out.println("测试是否能调用到service里的方法111");
	}
    
}