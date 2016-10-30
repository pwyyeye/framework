package com.xxl.baseService.service;  
  
import java.util.List;
import java.util.Map;

import com.xxl.baseService.vo.SysUsersVo;
import common.exception.BaseBusinessException;
//import org.springframework.security.core.GrantedAuthority;
import common.exception.BaseException;
  
public interface ISysUserService {  
    
	public SysUsersVo getByUserName(String userName) 
			throws BaseBusinessException, BaseException;  
//    
//    public Collection<GrantedAuthority> loadUserAuthorities(String userName) 
//			throws UserBusinessException, UserException;  
    
    public void saveSysUsers(SysUsersVo vo) throws BaseBusinessException, BaseException;
    
    public List<Map<String,String>> getURLResourceMapping();
    
}