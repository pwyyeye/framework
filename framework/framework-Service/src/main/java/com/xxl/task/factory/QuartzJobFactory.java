package com.xxl.task.factory;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.xxl.task.util.TaskUtils;
import common.task.vo.ScheduleJobVo;

/** 
 *  
 * @Description: 计划任务执行处 无状态 
 * @author snailxr 
 * @date 2014年4月24日 下午5:05:47 
 */  
public class QuartzJobFactory implements Job {  

	private static final Log logger = LogFactory.getLog(QuartzJobFactory.class);
  
    @Override  
    public void execute(JobExecutionContext context) throws JobExecutionException {  
        System.out.println("任务成功运行 QuartzJobFactory "+ new Date());
        ScheduleJobVo scheduleJobVo = (ScheduleJobVo) context.getMergedJobDataMap().get("scheduleJob");
        System.out.println("任务名称 QuartzJobFactory = [" + scheduleJobVo.getJobName() + "]");
        TaskUtils.invokMethod(scheduleJobVo);
    }
    
}  