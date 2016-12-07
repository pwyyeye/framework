package com.xxl.os.dao.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.xxl.os.dao.UsersDAO;

import common.dao.impl.BaseDAOImpl;

@Repository("usersDAO")
public class  UsersDAOImpl extends BaseDAOImpl implements UsersDAO{

public Log logger = LogFactory.getLog(this.getClass());
    
	

}
