package com.xxl.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// 测试时用，正式可以删除
public class DataConversionTask {
	/** 日志对象 */
    private static final Log logger = LogFactory.getLog(DataConversionTask.class);

    public void run() {

        if (logger.isInfoEnabled()) {
        	logger.info("数据转换任务线程开始执行");
        }
        
    }
}
