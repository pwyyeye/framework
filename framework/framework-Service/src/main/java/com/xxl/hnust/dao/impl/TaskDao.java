package com.xxl.hnust.dao.impl;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDAOImpl;
import com.xxl.hnust.bo.ScheduleJob;
import com.xxl.hnust.dao.ITaskDao;

@Repository
public class TaskDao extends BaseDAOImpl<ScheduleJob, java.lang.String> implements ITaskDao {

}
