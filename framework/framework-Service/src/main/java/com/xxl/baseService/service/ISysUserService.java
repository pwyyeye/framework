package com.xxl.baseService.service;  
  
import java.util.Collection;
import java.util.List;
import java.util.Map;

//import org.springframework.security.core.GrantedAuthority;


import com.xxl.baseService.vo.SysUsersVo;
import com.xxl.exception.FrameworkBusinessException;
import com.xxl.exception.FrameworkException;
  
public interface ISysUserService {  
    
	public SysUsersVo getByUserName(String userName) 
			throws FrameworkBusinessException, FrameworkException;  
//    
//    public Collection<GrantedAuthority> loadUserAuthorities(String userName) 
//			throws UserBusinessException, UserException;  
    
    public void saveSysUsers(SysUsersVo vo) throws FrameworkBusinessException, FrameworkException;
    
    public List<Map<String,String>> getURLResourceMapping();
    
}