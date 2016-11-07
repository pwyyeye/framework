package com.xxl.os.dao.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.xxl.os.dao.UserDutyDAO;

import common.dao.impl.BaseDAOImpl;

@Repository("userDutyDAO")
public class  UserDutyDAOImpl extends BaseDAOImpl implements UserDutyDAO{

public Log logger = LogFactory.getLog(this.getClass());
    
	

}
