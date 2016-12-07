package com.xxl.baseService.dao;

import common.dao.BaseDAO;
import common.os.vo.UsersVO;

public interface IFrameworkDao extends BaseDAO<Object, java.lang.String> {

	public UsersVO getUserInfo(String userID) throws Exception;
	
	public String getUserToken(String empID);
}