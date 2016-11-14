package common.task;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xxl.facade.TimeTaskRemote;

import common.utils.SpringUtils;

/**
 * 定时任务初始化
 * @author karys
 * 2016-09-25
 *
 */
public class TaskInit extends HttpServlet {

	private static final long serialVersionUID = 2992500555742955500L;

	private static final Log logger = LogFactory.getLog(TaskInit.class);
	
	@Autowired
	private TimeTaskRemote timeTaskRemote;

	public void init(ServletConfig config) throws ServletException {
		
		logger.info("－－－－－－开始添加定时任务 TaskInit－－－－－－");
		super.init(config);  
//	    timeTaskRemote = (TimeTaskRemote) SpringUtils.servletGetBean(this.getServletContext(), "timeTaskRemote");
		timeTaskRemote.initTask();
		
	}

}
