package com.xxl.baseService.dao.impl;

import org.springframework.stereotype.Repository;

import com.xxl.baseService.dao.ITaskDao;
import com.xxl.task.bo.ScheduleJob;

import common.dao.impl.BaseDAOImpl;

@Repository("taskDao")
public class TaskDaoImpl extends BaseDAOImpl<ScheduleJob, java.lang.String> implements ITaskDao {

}
