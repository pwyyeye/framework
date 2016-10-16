package com.xxl.hnust.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xxl.exception.UserBusinessException;
import com.xxl.exception.UserException;
import com.xxl.hnust.bo.SysUsers;
import com.xxl.hnust.dao.ISysUserDao;
import com.xxl.hnust.vo.SysAuthoritiesVo;
import common.dao.impl.BaseDAOImpl;

@Repository
public class SysUserDao extends BaseDAOImpl<SysUsers, java.lang.String> implements ISysUserDao {
	
	/** 
     * 先根据用户名获取到SysAuthorities集合 
     * @param username 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public List<SysAuthoritiesVo> getSysAuthoritiesByUsername(String userName)
    		throws UserBusinessException, UserException {
    	String sql = "SELECT AUTHORITY_ID, AUTHORITY_MARK, AUTHORITY_NAME, AUTHORITY_DESC, MESSAGE, ENABLE, ISSYS, MODULE_ID "+
    			"FROM SYS_AUTHORITIES WHERE AUTHORITY_ID IN( "+  
                "SELECT DISTINCT AUTHORITY_ID FROM SYS_ROLES_AUTHORITIES  S1 "+  
                "JOIN SYS_USERS_ROLES S2 ON S1.ROLE_ID = S2.ROLE_ID "+  
                "JOIN SYS_USERS S3 ON S3.USER_ID = S2.USER_ID AND S3.USER_NAME=?)";
    	Query query = getCurrentSession().createSQLQuery(sql);
		query.setParameter(0, userName);
		List<Object[]> list = query.list();
		List<SysAuthoritiesVo> authList = new ArrayList<SysAuthoritiesVo>();
		for(Object[] object : list) {
			SysAuthoritiesVo vo = new SysAuthoritiesVo();
			vo.setAuthorityId((Integer) object[0]);
			vo.setAuthorityMark((String) object[1]);
			vo.setAuthorityName((String) object[2]);
			vo.setAuthorityDesc((String) object[3]);
			vo.setMessage((String) object[4]);
			vo.setEnable((Integer) object[5]);
			vo.setIsSys((Integer) object[6]);
			vo.setModuleId((Integer) object[7]);
			authList.add(vo);
		}
        return authList;  
    }
    
    @SuppressWarnings("unchecked")
	public List<Map<String,String>> getURLResourceMapping() {  
	    String sql = "SELECT S3.RESOURCE_PATH,S2.AUTHORITY_MARK FROM SYS_AUTHORITIES_RESOURCES S1 "+  
	            "JOIN SYS_AUTHORITIES S2 ON S1.AUTHORITY_ID = S2.AUTHORITY_ID "+  
	            "JOIN SYS_RESOURCES S3 ON S1.RESOURCE_ID = S3.RESOURCE_ID WHERE S3.RESOURCE_TYPE='URL' ORDER BY S3.PRIORITY DESC";  
	    List<Map<String,String>> list = new ArrayList<Map<String,String>>();  
	    Query query = getCurrentSession().createSQLQuery(sql);  
	    List<Object[]> result = query.list();  
	    Iterator<Object[]> it = result.iterator();  
	    while(it.hasNext()) {  
	        Object[] o = it.next();  
	        Map<String,String> map = new HashMap<String,String>();  
	        map.put("resourcePath", (String)o[0]);  
	        map.put("authorityMark", (String)o[1]);  
	        list.add(map);  
	    }  
	    return list;  
	} 

}
