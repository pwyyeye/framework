package com.xxl.task.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.xxl.hnust.bo.ScheduleJob;

/**
 * 计划任务测试数据，后面改为从数据库中读取
 * @author karys
 *
 */
// 测试时用，正式可以删除
public class DataWorkContext {

	// 可以去数据库中取
	/** 计划任务map */
	private static Map<String, ScheduleJob> jobMap = new HashMap<String, ScheduleJob>();

	static {
		for (int i = 0; i < 2; i++) {
			ScheduleJob job = new ScheduleJob();
			job.setJobId((long) (10001 + i));
			job.setJobName("data_import" + i);
			job.setJobGroup("dataWork");
			job.setJobStatus("1");
			job.setCronExpression("0/5 * * * * ?");
			job.setDescription("数据导入任务");
			job.setIsConcurrent("1");
			//job.setSpringId("userService");
			job.setBeanClass("com.cn.hnust.service.impl.UserServiceImpl");
			job.setMethodName("testTaskJob"+i);
			addJob(job);
		}
	}

	/**
	 * 添加任务
	 * 
	 * @param scheduleJob
	 */
	public static void addJob(ScheduleJob scheduleJob) {
		jobMap.put(scheduleJob.getJobGroup() + "_" + scheduleJob.getJobName(),
				scheduleJob);
	}

	/**
	 * 取得所有任务
	 */
	@SuppressWarnings("rawtypes")
	public static List<ScheduleJob> getAllJob() {
		List<ScheduleJob> list = new ArrayList<ScheduleJob>();

		Iterator it = jobMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			list.add((ScheduleJob) entry.getValue());
		}

		return list;
	}

}
