package com.xxl.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xxl.facade.ITaskService;
import common.controller.BaseController;
import common.task.vo.ScheduleJobVo;
import common.utils.SemAppUtils;
import common.value.PageList;
import common.vo.BaseResponseVO;

@Controller
@RequestMapping("/task")
public class TaskController extends BaseController {

	public static Log logger = LogFactory.getLog(UserController.class);

	@Resource
	private ITaskService taskService;
	
	@InitBinder("scheduleJobVo")
	// 传入对象参数时，需要提前配置这项
	public void initBinder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("scheduleJobVo.");
	}
	
	/**
	 * 获取定时任务列表（库表）
	 * 
	 */
	@RequestMapping(value = "/getJobList")
	@ResponseBody
	public void getJobList(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "1") Integer page, // 第几页
			@RequestParam(required = false, defaultValue = "10") Integer rows, // 页数大小
			@RequestParam(required = false, defaultValue = "") String paramName,
			@RequestParam(required = false, defaultValue = "") String createTime) {
		try {
			response.setContentType("text/json;charset=UTF-8");
			PageList pageList = this.taskService.findByPage(new ScheduleJobVo(),
					(page - 1) * rows, rows);
			/*
			 * Map<String,Object> map = new HashMap<String,Object>();
			 * map.put("rows", pageList.getItems()); 
			 * map.put("total", pageList.getResults());
			 */
			List b1 = new ArrayList();
			//b1.add("com.cn.hnust.vo.ScheduleJobVo#password");
			//b1.add("com.cn.hnust.vo.ScheduleJobVo#age");
			String json = SemAppUtils.getJsonFromBean(new BaseResponseVO("1",
					"获取定时任务列表成功", pageList), b1, "yyyy-MM-dd HH:mm:ss");
			response.getWriter().write(json);
		} catch (Exception e) {
			logger.error("获取定时任务列表发生异常!", e);
			this.handleException(new IOException("获取定时任务列表发生异常!"), request, response);
		}
		
	}
	
	/**
	 * 添加定时任务（库表、运行时）
	 * 
	 * @param scheduleJobVo
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/addScheduleJob")
	@ResponseBody
	// 传ScheduleJobVo参数时的用法
	/*
	 * http://127.0.0.1:8081/BaseProject/task/addScheduleJob?scheduleJobVo.jobId=10001&&scheduleJobVo.jobName=data_import0&&scheduleJobVo.jobGroup=dataWork&&scheduleJobVo.jobStatus=1&&scheduleJobVo.cronExpression=0/5 * * * * ?&&scheduleJobVo.description=数据导入任务&&scheduleJobVo.isConcurrent=1&&scheduleJobVo.beanClass=com.cn.hnust.service.impl.UserServiceImpl&&scheduleJobVo.methodName=testTaskJob0
	 */
	public void addScheduleJob(@ModelAttribute ScheduleJobVo scheduleJobVo, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setContentType("text/json;charset=UTF-8");
			scheduleJobVo.setCreateTime(Calendar.getInstance());
			String id = this.taskService.addScheduleJob(scheduleJobVo);
			String json = SemAppUtils.getJsonFromBean(new BaseResponseVO("1", "添加定时任务成功", id), false);
			response.getWriter().write(json);
		} catch (Exception e) {
			logger.error("添加定时任务发生异常!", e);
			this.handleException(new IOException("添加定时任务发生异常!"), request, response);
		}
	}
	
	/**
	 * 获取所有计划中的任务列表（运行时）
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	@RequestMapping(value = "/getAllJob")
	@ResponseBody
	public List<ScheduleJobVo> getAllJob() throws SchedulerException {
		List<ScheduleJobVo> jobList = taskService.getAllJob();
		return jobList;
	}

	/**
	 * 所有正在运行的job（运行时）
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	@RequestMapping(value = "/getRunningJob")
	@ResponseBody
	public List<ScheduleJobVo> getRunningJob() throws SchedulerException {
		List<ScheduleJobVo> jobList = taskService.getRunningJob();
		return jobList;
	}

	/**
	 * 暂停一个job
	 * 
	 * @param ScheduleJobVo
	 * @throws SchedulerException
	 */
	@RequestMapping(value = "/pauseJob")
	@ResponseBody
	public void pauseJob(@ModelAttribute ScheduleJobVo scheduleJobVo, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("scheduleJobVo.getId()=="+scheduleJobVo.getId()
				+ " scheduleJobVo.getJobName()=="+scheduleJobVo.getJobName()
				+ " scheduleJobVo.getJobGroup()="+scheduleJobVo.getJobGroup());
		try {
			response.setContentType("text/json;charset=UTF-8");
			taskService.pauseJob(scheduleJobVo);
			String json = SemAppUtils.getJsonFromBean(new BaseResponseVO("1", "暂停定时任务成功", ""), false);
			response.getWriter().write(json);
		} catch (Exception e) {
			logger.error("暂停定时任务发生异常!", e);
			this.handleException(new IOException("暂停定时任务发生异常!"), request, response);
		}
		
	}

	/**
	 * 恢复一个job
	 * 
	 * @param ScheduleJobVo
	 * @throws SchedulerException
	 */
	@RequestMapping(value = "/resumeJob")
	@ResponseBody
	public void resumeJob(@ModelAttribute ScheduleJobVo scheduleJobVo, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setContentType("text/json;charset=UTF-8");
			taskService.resumeJob(scheduleJobVo);
			String json = SemAppUtils.getJsonFromBean(new BaseResponseVO("1", "恢复定时任务成功", ""), false);
			response.getWriter().write(json);
		} catch (Exception e) {
			logger.error("恢复定时任务发生异常!", e);
			this.handleException(new IOException("恢复定时任务发生异常!"), request, response);
		}
	}

	/**
	 * 删除一个job
	 * 
	 * @param ScheduleJobVo
	 * @throws SchedulerException
	 */
	@RequestMapping(value = "/deleteJob")
	@ResponseBody
	public void deleteJob(@ModelAttribute ScheduleJobVo scheduleJobVo, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setContentType("text/json;charset=UTF-8");
			taskService.deleteJob(scheduleJobVo);
			String json = SemAppUtils.getJsonFromBean(new BaseResponseVO("1", "删除定时任务成功", ""), false);
			response.getWriter().write(json);
		} catch (Exception e) {
			logger.error("删除定时任务发生异常!", e);
			this.handleException(new IOException("删除定时任务发生异常!"), request, response);
		}
	}

	/**
	 * 立即执行job
	 * 
	 * @param ScheduleJobVo
	 * @throws SchedulerException
	 */
	@RequestMapping(value = "/runAJobNow")
	@ResponseBody
	public void runAJobNow(@ModelAttribute ScheduleJobVo scheduleJobVo, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setContentType("text/json;charset=UTF-8");
			taskService.runAJobNow(scheduleJobVo);
			String json = SemAppUtils.getJsonFromBean(new BaseResponseVO("1", "立即执行定时任务成功", ""), false);
			response.getWriter().write(json);
		} catch (Exception e) {
			logger.error("立即执行定时任务发生异常!", e);
			this.handleException(new IOException("立即执行定时任务发生异常!"), request, response);
		}
	}

	/**
	 * 更新job时间表达式
	 * 
	 * @param ScheduleJobVo
	 * @throws SchedulerException
	 */
	@RequestMapping(value = "/updateJobCron")
	@ResponseBody
	// 调用方法
	// http://127.0.0.1:8081/BaseProject/task/updateJobCron?scheduleJobVo.jobName=data_import1&&scheduleJobVo.jobGroup=dataWork&&scheduleJobVo.cronExpression=0/5 * * * * ?
	public void updateJobCron(@ModelAttribute ScheduleJobVo scheduleJobVo, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setContentType("text/json;charset=UTF-8");
			taskService.updateJobCron(scheduleJobVo);
			String json = SemAppUtils.getJsonFromBean(new BaseResponseVO("1", "更新job时间表达式成功", ""), false);
			response.getWriter().write(json);
		} catch (Exception e) {
			logger.error("更新job时间表达式发生异常!", e);
			this.handleException(new IOException("更新job时间表达式发生异常!"), request, response);
		}
	}

}