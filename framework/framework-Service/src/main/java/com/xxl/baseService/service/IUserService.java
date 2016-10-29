package com.xxl.baseService.service;  
  
import com.xxl.baseService.vo.UserVo;
import com.xxl.exception.FrameworkBusinessException;
import com.xxl.exception.FrameworkException;

import common.value.PageList;
  
public interface IUserService {  
    
    public UserVo findById(Integer id);
    
    public PageList findByPage(UserVo vo, Integer firstResult, Integer fetchSize);
    
    public String addUser(UserVo vo);
    
    public void updateUser(UserVo vo);
    
    public void deleteUser(Integer id) throws FrameworkBusinessException, FrameworkException;
    
    public UserVo findUser(Integer userId);
    
    public UserVo findUser(String userName) throws FrameworkBusinessException, FrameworkException;
    
    public void testTaskJob0() throws FrameworkException;
    
    public void testTaskJob1();
    
}