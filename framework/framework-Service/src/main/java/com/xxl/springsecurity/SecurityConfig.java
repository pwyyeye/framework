package com.xxl.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import com.xxl.hnust.service.ISysUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private ISysUserService sysUserService;
	
	@Override  
    public void configure(WebSecurity web) throws Exception {  
        // 设置不拦截规则  
        web.ignoring().antMatchers("/**/*.css", "/**/*.jpg", "/**/*.jpeg", 
        		"/**/*.gif", "/**/*.png", "/js/*.js");  
    }  
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*auth.inMemoryAuthentication()
			.withUser("user").password("password").roles("USER").and()
			.withUser("admin").password("password").roles("USER", "ADMIN");*/
		//auth.userDetailsService(new SecurityUserService(userService));
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
	@Bean
	public AuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider impl = new DaoAuthenticationProvider();
		impl.setUserDetailsService(new SecurityUserService(sysUserService, messageSource()));
		// 下面的配置是解决 UsernameNotFoundException 无法捕捉的问题
		impl.setHideUserNotFoundExceptions(false);
		// 配置MD5密码转换
		impl.setPasswordEncoder(new Md5PasswordEncoder());
		// 配置国际化，读取配置文件信息，显示给用户
		impl.setMessageSource(messageSource());
		return impl;
	}
	
	/**
     * Configure MessageSource to lookup any validation/error message in internationalized property files
     */
    @Bean
	public MessageSource messageSource() {
	    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	    messageSource.setBasename("messages_zh_CN");
	    return messageSource;
	}
    
    /**
     * Creates the {@link FilterSecurityInterceptor}
     *
     * @param metadataSource the {@link FilterInvocationSecurityMetadataSource} to use
     * @param authenticationManager the {@link AuthenticationManager} to use
     * @return the {@link FilterSecurityInterceptor}
     * @throws Exception
     */
    private FilterSecurityInterceptor createFilterSecurityInterceptor() throws Exception {
        FilterSecurityInterceptor securityInterceptor = new FilterSecurityInterceptor();
        //配置加载系统资源与权限列表类
        securityInterceptor.setSecurityMetadataSource(new MySecurityMetadataSource(sysUserService));
        //配置accessDecisionManager访问控制器
        securityInterceptor.setAccessDecisionManager(new MyAccessDecisionManager());
        //securityInterceptor.setAuthenticationManager(authenticationManager);
        securityInterceptor.afterPropertiesSet();
        return securityInterceptor;
    }
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
	    
		// 需要验证的配置
		http//.authorizeRequests()
			//.accessDecisionManager(new MyAccessDecisionManager()) //配置accessDecisionManager访问控制器
		
			//下面这块配置已经从数据库中读取 /login.jsp /sysUser/login 可不用配置
			//.antMatchers("/login.jsp").permitAll() //登录页面不需要验证
			//.antMatchers("/sysUser/login").permitAll() //登录链接不需要验证
			//.antMatchers("/user/**").access("hasRole('ROLE_USER')") //user路径下的链接需要验证
			//.antMatchers("/task/**").access("hasRole('ROLE_TASK')") //task路径下的链接需要验证
			//.antMatchers("/index.jsp").access("hasRole('ROLE_USER') or hasRole('ROLE_TASK')") //根路径下的链接需要验证
			
			.formLogin().loginPage("/securityUser/toLogin") //配置登录页面
			.successHandler(new MySuccessHandler(sysUserService)) //配置登录成功后的处理类
			.and().logout().logoutSuccessUrl("/") //配置退出后的页面
			.and().exceptionHandling().accessDeniedHandler(new MyAccessDeniedHandler()) //配置没有权限处理类
			.and().addFilter(createFilterSecurityInterceptor()) //配置加载系统资源与权限列表类
			; 
			//.and().csrf().disable(); //禁用CRSF
		
		// 配置remember me功能 (此功能没有效果)
		//http.rememberMe().tokenValiditySeconds(2419200).key("testKey");
		
		/*http.authorizeRequests()
			.antMatchers("/task/getJobList").hasAuthority("ROLE_SECURITY")
			.antMatchers(HttpMethod.POST, "/task").hasAuthority("ROLE_SECURITY")
			.anyRequest().permitAll();
		
		http.authorizeRequests()
	        .antMatchers("/", "/home").permitAll()
	        .antMatchers("/admin/**").access("hasRole('ADMIN')")
	        .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
	        .and().formLogin().loginPage("/login")
	        .usernameParameter("ssoId").passwordParameter("password")
	        .and().csrf()
	        .and().exceptionHandling().accessDeniedPage("/Access_Denied");*/
		
		// 使用默认登录页
		//http.formLogin();
		
		// 自定义登录页面  
        //http.formLogin().loginPage("/user/login");
		
		// 禁用CRSF
		//http.csrf().disable();
        
	}
	
}
