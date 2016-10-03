package com.xxl.hnust.service;  
  
import com.common.value.PageList;
import com.xxl.exception.UserBusinessException;
import com.xxl.exception.UserException;
import com.xxl.hnust.vo.UserVo;
  
public interface IUserService {  
    
    public UserVo findById(Integer id);
    
    public PageList findByPage(UserVo vo, Integer firstResult, Integer fetchSize);
    
    public String addUser(UserVo vo);
    
    public void updateUser(UserVo vo);
    
    public void deleteUser(Integer id) throws UserBusinessException, UserException;
    
    public UserVo findUser(Integer userId);
    
    public UserVo findUser(String userName) throws UserBusinessException, UserException;
    
    public void testTaskJob0() throws UserException;
    
    public void testTaskJob1();
    
}