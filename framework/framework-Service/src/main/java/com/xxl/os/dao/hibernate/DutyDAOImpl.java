package com.xxl.os.dao.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.xxl.os.dao.DutyDAO;

import common.dao.impl.BaseDAOImpl;

@Repository("uutyDAO")
public class  DutyDAOImpl extends BaseDAOImpl implements DutyDAO{

public Log logger = LogFactory.getLog(this.getClass());
    
	

}
