package com.xxl.baseService.dao;

import java.util.List;
import java.util.Map;

import com.xxl.baseService.bo.SysUsers;
import com.xxl.baseService.vo.SysAuthoritiesVo;
import com.xxl.exception.UserBusinessException;
import com.xxl.exception.UserException;

import common.dao.BaseDAO;

public interface ISysUserDao extends BaseDAO<SysUsers, java.lang.String> {
    
	public List<SysAuthoritiesVo> getSysAuthoritiesByUsername(String userName) 
			throws UserBusinessException, UserException;
	
	public List<Map<String,String>> getURLResourceMapping();
    
}