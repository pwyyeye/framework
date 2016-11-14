package com.xxl.baseService.service.impl;  
  
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.xxl.baseService.bo.SysUsers;
import com.xxl.baseService.dao.ISysUserDao;
import com.xxl.baseService.service.ISysUserService;
import com.xxl.baseService.vo.SysUsersVo;
import com.xxl.exception.FrameworkBusinessException;
import com.xxl.exception.FrameworkException;
import common.utils.SemAppUtils;
import common.value.PageList;
  
@Service("sysUserService")  
public class SysUserServiceImpl implements ISysUserService {
	
	public static Log logger = LogFactory.getLog(SysUserServiceImpl.class);
	
    @Resource
    private ISysUserDao sysUserDao;
    
    public SysUsersVo getByUserName(String userName) 
			throws FrameworkBusinessException, FrameworkException {
    	DetachedCriteria criteria = DetachedCriteria.forClass(SysUsers.class);
    	criteria.add(Expression.eq("userName", userName));
    	PageList pageList = sysUserDao.findByCriteriaByPage(criteria, 0, 0);
    	SysUsersVo sysUsers = null;
    	if(pageList!=null && pageList.getItems().size()>0) {
    		sysUsers = (SysUsersVo) pageList.getItems().get(0);
    	}
    	return sysUsers;
    }

//	@Override
//	public Collection<GrantedAuthority> loadUserAuthorities(String userName) 
//			throws UserBusinessException, UserException {
//		List<SysAuthoritiesVo> list = sysUserDao.getSysAuthoritiesByUsername(userName);  
//        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();  
//        for (SysAuthoritiesVo authority : list) {  
//            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthorityMark());  
//            auths.add(grantedAuthority);  
//        }  
//        return auths;  
//	}
    
	public void saveSysUsers(SysUsersVo vo) throws FrameworkBusinessException, FrameworkException {
		SysUsers bo = (SysUsers) sysUserDao.findById(vo.getUserId(), SysUsers.class);
    	SemAppUtils.beanCopy(vo, bo);
    	sysUserDao.saveOrUpdate(bo);
	}
	
	public List<Map<String,String>> getURLResourceMapping() {
		return sysUserDao.getURLResourceMapping();
	}
	
}