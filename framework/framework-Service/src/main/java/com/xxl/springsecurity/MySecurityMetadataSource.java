package com.xxl.springsecurity;

import java.util.ArrayList;  
import java.util.Collection;  
import java.util.HashSet;  
import java.util.Iterator;  
import java.util.LinkedHashMap;
import java.util.List;  
import java.util.Map;  
import java.util.TreeMap;  
import java.util.concurrent.ConcurrentHashMap;  

import javax.servlet.http.HttpServletRequest;  

import org.apache.commons.lang.StringUtils;  
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;  
import org.springframework.security.access.ConfigAttribute;  
import org.springframework.security.access.SecurityConfig;  
import org.springframework.security.web.FilterInvocation;  
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;  
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;  
import org.springframework.security.web.util.matcher.RequestMatcher;  

import com.xxl.hnust.service.ISysUserService;

/**
 * 加载系统资源与权限列表
 * @author karys
 *
 */
public class MySecurityMetadataSource  implements FilterInvocationSecurityMetadataSource,InitializingBean {  
  
	public static Log logger = LogFactory.getLog(MySecurityMetadataSource.class);
      
    private static final String AUTH_NO_ROLE =" __AUTH_NO_ROLE__";  
      
    private ISysUserService sysUserService;  
      
    public MySecurityMetadataSource(ISysUserService sysUserService) {  
        this.sysUserService = sysUserService;  
    }  
  
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;  
  
    public Collection<ConfigAttribute> getAllConfigAttributes() {  
        return null;  
    }  
  
    public boolean supports(Class<?> clazz) {  
        return true;  
    }  
    
    private Map<String,String> loadResuorce(){  
        Map<String,String> map = new LinkedHashMap<String,String>();  
          
        List<Map<String,String>> list = sysUserService.getURLResourceMapping();  
        Iterator<Map<String,String>> it = list.iterator();  
        while(it.hasNext()){  
            Map<String,String> rs = it.next();  
            String resourcePath = rs.get("resourcePath");  
            String authorityMark = rs.get("authorityMark");  
              
            if(map.containsKey(resourcePath)){  
                String mark = map.get(resourcePath);  
                map.put(resourcePath, mark+","+authorityMark);  
            }else{  
                map.put(resourcePath, authorityMark);  
            }  
        }  
        return map;  
    }
    
    protected Map<String, Collection<ConfigAttribute>> bindRequestMap(){  
        Map<String, Collection<ConfigAttribute>> map =   
                new LinkedHashMap<String, Collection<ConfigAttribute>>();  
          
        Map<String,String> resMap = this.loadResuorce(); 
        for(Map.Entry<String,String> entry:resMap.entrySet()){
            String key = entry.getKey();  
            Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();  
            atts = SecurityConfig.createListFromCommaDelimitedString(entry.getValue()); 
            map.put(key, atts);  
        }  
          
        return map;  
    }  
    
    private void loadResourceDefine() {  
        if(resourceMap == null) {  
            resourceMap = new ConcurrentHashMap<String, Collection<ConfigAttribute>>();  
        }else{  
            resourceMap.clear();  
        }  
          
        resourceMap = bindRequestMap();
    }
    
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {  
    	
    	try {
			afterPropertiesSet();
			System.out.println("===MySecurityMetadataSource===");
			System.out.println("===resourceMap==="+resourceMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
          
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();    
   
        TreeMap<String, Collection<ConfigAttribute>> attrMap = new TreeMap<String, Collection<ConfigAttribute>>(resourceMap);  
          
        Iterator<String> ite = attrMap.keySet().iterator();    
          
        RequestMatcher urlMatcher = null;    
  
        Collection<ConfigAttribute> attrSet = new HashSet<ConfigAttribute>();  
        //match all of /admin/**  a/b/**  
        while (ite.hasNext()) {    
              
            String resURL = ite.next();    
            urlMatcher = new AntPathRequestMatcher(resURL);  
  
            if (urlMatcher.matches(request)||StringUtils.equals(request.getRequestURI(),resURL)) {    
                attrSet.addAll(attrMap.get(resURL));    
            }    
        }    
  
        if(!attrSet.isEmpty()){  
            return attrSet;  
        }  
        return null;  
    }  
   
    @Override  
    public void afterPropertiesSet() throws Exception {  
        loadResourceDefine() ;  
    }  
      
}