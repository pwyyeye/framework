package com.xxl.os.dao;

import java.util.Calendar;

import common.dao.BaseDAO;
import common.os.vo.UsersVO;
import common.os.vo.exception.OSException;


public interface OnlineDAO extends BaseDAO{
	public String getUserToken(final Integer empId);
	public UsersVO getEofficeLoginUser( final String ip,
			final String authKey, final Calendar expiredTime, final int type,
			final boolean isTest, final boolean checkActive)throws OSException ;
}
