package common.bussiness;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.xxl.facade.AdminRemote;
import com.xxl.facade.CommonRemote;

import common.filter.SemLogonInterface;
import common.os.vo.UsersVO;
import common.utils.SemAppConstants;
import common.utils.SemAppUtils;
import common.value.ItModuleVO;
import common.web.bean.SessionUserBean;
import common.web.utils.SemWebAppConstants;

public class LogonImpl implements SemLogonInterface{
	public Log logger = LogFactory.getLog(this.getClass());

	public static Log sysLogger = LogFactory.getLog("sys");
	protected InitialContext context = null;
	@Autowired
	private AdminRemote adminRemote;
	
	@Autowired
	private CommonRemote commonRemote;
	public boolean performLogon(UsersVO user, HttpServletRequest request,Integer roleID) {
		logger.debug("start perform logon...");
		try {
			String token=commonRemote.getUserToken((Integer)user.getId());
			String moduleStr=(String)request.getSession().getAttribute(SemWebAppConstants.SESSION_MODULE_ID);
			Integer moduleID=null;
			try{
				moduleID=new Integer(moduleStr);			
			}catch(Exception ee){}
			if(moduleID==null){
				moduleID=new Integer(SemAppConstants.COMMON_MODULE_ID);
				logger.debug("logon default module");
				request.getSession().setAttribute(SemWebAppConstants.SESSION_MODULE_ID, "" + moduleID);
			}
			SessionUserBean userBean = adminRemote.getSessionUserBean(user,
					moduleID, "",roleID,token);
			ItModuleVO module = adminRemote.getSystemByID(moduleID);
			request.setAttribute(SemWebAppConstants.SESSION_MODULE, module);
			if (userBean == null) {
				return false;// ????????????
			}
			request.setAttribute(SemWebAppConstants.USER_KEY, userBean);

			
			CommonLogger log = new CommonLogger((Integer)user.getId(), "01", "???"
					+ userBean.getRole().getName() + "????????????", "moduleID="
					+ moduleID, "");
			logger.info(log);
		} catch (Exception e) {
			logger.error("????????????", e);
			return false;
		} 
		return true;
	}


	public boolean performLogon(UsersVO user, HttpServletRequest request) {
		return performLogon(user,request,null);
	}

	
}
