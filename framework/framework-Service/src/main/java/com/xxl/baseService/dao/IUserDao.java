package com.xxl.baseService.dao;

import com.xxl.baseService.bo.User;
import com.xxl.baseService.vo.UserVo;
import com.xxl.exception.UserBusinessException;
import com.xxl.exception.UserException;

import common.dao.BaseDAO;

public interface IUserDao extends BaseDAO<User, java.lang.String> {
    
    public UserVo findUserById(Integer userId);
    
    public UserVo findUserByName(String userName) throws UserBusinessException, UserException;
    
}