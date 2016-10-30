package com.xxl.baseService.bo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import common.businessObject.BaseBusinessObject;
import common.task.vo.ScheduleJobVo;
import common.utils.SemAppUtils;

/**
 * 
 * @Description: 计划任务信息
 * @author snailxr
 * @date 2014年4月24日 下午10:49:43
 */
@Entity
@Table(name = "t_schedule_job")
public class ScheduleJob extends BaseBusinessObject {

	private static final long serialVersionUID = 4845111383294255204L;
	
	public static final String STATUS_RUNNING = "1"; //启用
	public static final String STATUS_NOT_RUNNING = "0"; //禁用
	public static final String CONCURRENT_IS = "1"; //无状态
	public static final String CONCURRENT_NOT = "0"; //有状态
	
	private Integer id;
	
	/** 任务id **/
	private Long jobId;

	private Calendar createTime;

	private Calendar updateTime;
	/**
	 * 任务名称
	 */
	private String jobName;
	/**
	 * 任务分组
	 */
	private String jobGroup;
	/**
	 * 任务状态 是否启动任务 0禁用 1启用 2删除
	 */
	private String jobStatus;
	/**
	 * cron表达式(任务运行时间表达式)
	 */
	private String cronExpression;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 任务执行时调用哪个类的方法 包名+类名
	 */
	private String beanClass;
	/**
	 * 任务是否有状态
	 */
	private String isConcurrent;
	/**
	 * spring bean
	 */
	private String springId;
	/**
	 * 任务调用的方法名
	 */
	private String methodName;
	
	/**
	 * 执行时间
	 */
	private Calendar runTime;
	/**
	 * 执行结果
	 */
	private String resultDesc;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, length = 11)
    public Integer getId() {
        return id;
    }
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "job_id", unique = true, nullable = false, length = 11)
	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", nullable = true)
	public Calendar getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Calendar updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "job_name", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Column(name = "job_group", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	@Column(name = "job_status", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	@Column(name = "cron_expression", unique = false, nullable = false, insertable = true, updatable = true, length = 255)
	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	@Column(name = "description", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "bean_class", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public String getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}

	@Column(name = "is_concurrent", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public String getIsConcurrent() {
		return isConcurrent;
	}

	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	@Column(name = "spring_id", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getSpringId() {
		return springId;
	}

	public void setSpringId(String springId) {
		this.springId = springId;
	}

	@Column(name = "method_name", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "run_time", nullable = true)
	public Calendar getRunTime() {
		return runTime;
	}

	public void setRunTime(Calendar runTime) {
		this.runTime = runTime;
	}

	@Column(name = "result_desc", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public Object toVO() {
		ScheduleJobVo vo = new ScheduleJobVo();
		SemAppUtils.BO2VO(this, vo);
		return vo;
	}

}