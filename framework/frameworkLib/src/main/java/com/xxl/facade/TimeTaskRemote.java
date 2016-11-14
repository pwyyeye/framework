package com.xxl.facade;  

import java.util.List;

import org.quartz.SchedulerException;

import common.task.vo.ScheduleJobVo;
import common.value.PageList;
  
public interface TimeTaskRemote {  
    
    public void initTask();
    
    public PageList findByPage(ScheduleJobVo vo, Integer firstResult, Integer fetchSize) throws Exception;
    
    public String addScheduleJob(ScheduleJobVo vo) throws Exception;
    
    public void updateJobRunResult(ScheduleJobVo vo) throws Exception;
    
    public List<ScheduleJobVo> getAllJob() throws SchedulerException;
    
    public List<ScheduleJobVo> getRunningJob() throws SchedulerException;
    
    public void pauseJob(ScheduleJobVo vo) throws Exception;
    
    public void resumeJob(ScheduleJobVo vo) throws Exception;
    
    public void deleteJob(ScheduleJobVo vo) throws Exception;
    
    public void runAJobNow(ScheduleJobVo vo) throws Exception;
    
    public void updateJobCron(ScheduleJobVo vo) throws Exception;
    
}