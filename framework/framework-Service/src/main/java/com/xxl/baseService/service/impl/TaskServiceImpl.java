package com.xxl.baseService.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import com.xxl.baseService.bo.ScheduleJob;
import com.xxl.baseService.dao.ITaskDao;
import com.xxl.facade.ITaskService;
import com.xxl.task.factory.QuartzJobFactory;
import com.xxl.task.factory.QuartzJobFactoryDisallowConcurrentExecution;
import common.task.vo.ScheduleJobVo;
import common.utils.SemAppUtils;
import common.value.PageList;

@Service("taskService")
public class TaskServiceImpl implements ITaskService {

	public static Log logger = LogFactory.getLog(TaskServiceImpl.class);

	@Resource
	private ITaskDao taskDao;

//	@Resource
	private Scheduler scheduler; 

	@Override
	public void initTask() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ScheduleJob.class);
    	criteria.addOrder(Order.asc("id"));
    	PageList pageList = taskDao.findByCriteriaByPage(criteria, 0, 0);
		List<ScheduleJobVo> jobList = pageList.getItems();

		System.out.println("－－－－－－开始添加定时任务 TaskServiceImpl－－－－－－");
		for (ScheduleJobVo job : jobList) {
			try {
				addJob(job);
			} catch (SchedulerException e) {
				logger.error("添加定时任务失败！", e);
			}
		}
	}

	/**
	 * 添加任务
	 * 
	 * @param ScheduleJobVo
	 * @throws SchedulerException
	 */
	public void addJob(ScheduleJobVo vo) throws SchedulerException {
		if (vo == null || !ScheduleJob.STATUS_RUNNING.equals(vo.getJobStatus())) {
			return;
		}

		logger.debug(scheduler + "...............add...............");
		//获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
		TriggerKey triggerKey = TriggerKey.triggerKey(vo.getJobName(), vo.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		// 不存在，创建一个
		if (null == trigger) {
			Class clazz = ScheduleJob.CONCURRENT_IS.equals(vo
					.getIsConcurrent()) ? QuartzJobFactory.class
					: QuartzJobFactoryDisallowConcurrentExecution.class;

			JobDetail jobDetail = JobBuilder.newJob(clazz)
					.withIdentity(vo.getJobName(), vo.getJobGroup()).build();

			jobDetail.getJobDataMap().put("scheduleJob", vo);

			//表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
					.cronSchedule(vo.getCronExpression());

			//按新的cronExpression表达式构建一个新的trigger
			trigger = TriggerBuilder.newTrigger()
					.withIdentity(vo.getJobName(), vo.getJobGroup())
					.withSchedule(scheduleBuilder).build();

			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
	        //表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
					.cronSchedule(vo.getCronExpression());

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
					.withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}
	
	/**
	 * 获取定时任务列表（库表）
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageList findByPage(ScheduleJobVo vo, Integer firstResult, Integer fetchSize) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(ScheduleJob.class);
    	criteria.addOrder(Order.asc("id"));
    	PageList pageList = taskDao.findByCriteriaByPage(criteria, firstResult, fetchSize);
		return pageList;
	}
	
	/**
	 * 添加定时任务（库表、运行时）
	 * 
	 * @param vo
	 * @return
	 */
	public String addScheduleJob(ScheduleJobVo vo) throws Exception {
		ScheduleJob bo = new ScheduleJob();
    	SemAppUtils.beanCopy(vo, bo);
    	String id = taskDao.save(bo);
    	// 添加成功后，将任务加到正在执行的定时任务列表
    	addJob(vo); 
    	return id;
    }
	
	/**
	 * 更新定时任务执行结果（库表）
	 * 
	 * @param vo
	 * @return
	 */
	public void updateJobRunResult(ScheduleJobVo vo) throws Exception {
		ScheduleJob bo = (ScheduleJob) taskDao.findById(vo.getId(), ScheduleJob.class);
		bo.setRunTime(vo.getRunTime());
		bo.setResultDesc(vo.getResultDesc());
    	taskDao.saveOrUpdate(bo);
    }
	
	/**
	 * 获取所有计划中的任务列表（运行时）
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJobVo> getAllJob() throws SchedulerException {
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<ScheduleJobVo> jobList = new ArrayList<ScheduleJobVo>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler
					.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				ScheduleJobVo job = new ScheduleJobVo();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setDescription("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler
						.getTriggerState(trigger.getKey());
				job.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		return jobList;
	}
	
	/**
	 * 所有正在运行的job（运行时）
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJobVo> getRunningJob() throws SchedulerException {
		List<JobExecutionContext> executingJobs = scheduler
				.getCurrentlyExecutingJobs();
		List<ScheduleJobVo> jobList = new ArrayList<ScheduleJobVo>(
				executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			ScheduleJobVo job = new ScheduleJobVo();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			job.setDescription("触发器:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler
					.getTriggerState(trigger.getKey());
			job.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}
	
	/**
	 * 暂停一个job
	 * 
	 * @param ScheduleJobVo
	 * @throws SchedulerException
	 */
	@Transactional
	public void pauseJob(ScheduleJobVo vo) throws Exception {
		JobKey jobKey = JobKey.jobKey(vo.getJobName(), vo.getJobGroup());
		scheduler.pauseJob(jobKey);
		
		ScheduleJob bo = (ScheduleJob) taskDao.findById(vo.getId(), ScheduleJob.class);
		bo.setJobStatus("0");
		bo.setUpdateTime(Calendar.getInstance());
		taskDao.saveOrUpdate(bo);
		
	}
	
	/**
	 * 恢复一个job
	 * 
	 * @param ScheduleJobVo
	 * @throws SchedulerException
	 */
	public void resumeJob(ScheduleJobVo vo) throws Exception {
		JobKey jobKey = JobKey.jobKey(vo.getJobName(), vo.getJobGroup());
		scheduler.resumeJob(jobKey);
		
		ScheduleJob bo = (ScheduleJob) taskDao.findById(vo.getId(), ScheduleJob.class);
		bo.setJobStatus("1");
		bo.setUpdateTime(Calendar.getInstance());
		taskDao.saveOrUpdate(bo);
		
	}
	
	/**
	 * 删除一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void deleteJob(ScheduleJobVo vo) throws Exception {
		JobKey jobKey = JobKey.jobKey(vo.getJobName(), vo.getJobGroup());
		scheduler.deleteJob(jobKey);
		
		taskDao.deleteById(vo.getId(), ScheduleJob.class);
	}
	
	/**
	 * 立即执行job
	 * 
	 * @param ScheduleJobVo
	 * @throws SchedulerException
	 */
	public void runAJobNow(ScheduleJobVo vo) throws Exception {
		JobKey jobKey = JobKey.jobKey(vo.getJobName(), vo.getJobGroup());
		scheduler.triggerJob(jobKey);
	}
	
	/**
	 * 更新job时间表达式
	 * 
	 * @param ScheduleJobVo
	 * @throws SchedulerException
	 */
	public void updateJobCron(ScheduleJobVo vo) throws Exception {
		TriggerKey triggerKey = TriggerKey.triggerKey(vo.getJobName(), vo.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
				.cronSchedule(vo.getCronExpression());

		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
				.withSchedule(scheduleBuilder).build();

		scheduler.rescheduleJob(triggerKey, trigger);
		
		ScheduleJob bo = (ScheduleJob) taskDao.findById(vo.getId(), ScheduleJob.class);
		bo.setCronExpression(vo.getCronExpression());
		bo.setUpdateTime(Calendar.getInstance());
		taskDao.saveOrUpdate(bo);
	}

}