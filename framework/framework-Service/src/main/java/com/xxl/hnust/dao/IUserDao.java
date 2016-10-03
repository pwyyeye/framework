package com.xxl.hnust.dao;

import com.common.dao.BaseDAO;
import com.xxl.exception.UserBusinessException;
import com.xxl.exception.UserException;
import com.xxl.hnust.bo.User;
import com.xxl.hnust.vo.UserVo;

public interface IUserDao extends BaseDAO<User, java.lang.String> {
    
    public UserVo findUserById(Integer userId);
    
    public UserVo findUserByName(String userName) throws UserBusinessException, UserException;
    
}