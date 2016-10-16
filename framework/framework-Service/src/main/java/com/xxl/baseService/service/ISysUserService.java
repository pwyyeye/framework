package com.xxl.baseService.service;  
  
import java.util.Collection;
import java.util.List;
import java.util.Map;

//import org.springframework.security.core.GrantedAuthority;


import com.xxl.baseService.vo.SysUsersVo;
import com.xxl.exception.UserBusinessException;
import com.xxl.exception.UserException;
  
public interface ISysUserService {  
    
	public SysUsersVo getByUserName(String userName) 
			throws UserBusinessException, UserException;  
//    
//    public Collection<GrantedAuthority> loadUserAuthorities(String userName) 
//			throws UserBusinessException, UserException;  
    
    public void saveSysUsers(SysUsersVo vo) throws UserBusinessException, UserException;
    
    public List<Map<String,String>> getURLResourceMapping();
    
}