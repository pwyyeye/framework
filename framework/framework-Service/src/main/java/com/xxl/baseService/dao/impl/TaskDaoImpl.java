package com.xxl.baseService.dao.impl;

import org.springframework.stereotype.Repository;

import com.xxl.baseService.bo.ScheduleJob;
import com.xxl.baseService.dao.ITaskDao;

import common.dao.impl.BaseDAOImpl;

@Repository
public class TaskDaoImpl extends BaseDAOImpl<ScheduleJob, java.lang.String> implements ITaskDao {

}
