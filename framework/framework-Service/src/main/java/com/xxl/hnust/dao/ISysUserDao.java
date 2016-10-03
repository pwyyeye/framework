package com.xxl.hnust.dao;

import java.util.List;
import java.util.Map;

import com.common.dao.BaseDAO;
import com.xxl.exception.UserBusinessException;
import com.xxl.exception.UserException;
import com.xxl.hnust.bo.SysUsers;
import com.xxl.hnust.vo.SysAuthoritiesVo;

public interface ISysUserDao extends BaseDAO<SysUsers, java.lang.String> {
    
	public List<SysAuthoritiesVo> getSysAuthoritiesByUsername(String userName) 
			throws UserBusinessException, UserException;
	
	public List<Map<String,String>> getURLResourceMapping();
    
}