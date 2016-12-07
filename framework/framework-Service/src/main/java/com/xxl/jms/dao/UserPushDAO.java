package com.xxl.jms.dao;

import common.dao.BaseDAO;
import common.value.PageList;

public interface UserPushDAO extends BaseDAO{
	public PageList getUserAllPush(final Integer userId, final Integer moduleID) ;
}
