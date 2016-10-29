package com.xxl.baseService.service.impl;  
  
import java.util.Calendar;

import javax.annotation.Resource;  

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;  

import com.xxl.baseService.bo.User;
import com.xxl.baseService.dao.IUserDao;
import com.xxl.baseService.service.IUserService;
import com.xxl.baseService.vo.UserVo;
import com.xxl.exception.FrameworkBusinessException;
import com.xxl.exception.FrameworkException;

import common.utils.SemAppUtils;
import common.value.PageList;
  
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
    
    public void deleteUser(Integer id) throws FrameworkBusinessException, FrameworkException {
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
    
    public UserVo findUser(String userName) throws FrameworkBusinessException, FrameworkException {
    	UserVo vo = userDao.findUserByName(userName);
    	return vo;
    }

	public void testTaskJob0() throws FrameworkException {
		System.out.println("测试是否能调用到service里的方法000");
		throw new FrameworkException("定时任务执行失败！！！！！！"+SemAppUtils.getFullTime(Calendar.getInstance()));
	}
	
	public void testTaskJob1() {
		System.out.println("测试是否能调用到service里的方法111");
	}
    
}