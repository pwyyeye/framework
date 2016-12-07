package com.xxl.jms.dao;

import java.util.Map;

import common.dao.BaseDAO;

public interface UserPushLogDAO extends BaseDAO{
	public Map getUnReadPush(final Integer userId) ;
}
