package com.xxl.baseService.dao;

import java.util.List;
import java.util.Map;

import com.xxl.baseService.bo.SysUsers;
import com.xxl.baseService.vo.SysAuthoritiesVo;
import com.xxl.exception.FrameworkBusinessException;
import com.xxl.exception.FrameworkException;

import common.dao.BaseDAO;

public interface ISysUserDao extends BaseDAO<SysUsers, java.lang.String> {
    
	public List<SysAuthoritiesVo> getSysAuthoritiesByUsername(String userName) 
			throws FrameworkBusinessException, FrameworkException;
	
	public List<Map<String,String>> getURLResourceMapping();
    
}