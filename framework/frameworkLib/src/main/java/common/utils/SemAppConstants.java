package common.utils;



public class SemAppConstants {

	public static final int COMMON_MODULE_ID=6;
	public static final int ALPHA_MODULE_ID=99;
	
	public final static int STATUS_OPEN=1;
	public final static int TYPE_ASYNCH=1;
	
	public final static int STATUS_TASK_INIT=0;
	public final static int STATUS_TASK_SUCCESS=1;
	public final static int STATUS_TASK_FAILER=2;
	public final static int STATUS_TASK_REINIT=3;
	
	
	//与background任务相关
	public final static int STATUS_JMS_TASK_INIT=0;
	public final static int STATUS_JMS_SUCCESS=1;
	public final static int STATUS_JMS_FAILER=-1;
	
	//与定时任务相关
	public final static int TASK_TYPE_DAY=0;//每天
	public final static int TASK_TYPE_HOUR=1;//每小时
	public final static int TASK_TYPE_WEEK=2;//每周
	public final static int TASK_TYPE_MONTH=3;//每天
	public final static int TASK_RUN_RESULT_SUCCESS=1;//执行成功
	public final static int TASK_RUN_RESULT_FAILER=-1;//执行失败
	public final static int TASK_RUN_RESULT_INIT=0;//运行中
	
    //系统事件的ID号
	public final static int MESSAGE_SYSTEM_ID=1;//系统事件ID号。
	
	//后台异常进程处理的sub quek号码
	public final static int MAIL_QUEUE=1;//后台发邮件的que号
	public final static int QUEUE_REPORT_SCHEDULE=2;//后台执行报表调度器的que号
	public final static int QUEUE_TASK_SCHEDULE=3;//后台招待任务调度器的que号
	public final static int QUEUE_PUSH_MESSAGE=4;
	
	public static final String PRIMARY_CONTEXT_ID = "businessBeanFactory";
	
	//报表调度器，自动发送报表的类型重复类型,0表示单次,1表示每小时,2表示每天,3表示每周,4表示每月,5表示每年
	public static final int REPORT_SCHEDULE_ONCE = 0;
	public static final int REPORT_SCHEDULE_HOUR = 1;
	public static final int REPORT_SCHEDULE_DAY = 2;
	public static final int REPORT_SCHEDULE_WEEK = 3;
	public static final int REPORT_SCHEDULE_MONTH = 4;
	public static final int REPORT_SCHEDULE_YEAR = 5;
}
