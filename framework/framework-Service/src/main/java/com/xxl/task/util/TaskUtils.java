package com.xxl.task.util;

import java.lang.reflect.Method;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xxl.facade.ITaskService;
import common.task.vo.ScheduleJobVo;
import common.utils.SemAppUtils;
import common.utils.SpringUtils;

public class TaskUtils {  
	public static Log logger = LogFactory.getLog(TaskUtils.class);  
  
    /** 
     * 通过反射调用scheduleJob中定义的方法 
     *  
     * @param scheduleJob 
     */  
    public static void invokMethod(ScheduleJobVo scheduleJobVo) {  
        Object object = null;  
        Class clazz = null;  
                //springId不为空先按springId查找bean  
        if (SemAppUtils.isNotEmpty(scheduleJobVo.getSpringId())) {  
            object = SpringUtils.getBean(scheduleJobVo.getSpringId());  
        } else if (SemAppUtils.isNotEmpty(scheduleJobVo.getBeanClass())) {  
            try {  
                clazz = Class.forName(scheduleJobVo.getBeanClass());  
                object = clazz.newInstance();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        if (object == null) {  
        	logger.error("任务名称 = [" + scheduleJobVo.getJobName() + "]---------------未启动成功，请检查是否配置正确！！！");  
            return;  
        }  
        clazz = object.getClass();  
        Method method = null;  
        try {  
            method = clazz.getDeclaredMethod(scheduleJobVo.getMethodName());  
        } catch (NoSuchMethodException e) {  
        	logger.error("任务名称 = [" + scheduleJobVo.getJobName() + "]---------------未启动成功，方法名设置错误！！！");  
        } catch (SecurityException e) {  
            e.printStackTrace();  
        }  
        if (method != null) {
        	ITaskService taskService = (ITaskService) SpringUtils.getBean("taskService");
        	scheduleJobVo.setRunTime(Calendar.getInstance());
            try {  
                method.invoke(object);
                
                scheduleJobVo.setResultDesc("执行成功！");
            } catch (Exception e) {
            	logger.error("执行定时任务发生异常！", e);
            	scheduleJobVo.setResultDesc("执行定时任务发生异常！结果：" + e.getCause().getMessage());
            }  
            
            try {
				taskService.updateJobRunResult(scheduleJobVo);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }  
    }  
    
}