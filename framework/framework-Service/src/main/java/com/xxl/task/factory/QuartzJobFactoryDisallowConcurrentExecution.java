package com.xxl.task.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.xxl.task.util.TaskUtils;
import common.task.vo.ScheduleJobVo;

/** 
 *  
 * @Description: 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作 
 * @author snailxr 
 * @date 2014年4月24日 下午5:05:47 
 */  
@DisallowConcurrentExecution  
public class QuartzJobFactoryDisallowConcurrentExecution implements Job { 
	
	private static final Log logger = LogFactory.getLog(QuartzJobFactoryDisallowConcurrentExecution.class);
  
    @Override  
    public void execute(JobExecutionContext context) throws JobExecutionException {  
    	System.out.println("任务成功运行 QuartzJobFactoryDisallowConcurrentExecution");
    	ScheduleJobVo scheduleJobVo = (ScheduleJobVo) context.getMergedJobDataMap().get("scheduleJob");
        System.out.println("任务名称 QuartzJobFactoryDisallowConcurrentExecution = [" + scheduleJobVo.getJobName() + "]"); 
        TaskUtils.invokMethod(scheduleJobVo);
    }
    
}  