# Host: 127.0.0.1  (Version: 5.6.26)
# Date: 2016-10-12 21:29:39
# Generator: MySQL-Front 5.3  (Build 4.214)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "commonlog"
#

DROP TABLE IF EXISTS `commonlog`;
CREATE TABLE `commonlog` (
  `dl_id` int(11) NOT NULL AUTO_INCREMENT,
  `DL_USER` varchar(64) DEFAULT NULL,
  `DL_DATE` datetime DEFAULT NULL,
  `LOG_ID` decimal(8,0) DEFAULT NULL,
  `DL_CONTENT` varchar(1024) DEFAULT NULL,
  `DL_REMARK` varchar(1024) DEFAULT NULL,
  `DL_IP` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`dl_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='log table';

#
# Structure for table "commonlogreport"
#

DROP TABLE IF EXISTS `commonlogreport`;
CREATE TABLE `commonlogreport` (
  `DL_ID` decimal(8,0) DEFAULT NULL,
  `DL_USER` varchar(64) DEFAULT NULL,
  `DL_DATE` datetime DEFAULT NULL,
  `LOG_ID` decimal(8,0) DEFAULT NULL,
  `DL_CONTENT` varchar(1024) DEFAULT NULL,
  `DL_REMARK` varchar(1024) DEFAULT NULL,
  `DL_IP` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "doc_cabinet"
#

DROP TABLE IF EXISTS `doc_cabinet`;
CREATE TABLE `doc_cabinet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `owner` decimal(10,0) DEFAULT NULL,
  `createuser` decimal(10,0) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `ownerdept` varchar(10) DEFAULT NULL,
  `message` decimal(10,0) DEFAULT NULL,
  `root` varchar(255) DEFAULT NULL,
  `status` decimal(1,0) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "eia_log"
#

DROP TABLE IF EXISTS `eia_log`;
CREATE TABLE `eia_log` (
  `dl_id` int(11) NOT NULL AUTO_INCREMENT,
  `DL_USER` varchar(10) DEFAULT NULL,
  `DL_DATE` datetime DEFAULT NULL,
  `DL_CONTENT` varchar(256) DEFAULT NULL,
  `DL_REMARK` varchar(126) DEFAULT NULL,
  `DL_IP` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`dl_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='//log';

#
# Structure for table "eia_online"
#

DROP TABLE IF EXISTS `eia_online`;
CREATE TABLE `eia_online` (
  `OL_USERID` decimal(8,0) NOT NULL,
  `OL_LOGINTIME` datetime DEFAULT NULL,
  `OL_ACTIVETIME` datetime DEFAULT NULL,
  `OL_LASTLOGINTIME` datetime DEFAULT NULL,
  `OL_ACTIVENUM` decimal(8,0) DEFAULT NULL,
  `OL_LOGINNUM` decimal(8,0) DEFAULT NULL,
  `OL_IP` varchar(64) DEFAULT NULL,
  `OL_AUTHKEY` varchar(30) DEFAULT NULL,
  `OL_ACTIVENODEID` decimal(10,0) DEFAULT NULL,
  `OL_RIGHTTYPE` char(1) DEFAULT NULL,
  `OL_RIGHTID` varchar(10) DEFAULT NULL,
  `OL_DESC` varchar(255) DEFAULT NULL,
  `OL_STATUS` char(1) DEFAULT NULL,
  PRIMARY KEY (`OL_USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "eia_system"
#

DROP TABLE IF EXISTS `eia_system`;
CREATE TABLE `eia_system` (
  `SYSTEMID` decimal(8,0) NOT NULL COMMENT '//idºÅ',
  `SYSTEMNAME` varchar(64) DEFAULT NULL COMMENT '//ÏµÍ³Ãû³Æ',
  `CREATEDATE` datetime DEFAULT NULL COMMENT '//ÉÏÏßÈÕÆÚ',
  `PARENTID` decimal(8,0) DEFAULT NULL COMMENT '//¸¸ÏµÍ³',
  `VALID` decimal(1,0) DEFAULT NULL COMMENT '//ÊÇ·ñ¿ÉÓÃ£¬-1±íÊ¾ÎÞÐ§£¬0±íÊ¾ÓÐÐ§',
  `LAST_MODIFY_USER` varchar(20) DEFAULT NULL COMMENT '//×îºóÐÞ¸ÄÓÃ»§',
  `LAST_MODIFY_DATE` datetime DEFAULT NULL COMMENT '//×îºóÐÞ¸ÄÊ±¼ä',
  `INDEX_PAGE` varchar(64) DEFAULT NULL COMMENT '//Ê×Ò³',
  `SORDID` decimal(8,0) DEFAULT NULL COMMENT '//ÅÅÐòID',
  `MESSAGE_ID` decimal(8,0) DEFAULT NULL COMMENT '//¶ÔÓ¦Ä£¿é·¢²¼ÏµÍ³Òì³£ÐÅÏ¢µÄIDºÅ(ÓëÐÅÏ¢¶©ÔÄÄ£¿éÕûºÏ)',
  `SERVICE_JNDI` varchar(64) DEFAULT NULL COMMENT '//¸ÃÄ£¿éÌá¹©½Ó¿Ú·þÎñµÄJNDIµØÖ·',
  `DEPLOY_SERVER` varchar(64) DEFAULT NULL COMMENT '//Èç¿ç·þÎñÆ÷£¬ÔòÌá¹©¸Ã·þÎñÆ÷µÄµØÖ·¼°¶Ë¿Ú  Èç  172.16.1.105:8080',
  `SERVICE_HOME` varchar(64) DEFAULT NULL COMMENT '//Ìá¹©½Ó¿Ú·þÎñµÄHOME½Ó¿Ú,Èç com.sem.pp.webservices.PPWebservicesHome',
  `SERVICE_REMOTE` varchar(64) DEFAULT NULL COMMENT '//Ìá¹©½Ó¿Ú·þÎñµÄREMOTE½Ó¿Ú,Èçcom.sem.pp.webservices.PPWebservice',
  `FILE_DIR_ID` decimal(8,0) DEFAULT NULL COMMENT '////Ä£¿éµÄ¸½¼þÔÚÎÄ¼þ·þÎñÆ÷ÉÏÎÄ¼þÄ¿Â¼µÄIDºÅ,¸ÃIDºÅ¶ÔÓ¦KBSµÄÎÄ¼þ±àºÅ',
  PRIMARY KEY (`SYSTEMID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='//ÏµÍ³±í';

#
# Structure for table "eia_subscibe_event"
#

DROP TABLE IF EXISTS `eia_subscibe_event`;
CREATE TABLE `eia_subscibe_event` (
  `se_id` int(11) NOT NULL AUTO_INCREMENT,
  `IS_ID` decimal(8,0) DEFAULT NULL COMMENT '//fk,ÊÂ¼þËùÊôÏµÍ³',
  `SE_NAME` varchar(64) DEFAULT NULL COMMENT '//ÊÂ¼þÃû³Æ',
  `SE_TYPE` decimal(8,0) DEFAULT NULL COMMENT '//ÊÂ¼þÀàÐÍ£¬0±íÊ¾ÓÃ»§´¥·¢£¬1±íÊ¾¶¨Ê±´¥·¢',
  `SE_STATUS` decimal(8,0) DEFAULT NULL COMMENT '//×´Ì¬£¬-1±íÊ¾×÷·Ï',
  PRIMARY KEY (`se_id`),
  KEY `SE_F_KEY` (`IS_ID`),
  CONSTRAINT `SE_F_KEY` FOREIGN KEY (`IS_ID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=utf8 COMMENT='¿É¶©ÔÄµÄÊÂ¼þ±í';

#
# Structure for table "eia_subscibe"
#

DROP TABLE IF EXISTS `eia_subscibe`;
CREATE TABLE `eia_subscibe` (
  `su_id` int(11) NOT NULL AUTO_INCREMENT,
  `SU_EMP_ID` varchar(10) DEFAULT NULL COMMENT '//¶©ÔÄÕß¹¤ºÅ',
  `SE_ID` decimal(8,0) DEFAULT NULL COMMENT '//¶©ÔÄµÄÊÂ¼þ',
  `SU_ROUTE` decimal(8,0) DEFAULT NULL COMMENT '//¶©ÔÄÀàÐÍ£¬0±íÊ¾OA¶ÌÐÅ£¬1±íÊ¾MAIL£¬2±íÊ¾ÊÖ»ú¶ÌÐÅ£¨ÔÝÎ´ÆôÓÃ£©',
  `SU_START_DATE` datetime DEFAULT NULL COMMENT '//¶©ÔÄÉúÐ§Ê±¼ä',
  `SU_END_DATE` datetime DEFAULT NULL COMMENT '//¶©ÔÄÊ§Ð§Ê±¼ä',
  `SU_STATUS` decimal(8,0) DEFAULT NULL COMMENT '//¶©ÔÄ×´Ì¬£¬0±íÊ¾Õý³££¬-1±íÊ¾ÎÞÐ§',
  `SYSTEMID` decimal(8,0) DEFAULT NULL COMMENT '//ËùÊôÏµÍ³',
  PRIMARY KEY (`su_id`),
  KEY `SU_F_KEY1` (`SE_ID`),
  KEY `SU_F_KEY2` (`SYSTEMID`),
  CONSTRAINT `SU_F_KEY2` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='¶©ÔÄ±í';

#
# Structure for table "eia_properties"
#

DROP TABLE IF EXISTS `eia_properties`;
CREATE TABLE `eia_properties` (
  `NAME` varchar(64) NOT NULL COMMENT '²ÎÊýÃû',
  `VALUE` longtext COMMENT '²ÎÊýÖµ',
  `REMARK` varchar(512) DEFAULT NULL COMMENT '±¸×¢',
  `SYSTEMID` decimal(8,0) DEFAULT NULL COMMENT '//ËùÊôÏµÍ³',
  `DEFAULTVALUE` varchar(256) DEFAULT NULL COMMENT 'Ä¬ÈÏÖµ',
  `PARTYPE` decimal(8,0) DEFAULT NULL COMMENT '0±íÊ¾ÏµÍ³¼¶±ðÅäÖÃ,1±íÊ¾ÓÃ»§¼¶±ðÅäÖÃ',
  `SETDATE` datetime DEFAULT NULL COMMENT 'ÉèÖÃÈÕÆÚ',
  `SETUSER` decimal(8,0) DEFAULT NULL COMMENT 'ÉèÖÃÈËÔ±',
  `TIMELIMIT` decimal(8,0) DEFAULT NULL COMMENT '0±íÊ¾Ã»ÓÐÊ±¼äÏÞÖÆ,1±íÊ¾ÓÐÊ±¼äÏÞÖÆ',
  `STARTTIME` datetime DEFAULT NULL COMMENT 'µ±isTimeLimitÎª1Ê±,ÏÞÖÆµÄ¿ªÊ¼Ê±¼ä',
  `ENDTIME` datetime DEFAULT NULL COMMENT 'µ±isTimeLimitÎª1Ê±,ÏÞÖÆµÄ½áÊøÊ±¼ä',
  `PARENTNAME` varchar(64) DEFAULT NULL COMMENT 'µ±¶ÔÅäÖÃ½øÐÐ·ÖÀàÊ±Ê¹ÓÃ.',
  PRIMARY KEY (`NAME`),
  KEY `F_EIA_PROPERTIES` (`SYSTEMID`),
  CONSTRAINT `F_EIA_PROPERTIES` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='//ÏµÍ³²ÎÊý±í£¬ÓÃÓÚ±£´æÏµÍ³µÄ²ÎÊý';

#
# Structure for table "br_task_log"
#

DROP TABLE IF EXISTS `br_task_log`;
CREATE TABLE `br_task_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `SYSTEMID` decimal(8,0) DEFAULT NULL,
  `TASKID` decimal(8,0) DEFAULT NULL,
  `BEGIN_RUN_DATE` datetime DEFAULT NULL COMMENT '//¿ªÊ¼Ö´ÐÐÊ±¼ä',
  `END_RUN_DATE` datetime DEFAULT NULL COMMENT '//½áÊøÊ±¼ä',
  `RUN_RESULT` decimal(8,0) DEFAULT NULL COMMENT '//½á¹û±íÊ¾£¬0±íÊ¾´¦ÀíÖÐ£¬-1±íÊ¾´¦ÀíÊ§°Ü£¬1±íÊ¾´¦Àí³É¹¦',
  `RUN_CONTENT` varchar(1024) DEFAULT NULL COMMENT '//´¦Àí½á¹û',
  PRIMARY KEY (`id`),
  KEY `F_BR_TASK_LOG_1` (`SYSTEMID`),
  CONSTRAINT `F_BR_TASK_LOG_1` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ºóÌ¨Ö´ÐÐÈÎÎñµÄ½á¹û';

#
# Structure for table "br_task"
#

DROP TABLE IF EXISTS `br_task`;
CREATE TABLE `br_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(64) DEFAULT NULL COMMENT '//ÃèÊö',
  `SYSTEMID` decimal(8,0) DEFAULT NULL COMMENT '//Ä£¿é',
  `METHOD` varchar(64) DEFAULT NULL COMMENT '//REMOTE·½·¨ÏÂµÄ½Ó¿ÚÃû³Æ',
  `ARGMENT` varchar(64) DEFAULT NULL COMMENT '//²ÎÊýÀàÐÍ',
  `TASK_TYPE` decimal(8,0) DEFAULT NULL COMMENT '//¶¨Ê±µÄÀàÐÍ£¬0±íÊ¾Ã¿Ìì£¬1±íÊ¾Ã¿Ð¡Ê±£¬2±íÊ¾Ã¿ÖÜ£¬3±íÊ¾Ã¿ÔÂ',
  `TASK_RUN_TIME` datetime DEFAULT NULL COMMENT '//ÔËÐÐµÄÊ±¼ä',
  `LAST_RUN_DATE` datetime DEFAULT NULL COMMENT '//×îºóÒ»´ÎÔËÐÐÊ±¼ä',
  `LAST_RUN_RESULT` decimal(1,0) DEFAULT NULL COMMENT '//×îºóÒ»´ÎÔËÐÐ½á¹û',
  `LAST_RUN_REMARK` varchar(1024) DEFAULT NULL COMMENT '//×îºóÒ»´ÎÔËÐÐµÄÐÅÏ¢',
  `REMARK` varchar(128) DEFAULT NULL COMMENT '//±¸×¢',
  `STATUS` decimal(8,0) DEFAULT NULL COMMENT '//×´Ì¬,0±íÊ¾Õý³££¬-1±íÊ¾ÒÑÉ¾³ý',
  PRIMARY KEY (`id`),
  KEY `F_BR_TASK_1` (`SYSTEMID`),
  CONSTRAINT `F_BR_TASK_1` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='ºóÌ¨ÔËÐÐ³ÌÐòµÄÁÐ±í';

#
# Structure for table "eia_system_message"
#

DROP TABLE IF EXISTS `eia_system_message`;
CREATE TABLE `eia_system_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `SYSTEMID` decimal(8,0) DEFAULT NULL COMMENT '//ÏµÍ³',
  `SUBJECT` varchar(64) DEFAULT NULL COMMENT '//Ö÷Ìâ',
  `CONTENT` varchar(128) DEFAULT NULL COMMENT '//ÏêÏ¸ÄÚÈÝ',
  `START_DATE` datetime DEFAULT NULL COMMENT '//¿ªÊ¼ÓÐÐ§ÈÕÆÚ',
  `END_DATE` datetime DEFAULT NULL COMMENT '//½áÊøÓÐÐ§ÈÕÆÚ',
  `STATUS` decimal(1,0) DEFAULT NULL COMMENT '//ÖØÒªÐÔ£¬0±íÊ¾Ò»°ã£¬1±íÊ¾ÖØÒª£¬ÐèÁ¢¼´µ¯³ö´°¿Ú',
  `ATTACH` decimal(8,0) DEFAULT NULL COMMENT '//¸½¼þIDºÅ',
  `ATTACH_NAME` varchar(64) DEFAULT NULL COMMENT '//¸½¼þÃû',
  PRIMARY KEY (`id`),
  KEY `F_EIA_SYSTEM_MESSAGE_1` (`SYSTEMID`),
  CONSTRAINT `F_EIA_SYSTEM_MESSAGE_1` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

#
# Structure for table "eia_user_properties"
#

DROP TABLE IF EXISTS `eia_user_properties`;
CREATE TABLE `eia_user_properties` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `US_ID` decimal(8,0) DEFAULT NULL,
  `NAME` varchar(64) DEFAULT NULL,
  `VALUE` varchar(256) DEFAULT NULL,
  `SETDATE` datetime DEFAULT NULL COMMENT '//ÉèÖÃÈÕÆÚ',
  `SETUSER` varchar(8) DEFAULT NULL COMMENT '//ÉèÖÃÈËÔ±',
  `ISTIMELIMIT` decimal(1,0) DEFAULT NULL COMMENT '0±íÊ¾Ã»ÓÐÊ±¼äÏÞÖÆ,1±íÊ¾ÓÐÊ±¼äÏÞÖÆ',
  `STARTTIME` datetime DEFAULT NULL COMMENT 'µ±isTimeLimitÎª1Ê±,ÏÞÖÆµÄ¿ªÊ¼Ê±¼ä',
  `ENDTIME` datetime DEFAULT NULL COMMENT 'µ±isTimeLimitÎª1Ê±,ÏÞÖÆµÄ½áÊøÊ±¼ä',
  PRIMARY KEY (`id`),
  KEY `F_EIA_USER_PROPERTIES_2` (`NAME`),
  CONSTRAINT `F_EIA_USER_PROPERTIES_2` FOREIGN KEY (`NAME`) REFERENCES `eia_properties` (`NAME`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "eia_user_push"
#

DROP TABLE IF EXISTS `eia_user_push`;
CREATE TABLE `eia_user_push` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL COMMENT '用户ID',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '推送类型',
  `onFlag` int(11) DEFAULT NULL COMMENT '0表示开，1表示关',
  `SYSTEMID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

#
# Structure for table "eia_user_push_log"
#

DROP TABLE IF EXISTS `eia_user_push_log`;
CREATE TABLE `eia_user_push_log` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createUser` int(11) DEFAULT NULL,
  `createDate` date DEFAULT NULL,
  `result` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '0表示已发送，-1表示失败，1表示已发送',
  `SYSTEMID` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `hasRead` int(11) NOT NULL DEFAULT '0' COMMENT '0表示未读，1表示已读',
  `readDate` date DEFAULT NULL,
  `bzId` int(11) DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

#
# Structure for table "eia_user_push_type"
#

DROP TABLE IF EXISTS `eia_user_push_type`;
CREATE TABLE `eia_user_push_type` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SYSTEMID` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `custom` int(11) NOT NULL DEFAULT '0' COMMENT '0表示用户可配，1表示用户不可配',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

#
# Structure for table "jms_task"
#

DROP TABLE IF EXISTS `jms_task`;
CREATE TABLE `jms_task` (
  `taskid` int(11) NOT NULL AUTO_INCREMENT,
  `MESSAGEID` varchar(64) DEFAULT NULL,
  `QUEUENAME` varchar(64) DEFAULT NULL,
  `MESSAGE` varchar(2048) DEFAULT NULL,
  `SYSTEMID` decimal(8,0) DEFAULT NULL,
  `EMPID` varchar(6) DEFAULT NULL,
  `CREATEDATE` datetime DEFAULT NULL,
  `STATUS` decimal(8,0) DEFAULT NULL COMMENT '×´Ì¬£º0±íÊ¾ÒÑÌá½»¡¢ÕýÔÚÔËÐÐ,1±íÊ¾ÒÑ³É¹¦Ö´ÐÐ£¬2±íÊ¾ÕÐ´ýÊ§°Ü',
  `FINISHDATE` datetime DEFAULT NULL,
  `DEALRESULT` varchar(2048) DEFAULT NULL COMMENT '´¦Àí½á¹ûËµÃ÷',
  `REMARK` varchar(256) DEFAULT NULL COMMENT '±¸×¢',
  `CLOSEFLAG` decimal(1,0) DEFAULT NULL COMMENT '0±íÊ¾Î´½á°¸£¬1±íÊ¾½á°¸',
  PRIMARY KEY (`taskid`),
  KEY `IDX_JMS_TASK_1` (`EMPID`),
  KEY `F_JMS_TASK_1` (`SYSTEMID`),
  CONSTRAINT `F_JMS_TASK_1` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COMMENT='background ÈÎÎñÖ´ÐÐ×´¿ö±í';

#
# Structure for table "persistent_logins"
#

DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `SERIES` varchar(64) NOT NULL,
  `LAST_USED` datetime DEFAULT NULL,
  `TOKEN` varchar(64) DEFAULT NULL,
  `USER_NAME` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`SERIES`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "qrtz_job_details"
#

DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "rb_actions"
#

DROP TABLE IF EXISTS `rb_actions`;
CREATE TABLE `rb_actions` (
  `actionid` int(11) NOT NULL AUTO_INCREMENT,
  `SYSTEMID` decimal(8,0) DEFAULT NULL,
  `ACTIONNAME` varchar(100) DEFAULT NULL,
  `FATHERID` decimal(8,0) DEFAULT NULL,
  `WEBLINK` varchar(100) DEFAULT NULL,
  `FRAME` varchar(100) DEFAULT NULL,
  `OPERATIONID` decimal(8,0) DEFAULT NULL,
  `SORTID` decimal(8,0) DEFAULT NULL COMMENT '//ÅÅÐòºÅ',
  `VALID` decimal(1,0) DEFAULT NULL,
  `SINGLEMODE` decimal(1,0) DEFAULT NULL COMMENT '////×ªÒå£¬ÊÇ·ñÎªµ¥Ä£Ä£Ê½£¬0±íÊ¾²»ÊÇ£¬1±íÊ¾ÊÇ',
  PRIMARY KEY (`actionid`),
  KEY `F_RB_ACTIONS_1` (`SYSTEMID`),
  CONSTRAINT `F_RB_ACTIONS_1` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB AUTO_INCREMENT=11101 DEFAULT CHARSET=utf8;

#
# Structure for table "rb_actions_favorite"
#

DROP TABLE IF EXISTS `rb_actions_favorite`;
CREATE TABLE `rb_actions_favorite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `EMPID` varchar(10) DEFAULT NULL COMMENT '¹¤ºÅ',
  `TYPE` decimal(8,0) DEFAULT NULL COMMENT 'ÀàÐÍ1±íÊ¾¹¦ÄÜ£¬2±íÊ¾±¨±í',
  `ACTIONID` varchar(64) DEFAULT NULL COMMENT '²Ëµ¥IDºÅ',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '²úÉúÈÕÆÚ',
  `NAME` varchar(64) DEFAULT NULL COMMENT 'ÊÕ²Ø²Ëµ¥Ãû',
  `SYSTEMID` decimal(8,0) DEFAULT NULL COMMENT 'ÏµÍ³',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='¸öÈË¹¦ÄÜÊÕ²Ø¼Ð';

#
# Structure for table "rb_operations"
#

DROP TABLE IF EXISTS `rb_operations`;
CREATE TABLE `rb_operations` (
  `operationid` int(11) NOT NULL AUTO_INCREMENT,
  `OPERATIONNAME` varchar(64) DEFAULT NULL,
  `DESCRIPTION` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`operationid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "rb_pabinding"
#

DROP TABLE IF EXISTS `rb_pabinding`;
CREATE TABLE `rb_pabinding` (
  `bindingid` int(11) NOT NULL AUTO_INCREMENT,
  `ROLEID` decimal(8,0) DEFAULT NULL,
  `ACTIONID` decimal(8,0) DEFAULT NULL,
  `RESOURCEID` decimal(8,0) DEFAULT NULL,
  `VALID` decimal(1,0) DEFAULT NULL,
  `RIGHTCODE` varchar(16) DEFAULT NULL COMMENT 'A±íÊ¾ÐÂÔö,U±íÊ¾ÐÞ¸Ä,L±íÊ¾²éÑ¯,D±íÊ¾É¾³ý,E±íÊ¾µ¼³ö,O±íÊ¾ÆäËü,ÇÒ¿É×Ô¶¨Òå£¬¼°¸´ºÏ',
  `SYSTEMID` decimal(8,0) DEFAULT NULL,
  `OR_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`bindingid`),
  KEY `F_RB_PABINDING` (`SYSTEMID`),
  KEY `F_RB_PABINGING_1` (`ROLEID`),
  KEY `F_RB_PABINGING_2` (`ACTIONID`),
  CONSTRAINT `F_RB_PABINDING` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8;

#
# Structure for table "rb_resources"
#

DROP TABLE IF EXISTS `rb_resources`;
CREATE TABLE `rb_resources` (
  `resourceid` int(11) NOT NULL AUTO_INCREMENT,
  `SYSTEMID` decimal(8,0) DEFAULT NULL,
  `RESOURCENAME` varchar(100) DEFAULT NULL,
  `RESOURCEPARENT` decimal(8,0) DEFAULT NULL,
  `DESCRIPTION` varchar(256) DEFAULT NULL,
  `SORTID` decimal(8,0) DEFAULT NULL COMMENT '//ÅÅÐòºÅ',
  `VALID` decimal(1,0) DEFAULT NULL,
  PRIMARY KEY (`resourceid`),
  KEY `F_RB_RESOURCES_1` (`SYSTEMID`),
  CONSTRAINT `F_RB_RESOURCES_1` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

#
# Structure for table "rb_roles"
#

DROP TABLE IF EXISTS `rb_roles`;
CREATE TABLE `rb_roles` (
  `roleid` int(11) NOT NULL AUTO_INCREMENT,
  `SYSTEMID` decimal(8,0) DEFAULT NULL,
  `ROLENAME` varchar(100) DEFAULT NULL,
  `DESCRIPTION` varchar(512) DEFAULT NULL,
  `VALID` decimal(1,0) DEFAULT NULL,
  `AFFIX` decimal(8,0) DEFAULT NULL COMMENT '//¸½¼þID',
  `OR_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`roleid`),
  KEY `F_RB_ROLES_1` (`SYSTEMID`),
  CONSTRAINT `F_RB_ROLES_1` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8;

#
# Structure for table "rb_uabinding"
#

DROP TABLE IF EXISTS `rb_uabinding`;
CREATE TABLE `rb_uabinding` (
  `bindingid` int(11) NOT NULL AUTO_INCREMENT,
  `ROLEID` decimal(8,0) DEFAULT NULL,
  `USERID` decimal(8,0) DEFAULT NULL,
  `DEPTID` varchar(10) DEFAULT NULL,
  `LEVELID` decimal(8,0) DEFAULT NULL,
  `SYSTEMID` decimal(8,0) DEFAULT NULL,
  `OR_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`bindingid`),
  KEY `F_RB_UABINDING_1` (`ROLEID`),
  KEY `F_RB_UABINDING_3` (`SYSTEMID`),
  CONSTRAINT `F_RB_UABINDING_3` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

#
# Structure for table "rb_userlogin"
#

DROP TABLE IF EXISTS `rb_userlogin`;
CREATE TABLE `rb_userlogin` (
  `loginid` int(11) NOT NULL AUTO_INCREMENT,
  `SYSTEMID` decimal(8,0) DEFAULT NULL,
  `USERID` decimal(8,0) DEFAULT NULL,
  `LASTROLEID` decimal(8,0) DEFAULT NULL,
  `LASTLOGINDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`loginid`),
  KEY `F_RB_USERLOGIN_1` (`SYSTEMID`),
  CONSTRAINT `F_RB_USERLOGIN_1` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB AUTO_INCREMENT=1359 DEFAULT CHARSET=utf8;

#
# Structure for table "rp_rabinding"
#

DROP TABLE IF EXISTS `rp_rabinding`;
CREATE TABLE `rp_rabinding` (
  `bindingid` int(11) NOT NULL AUTO_INCREMENT,
  `ROLEID` decimal(8,0) DEFAULT NULL COMMENT '//½ÇÉ«IDºÅ',
  `REPORTID` decimal(8,0) DEFAULT NULL COMMENT '//±¨±íIDºÅ',
  `VALID` decimal(8,0) DEFAULT NULL COMMENT '//ÊÇ·ñÓÐÐ§,0±íÊ¾ÓÐÐ§£¬1±íÊ¾ÎÞÐ§',
  `RIGHTCODE` varchar(64) DEFAULT NULL COMMENT '//È¨ÏÞ´úÂë',
  `SYSTEMID` decimal(8,0) DEFAULT NULL COMMENT '//ÏµÍ³',
  PRIMARY KEY (`bindingid`),
  KEY `F_RP_RABINDING_1` (`ROLEID`),
  KEY `F_RP_RABINDING_2` (`SYSTEMID`),
  KEY `F_RP_RABINDING_3` (`REPORTID`),
  CONSTRAINT `F_RP_RABINDING_2` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='±¨±íÈ¨ÏÞ¹ÜÀí±í';

#
# Structure for table "rp_report_module"
#

DROP TABLE IF EXISTS `rp_report_module`;
CREATE TABLE `rp_report_module` (
  `moduleid` int(11) NOT NULL AUTO_INCREMENT,
  `SYSTEMID` decimal(8,0) DEFAULT NULL COMMENT '//Ä£¿éºÅ',
  `NAME` varchar(64) DEFAULT NULL COMMENT '//±¨±íÃû×Ö',
  `MODULE` varchar(64) DEFAULT NULL COMMENT '//±¨±íÄ£°å',
  `NEED_DATE_KEY` decimal(1,0) DEFAULT NULL COMMENT '//ÊÇ·ñÐèÒªÊ±¼äÑ¡Ôñ¿ò 0±íÊ¾²»ÐèÒª£¬1±íÊ¾ÐèÒªÈÕDateKey 2±íÊ¾ÐèÒªÔÂDateKey 3±íÊ¾ÐèÒªÄêDateKey 4±íÊ¾ÐèÒªÖÜDateKey 5±íÊ¾ÐèÒª¼¾DateKey',
  `NEED_LINE_KEY` decimal(1,0) DEFAULT NULL COMMENT '//ÊÇ·ñÐèÒªÏß±ðÑ¡Ôñ¿ò  0±íÊ¾²»ÐèÒª£¬1±íÊ¾ÐèÒª',
  `NEED_SERIES_KEY` decimal(1,0) DEFAULT NULL COMMENT '//ÊÇ·ñÐèÒª³µÏµÑ¡Ôñ¿ò 0±íÊ¾²»ÐèÒª£¬1±íÊ¾ÐèÒª',
  `NEED_MODEL_KEY` decimal(1,0) DEFAULT NULL COMMENT '//ÊÇ·ñÐèÒª³µÐÍÑ¡Ôñ¿ò 0±íÊ¾²»ÐèÒª£¬1±íÊ¾ÐèÒª',
  `NEED_WORKSHOP` decimal(1,0) DEFAULT NULL COMMENT '//ÊÇ·ñÐèÒª³µ¼äÑ¡Ôñ¿ò 0±íÊ¾²»ÐèÒª£¬1±íÊ¾ÐèÒª',
  `DATE_KEY_TYPE` decimal(1,0) DEFAULT NULL COMMENT '//ÊÇ·ñÊÇ¿çÊ±¼äÑ¡Ôñ¿ò£¬0±íÊ¾Õý³£µÄÈÕÆÚÑ¡Ôñ¿ò eg:20090101 ,1±íÊ¾¿çÈÕÆÚÑ¡Ôñ¿ò: eg:20090101-20090110',
  `NEED_LOG` decimal(1,0) DEFAULT NULL COMMENT '//ÊÇ·ñ¶Ô·ÃÎÊÊ±½øÐÐLOG£¬0±íÊ¾²»½øÐÐLOG£¬1±íÊ¾¶Ô·ÃÎÊ½øÐÐLog',
  `NEED_COLOR_KEY` decimal(1,0) DEFAULT NULL COMMENT '//ÊÇ·ñÐèÒªÑÕÉ«Ñ¡Ôñ¿ò  0±íÊ¾²»ÐèÒª 1±íÊ¾ÐèÒª',
  `OTHER_DATAKEY_MODE` varchar(64) DEFAULT NULL,
  `NEED_TIME_KEY` decimal(1,0) DEFAULT NULL COMMENT '//ÊÇ·ñÐèÒªÊ±¼äÑ¡Ôñ¿ò£¬0±íÊ¾²»ÐèÒª£¬1±íÊ¾ÐèÒª',
  `TIMEKEY_MODE` varchar(64) DEFAULT NULL,
  `CUSTOM_KEY` varchar(2048) DEFAULT NULL COMMENT '//ÆäËüÉ¸Ñ¡Ìõ¼þ',
  `REMARK` varchar(64) DEFAULT NULL COMMENT '//±¨±íËµÃ÷',
  `PARENT` decimal(8,0) DEFAULT NULL COMMENT '//¸ù½Úµã',
  `NEED_CONTROLPOINT` decimal(8,0) DEFAULT NULL COMMENT '//ÊÇ·ñÐèÒª¹ÜÖÆµãÑ¡Ôñ¿ò£¬0±íÊ¾²»ÐèÒª£¬1±íÊ¾ÐèÒª',
  `SORT_ID` decimal(8,0) DEFAULT NULL COMMENT '//ÅÅÐòºÅ',
  `VALID` decimal(1,0) DEFAULT NULL COMMENT '//ÊÇ·ñÓÐÐ§£¬0±íÊ¾¿ÉÒÔÊ¹ÓÃ£¬1±íÊ¾²»ÄÜÊ¹ÓÃ',
  `NEED_DEPT_KEY` decimal(1,0) DEFAULT NULL COMMENT '//ÊÇ·ñÐèÒª²¿ÃÅÑ¡Ôñ¿ò,0±íÊ¾ÐèÒª£¬1±íÊ¾²»ÐèÒª',
  `OLD_VERSION` decimal(1,0) DEFAULT NULL COMMENT '//0±íÊ¾ÐÂ°æ±¾µÄRAQ£¬1±íÊ¾¾É°æ±¾µÄRAQ',
  `PARAM_MODULE` decimal(1,0) DEFAULT NULL COMMENT '//²ÎÊýÄ£°å£¬0±íÊ¾²ÎÊý¿òÇ¶Èëµ½±¨±íÒ³ÃæÖÐ£¬1±íÊ¾²ÎÊý¿ò¶ÀÁ¢ÓÚ±¨±íÒ³Ãæ',
  `JAVASCRIPT` varchar(2048) DEFAULT NULL COMMENT '//×Ô¶¨Òå½Å±¾',
  `SUBMIT_SCRIPT` varchar(2048) DEFAULT NULL COMMENT '//×Ô¶¨ÒåÌá½»½Å±¾',
  `EXPORT_TYPE` decimal(1,0) DEFAULT NULL COMMENT '//Êä³öµÄÀàÐÍ£¬0±íÊ¾html,1±íÊ¾excel,2±íÊ¾PDF,3±íÊ¾Word,4±íÊ¾text,Ö»¶Ô4.0°æ±¾ÒÔÉÏµÄ±¨±íÉúÐ§',
  PRIMARY KEY (`moduleid`),
  KEY `F_RP_REPORT_MODULE_1` (`SYSTEMID`),
  CONSTRAINT `F_RP_REPORT_MODULE_1` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='±¨±íÄ£°å';

#
# Structure for table "rp_report_schedule"
#

DROP TABLE IF EXISTS `rp_report_schedule`;
CREATE TABLE `rp_report_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `SYSTEMID` decimal(8,0) DEFAULT NULL COMMENT 'Ä£¿é',
  `MODULEID` decimal(8,0) DEFAULT NULL COMMENT '±¨±íÄ£°å',
  `CIRCLE_TYPE` decimal(8,0) DEFAULT NULL COMMENT 'ÖØ¸´ÀàÐÍ,0±íÊ¾µ¥´Î,1±íÊ¾Ã¿Ð¡Ê±,2±íÊ¾Ã¿Ìì,3±íÊ¾Ã¿ÖÜ,4±íÊ¾Ã¿ÔÂ,5±íÊ¾Ã¿Äê',
  `FIRST_EXCUTE_DATE` datetime DEFAULT NULL COMMENT 'µÚÒ»´ÎÖ´ÐÐÊ±¼ä',
  `NEXT_EXCUTE_DATE` datetime DEFAULT NULL COMMENT 'ÏÂ´ÎÖ´ÐÐÊ±¼ä',
  `PARAMETER` varchar(128) DEFAULT NULL COMMENT '²ÎÊý,ÆäÖÐdateKey»á×Ô¶¯´«',
  `RECIPIENTS_TYPE` decimal(8,0) DEFAULT NULL COMMENT 'ÊÕ¼þÈËµÄÀàÐÍ,0±íÊ¾µç×ÓÓÊÏä,1±íÊ¾¹¤ºÅ,2±íÊ¾²¿ÃÅ±àÂë',
  `RECIPIENTS` varchar(128) DEFAULT NULL COMMENT 'ÊÕ¼þÈË,¶à¸ö½«·ÖºÅ¸ô¿ª',
  `MAIL_SUBJECT` varchar(64) DEFAULT NULL COMMENT 'ÓÊ¼þ±êÌâ',
  `MAIL_CONTENT` varchar(512) DEFAULT NULL COMMENT 'ÓÊ¼þÕýÎÄ',
  `REPORT_NAME` varchar(64) DEFAULT NULL COMMENT '±¨±íÎÄ¼þÃû',
  `RECIPIENTS_SOURCES` decimal(8,0) DEFAULT NULL COMMENT '²ÎÊýºÍ½ÓÊÕÕßÀ´Ô´,0±íÊ¾À´×ÔÅäÖÃ,1±íÊ¾À´×Ô½Ó¿Ú',
  `RECIPIENTS_IMPLEMENT_METHOD` varchar(64) DEFAULT NULL COMMENT 'ÊµÏÖ·½·¨Ãû',
  `VALID` decimal(8,0) DEFAULT NULL COMMENT 'ÊÇ·ñÓÐÐ§,0±íÊ¾ÓÐÐ§,1±íÊ¾ÎÞÐ§',
  `REMARK` varchar(256) DEFAULT NULL COMMENT '±¸×¢',
  `LAST_EXCUTE_RESULT` decimal(8,0) DEFAULT NULL COMMENT 'ÉÏÒ»´ÎÖ´ÐÐ½á¹û,0±íÊ¾³É¹¦,1±íÊ¾Ê§°Ü',
  `LAST_EXCUTE_RESULT_REMARK` varchar(256) DEFAULT NULL COMMENT 'ÉÏÒ»´ÎÖ´ÐÐ½á¹ûËµÃ÷',
  `LAST_EXCUTE_DATE` datetime DEFAULT NULL COMMENT 'ÉÏÒ»´ÎÖ´ÐÐÊ±¼ä',
  `RECIPIENTS_EMPID` varchar(128) DEFAULT NULL COMMENT 'ÊÕ¼þÈËµÄÔ±¹¤¹¤ºÅ,¶à¸ö½«·ÖºÅ¸ô¿ª',
  `RECIPIENTS_DPNO` varchar(128) DEFAULT NULL COMMENT 'ÊÕ¼þÈËµÄ²¿ÃÅID,¶à¸ö½«·ÖºÅ¸ô¿ª',
  PRIMARY KEY (`id`),
  KEY `F_RP_REPORT_SCHEDULE_1` (`SYSTEMID`),
  KEY `F_RP_REPORT_SCHEDULE_2` (`MODULEID`),
  CONSTRAINT `F_RP_REPORT_SCHEDULE_1` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='±¨±íµ÷¶ÈÆ÷';

#
# Structure for table "sy_department"
#

DROP TABLE IF EXISTS `sy_department`;
CREATE TABLE `sy_department` (
  `DE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DE_ORDERNO` int(11) DEFAULT NULL,
  `DE_NAME` varchar(255) DEFAULT NULL,
  `DE_ABBR` varchar(255) DEFAULT NULL,
  `DE_GROUPNAME` varchar(255) DEFAULT NULL,
  `DE_KIND` varchar(255) DEFAULT NULL,
  `DE_TEL` varchar(255) DEFAULT NULL,
  `DE_FAX` varchar(255) DEFAULT NULL,
  `DE_LEADERID` int(11) DEFAULT NULL,
  `DE_CHIEFID` int(11) DEFAULT NULL,
  `DE_REMARK` varchar(255) DEFAULT NULL,
  `DE_PARENTID` int(11) DEFAULT NULL,
  `DE_DEPTLEVEL` int(11) DEFAULT NULL,
  `DE_MARK` varchar(255) DEFAULT NULL,
  `DE_STATUS` int(11) DEFAULT NULL,
  `DE_REGISTERDATE` date DEFAULT NULL,
  `DE_LOGOUTDATE` date DEFAULT NULL,
  `DE_DEPTTYPE` varchar(255) DEFAULT NULL,
  `DE_DEPTNUM` varchar(255) DEFAULT NULL,
  `OR_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`DE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=922 DEFAULT CHARSET=utf8;

#
# Structure for table "sy_duty"
#

DROP TABLE IF EXISTS `sy_duty`;
CREATE TABLE `sy_duty` (
  `DU_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DU_NAME` varchar(255) DEFAULT NULL,
  `DU_PARENTID` int(11) DEFAULT NULL,
  `DU_LEVEL` int(11) DEFAULT NULL,
  `DU_NOTE` varchar(255) DEFAULT NULL,
  `DU_MARK` varchar(255) DEFAULT NULL,
  `DU_ISDIRECTOR` int(11) DEFAULT NULL,
  `DU_DUTYNUM` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DU_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

#
# Structure for table "sy_organise"
#

DROP TABLE IF EXISTS `sy_organise`;
CREATE TABLE `sy_organise` (
  `OR_ID` int(11) NOT NULL AUTO_INCREMENT,
  `OR_CODE` varchar(255) NOT NULL DEFAULT '',
  `OR_NAME` varchar(255) DEFAULT NULL,
  `OR_ABBR` varchar(255) DEFAULT NULL,
  `OR_PARENTID` int(11) DEFAULT NULL,
  `OR_TEL` varchar(255) DEFAULT NULL,
  `OR_FAX` varchar(255) DEFAULT NULL,
  `OR_ADDR` varchar(255) DEFAULT NULL,
  `OR_POSTCODE` varchar(255) DEFAULT NULL,
  `OR_ORGALEVEL` int(11) DEFAULT NULL,
  `OR_REMARK` varchar(255) DEFAULT '' COMMENT '广告词',
  `OR_REGION` varchar(255) DEFAULT NULL COMMENT '酒吧banner',
  `OR_REGIONABBR` varchar(255) DEFAULT NULL COMMENT '企业精神',
  `OR_STATUS` varchar(255) DEFAULT NULL,
  `OR_REGISTERDATE` date DEFAULT NULL,
  `OR_LOGOUTDATE` date DEFAULT NULL,
  PRIMARY KEY (`OR_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1148 DEFAULT CHARSET=utf8;

#
# Structure for table "sy_userduty"
#

DROP TABLE IF EXISTS `sy_userduty`;
CREATE TABLE `sy_userduty` (
  `DE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DU_ID` int(11) NOT NULL DEFAULT '0',
  `US_ID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`DE_ID`,`DU_ID`,`US_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=889 DEFAULT CHARSET=utf8;

#
# Structure for table "sy_users"
#

DROP TABLE IF EXISTS `sy_users`;
CREATE TABLE `sy_users` (
  `US_ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `loginId` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `party` int(11) DEFAULT NULL,
  `peoples` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `nativePlace` varchar(255) DEFAULT NULL,
  `wedLock` varchar(255) DEFAULT NULL,
  `educateLevel` int(11) DEFAULT NULL,
  `archAddr` varchar(255) DEFAULT NULL,
  `credentialType` int(11) DEFAULT NULL,
  `credentialNo` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `room` varchar(255) DEFAULT NULL,
  `addr` varchar(255) DEFAULT NULL,
  `postcode` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `orderNo` int(11) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `housetel` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `mark` varchar(255) DEFAULT NULL COMMENT '个人图像',
  `status` varchar(255) DEFAULT NULL,
  `registerDate` date DEFAULT NULL,
  `logoutDate` date DEFAULT NULL,
  `timeType` int(11) DEFAULT NULL,
  `engineer` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `bloodType` varchar(255) DEFAULT NULL,
  `isDirector` int(11) DEFAULT NULL,
  `isAd` int(11) NOT NULL DEFAULT '0' COMMENT '0表示正常用户，1表示用户被锁定',
  `isOpen` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `DE_ID` int(11) DEFAULT NULL,
  `OR_ID` int(11) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `wechat` varchar(255) DEFAULT NULL,
  `weibo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`US_ID`),
  UNIQUE KEY `U_SY_USERS` (`loginId`),
  UNIQUE KEY `U_SY_USERS_2` (`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=130920 DEFAULT CHARSET=utf8;

#
# Structure for table "sy_friends"
#

DROP TABLE IF EXISTS `sy_friends`;
CREATE TABLE `sy_friends` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL DEFAULT '0',
  `friendId` int(11) DEFAULT NULL,
  `makeWay` int(1) DEFAULT NULL,
  `makeDate` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `U_friendId` (`userId`,`friendId`),
  KEY `F_SY_FRIEND1` (`userId`),
  KEY `F_SY_FRIEND2` (`friendId`),
  CONSTRAINT `F_SY_FRIEND1` FOREIGN KEY (`userId`) REFERENCES `sy_users` (`US_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `F_SY_FRIEND2` FOREIGN KEY (`friendId`) REFERENCES `sy_users` (`US_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=214 DEFAULT CHARSET=utf8;

#
# Structure for table "sy_blacklist"
#

DROP TABLE IF EXISTS `sy_blacklist`;
CREATE TABLE `sy_blacklist` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL DEFAULT '0',
  `blackUserId` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `createDate` datetime DEFAULT NULL COMMENT '日期',
  PRIMARY KEY (`Id`),
  KEY `F_SY_BLACKLIST_2` (`userId`),
  CONSTRAINT `F_SY_BLACKLIST_2` FOREIGN KEY (`userId`) REFERENCES `sy_users` (`US_ID`),
  CONSTRAINT `F_SY_BLACKList` FOREIGN KEY (`userId`) REFERENCES `sy_users` (`US_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='好友黑名单';

#
# Structure for table "sys_modules"
#

DROP TABLE IF EXISTS `sys_modules`;
CREATE TABLE `sys_modules` (
  `MODULE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `APPLICATION` varchar(100) DEFAULT NULL,
  `CONTROLLER` varchar(100) DEFAULT NULL,
  `ENABLE` int(11) DEFAULT NULL,
  `I_LEVEL` int(11) DEFAULT NULL,
  `LEAF` int(11) DEFAULT NULL,
  `MODULE_DESC` varchar(200) DEFAULT NULL,
  `MODULE_NAME` varchar(100) NOT NULL,
  `MODULE_TYPE` varchar(100) DEFAULT NULL,
  `MODULE_URL` varchar(100) DEFAULT NULL,
  `PARENT` varchar(100) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  PRIMARY KEY (`MODULE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

#
# Structure for table "sys_authorities"
#

DROP TABLE IF EXISTS `sys_authorities`;
CREATE TABLE `sys_authorities` (
  `AUTHORITY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `AUTHORITY_DESC` varchar(255) DEFAULT NULL,
  `AUTHORITY_MARK` varchar(100) DEFAULT NULL,
  `AUTHORITY_NAME` varchar(100) NOT NULL,
  `ENABLE` int(11) DEFAULT NULL,
  `ISSYS` int(11) DEFAULT NULL,
  `MESSAGE` varchar(100) DEFAULT NULL,
  `MODULE_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`AUTHORITY_ID`),
  KEY `FK_2nr3qta7xjorh3qug4iq06yxh` (`MODULE_ID`),
  CONSTRAINT `FK_2nr3qta7xjorh3qug4iq06yxh` FOREIGN KEY (`MODULE_ID`) REFERENCES `sys_modules` (`MODULE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Structure for table "sys_resources"
#

DROP TABLE IF EXISTS `sys_resources`;
CREATE TABLE `sys_resources` (
  `RESOURCE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ENABLE` int(11) DEFAULT NULL,
  `ISSYS` int(11) DEFAULT NULL,
  `PRIORITY` varchar(100) DEFAULT NULL,
  `RESOURCE_DESC` varchar(200) DEFAULT NULL,
  `RESOURCE_NAME` varchar(100) DEFAULT NULL,
  `RESOURCE_PATH` varchar(200) DEFAULT NULL,
  `RESOURCE_TYPE` varchar(100) DEFAULT NULL,
  `MODULE_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`RESOURCE_ID`),
  KEY `FK_htw1ji56ns6mbvsghnnr6d7uo` (`MODULE_ID`),
  CONSTRAINT `FK_htw1ji56ns6mbvsghnnr6d7uo` FOREIGN KEY (`MODULE_ID`) REFERENCES `sys_modules` (`MODULE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Structure for table "sys_authorities_resources"
#

DROP TABLE IF EXISTS `sys_authorities_resources`;
CREATE TABLE `sys_authorities_resources` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AUTHORITY_ID` int(11) NOT NULL,
  `RESOURCE_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_1m0dd2gosvuulmdaxjnmy3nyl` (`AUTHORITY_ID`),
  KEY `FK_d9m0gbj2e3bute4g77uaifrhl` (`RESOURCE_ID`),
  CONSTRAINT `FK_1m0dd2gosvuulmdaxjnmy3nyl` FOREIGN KEY (`AUTHORITY_ID`) REFERENCES `sys_authorities` (`AUTHORITY_ID`),
  CONSTRAINT `FK_d9m0gbj2e3bute4g77uaifrhl` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `sys_resources` (`RESOURCE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

#
# Structure for table "sys_roles"
#

DROP TABLE IF EXISTS `sys_roles`;
CREATE TABLE `sys_roles` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ENABLE` int(11) DEFAULT NULL,
  `ISSYS` int(11) DEFAULT NULL,
  `ROLE_DESC` varchar(200) DEFAULT NULL,
  `ROLE_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Structure for table "sys_roles_authorities"
#

DROP TABLE IF EXISTS `sys_roles_authorities`;
CREATE TABLE `sys_roles_authorities` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AUTHORITY_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_7dwwv22y0sbtfhls9499fbp31` (`AUTHORITY_ID`),
  KEY `FK_b5dg1coe74adbc1oxw9ls1g06` (`ROLE_ID`),
  CONSTRAINT `FK_7dwwv22y0sbtfhls9499fbp31` FOREIGN KEY (`AUTHORITY_ID`) REFERENCES `sys_authorities` (`AUTHORITY_ID`),
  CONSTRAINT `FK_b5dg1coe74adbc1oxw9ls1g06` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_roles` (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Structure for table "sys_roles_moudles"
#

DROP TABLE IF EXISTS `sys_roles_moudles`;
CREATE TABLE `sys_roles_moudles` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MODULE_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_lbqk4oso9fyf1vojla20hef0g` (`MODULE_ID`),
  KEY `FK_bexwb7eougvaeslrerr9j59rk` (`ROLE_ID`),
  CONSTRAINT `FK_bexwb7eougvaeslrerr9j59rk` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_roles` (`ROLE_ID`),
  CONSTRAINT `FK_lbqk4oso9fyf1vojla20hef0g` FOREIGN KEY (`MODULE_ID`) REFERENCES `sys_modules` (`MODULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "sys_users"
#

DROP TABLE IF EXISTS `sys_users`;
CREATE TABLE `sys_users` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACCOUNT_NON_EXPIRED` int(11) DEFAULT NULL,
  `ACCOUNT_NON_LOCKED` int(11) DEFAULT NULL,
  `CREDENTIALS_NON_EXPIRED` int(11) DEFAULT NULL,
  `DEAD_LINE` datetime DEFAULT NULL,
  `DEP_ID` varchar(100) DEFAULT NULL,
  `DEP_NAME` varchar(100) DEFAULT NULL,
  `DT_CREATE` datetime DEFAULT NULL,
  `ENABLED` int(11) DEFAULT NULL,
  `LAST_LOGIN` datetime DEFAULT NULL,
  `LOGIN_IP` varchar(100) DEFAULT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  `USER_NAME` varchar(100) NOT NULL,
  `V_QZJGID` varchar(100) DEFAULT NULL,
  `V_QZJGMC` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Structure for table "sys_users_roles"
#

DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE `sys_users_roles` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_21472cobn0iu9xsqn7adjk1l3` (`ROLE_ID`),
  KEY `FK_2esmt02xyysq82hfo1ht0tbs6` (`USER_ID`),
  CONSTRAINT `FK_21472cobn0iu9xsqn7adjk1l3` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_roles` (`ROLE_ID`),
  CONSTRAINT `FK_2esmt02xyysq82hfo1ht0tbs6` FOREIGN KEY (`USER_ID`) REFERENCES `sys_users` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Structure for table "t_schedule_job"
#

DROP TABLE IF EXISTS `t_schedule_job`;
CREATE TABLE `t_schedule_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bean_class` varchar(100) NOT NULL,
  `create_time` datetime NOT NULL,
  `cron_expression` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_concurrent` varchar(1) NOT NULL,
  `job_group` varchar(50) NOT NULL,
  `job_id` bigint(20) NOT NULL,
  `job_name` varchar(50) NOT NULL,
  `job_status` varchar(1) NOT NULL,
  `method_name` varchar(100) NOT NULL,
  `result_desc` varchar(255) DEFAULT NULL,
  `run_time` datetime DEFAULT NULL,
  `spring_id` varchar(100) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

#
# Structure for table "uddi_alpha_batch"
#

DROP TABLE IF EXISTS `uddi_alpha_batch`;
CREATE TABLE `uddi_alpha_batch` (
  `batch_id` int(11) NOT NULL AUTO_INCREMENT,
  `PROGRAM` varchar(64) DEFAULT NULL COMMENT '//¶ÔÓ¦COBOLµÄ³ÌÐòµÄÃû³Æ£¬·ÅÔÚEXE$XOÄ¿Â¼ÏÂ',
  `SYSTEMID` decimal(8,0) DEFAULT NULL COMMENT '//¶ÔÓ¦µÄÏµÍ³Ãû',
  `PARAMETER` varchar(512) DEFAULT NULL COMMENT '//²ÎÊý',
  `INTERFACE` varchar(64) DEFAULT NULL COMMENT '//½Ó¿Ú³ÌÐò',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '//²úÉúÊ±¼ä',
  `PROCESS_DATE` datetime DEFAULT NULL COMMENT '//´¦ÀíÍê³ÉÊ±¼ä',
  `STATUS` decimal(1,0) DEFAULT NULL COMMENT '//×´Ì¬£¬0±íÊ¾ÐÂ½¨(Î´´¦Àí)£¬1±íÊ¾³É¹¦£¬2±íÊ¾Ê§°Ü£¬3±íÊ¾ÖØÐÂ´¦Àí',
  `PROCESS_RESULT` varchar(64) DEFAULT NULL COMMENT '//´¦Àí½á¹û',
  PRIMARY KEY (`batch_id`),
  KEY `F_UDDI_ALPHA_BATCH` (`SYSTEMID`),
  CONSTRAINT `F_UDDI_ALPHA_BATCH` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ALPHAÊý¾Ýµ¼Èëµ½ÏàÓ¦ÏµÍ³µÄ¹Ü¿Ø±í';

#
# Structure for table "uddi_alpha_ism"
#

DROP TABLE IF EXISTS `uddi_alpha_ism`;
CREATE TABLE `uddi_alpha_ism` (
  `ismid` int(11) NOT NULL AUTO_INCREMENT,
  `SYSTEMID` decimal(8,0) DEFAULT NULL COMMENT '//ËùÊôÏµÍ³',
  `NAME` varchar(64) DEFAULT NULL COMMENT '//»ØÐ´µÄµµ°¸Ãû',
  `PROGRAM` varchar(64) DEFAULT NULL COMMENT '//¶ÔÕÕµÄCOBOL³ÌÐò',
  `STATUS` decimal(1,0) DEFAULT NULL COMMENT '//»ØÐ´±êÖ¾¡¢1±íÊ¾Òª»ØÐ´£¬0±íÊ¾²»»ØÐ´',
  `WRITE_TYPE` decimal(1,0) DEFAULT NULL COMMENT '//0±íÊ¾Í¬²½»ØÐ´£¬1±íÊ¾Òì³£»ØÐ´(Backgroup Run)',
  `LAST_UPDATE_DATE` datetime DEFAULT NULL COMMENT '//×îºó¸üÐÂÊ±¼ä',
  PRIMARY KEY (`ismid`),
  KEY `U_UDDI_ALPHA_ISM1` (`SYSTEMID`),
  CONSTRAINT `U_UDDI_ALPHA_ISM1` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='»ØÐ´ALPHAµÄµµ°¸±í';

#
# Structure for table "uddi_alpha_task"
#

DROP TABLE IF EXISTS `uddi_alpha_task`;
CREATE TABLE `uddi_alpha_task` (
  `taskid` int(11) NOT NULL AUTO_INCREMENT,
  `ISMID` decimal(8,0) DEFAULT NULL COMMENT '//¶ÔÓ¦µÄµµ°¸Ãû',
  `SYSTEMID` decimal(8,0) DEFAULT NULL COMMENT '//ÏµÍ³ID',
  `OPERATION` decimal(8,0) DEFAULT NULL COMMENT '//²Ù×÷0±íÊ¾ÐÂÔö£¬1±íÊ¾ÐÞ¸Ä£¬2±íÊ¾É¾³ý',
  `PARAMETER` varchar(512) DEFAULT NULL COMMENT '//²ÎÊý',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '//²úÉúÈÕÆÚ',
  `PROCESS_DATE` datetime DEFAULT NULL COMMENT '//±»´¦ÀíÊ±¼ä',
  `STATUS` decimal(8,0) DEFAULT NULL COMMENT '//ÊÇ·ñÓÐÐ§,0±íÊ¾Î´´¦Àí,1±íÊ¾´¦Àí³É¹¦£¬2±íÊ¾´¦ÀíÊ§°Ü',
  `PROCESS_RESULT` varchar(512) DEFAULT NULL COMMENT '//´¦Àí½á¹û',
  PRIMARY KEY (`taskid`),
  KEY `F_UDDI_ALPHA_TASK1` (`ISMID`),
  KEY `F_UDDI_ALPHA_TASK2` (`SYSTEMID`),
  CONSTRAINT `F_UDDI_ALPHA_TASK2` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='ºóÌ¨´¦Àí(background)µÄÐ´ALPHAµÄÊý¾Ý';

#
# Structure for table "uddi_interface_ejb"
#

DROP TABLE IF EXISTS `uddi_interface_ejb`;
CREATE TABLE `uddi_interface_ejb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `SYSTEMID` decimal(8,0) DEFAULT NULL COMMENT '//ËùÊôÏµÍ³',
  `METHOD` varchar(64) DEFAULT NULL COMMENT '//¹«¿ª·½·¨',
  `DESCRIPTOR_INPUT` varchar(64) DEFAULT NULL COMMENT '//ÊäÈëËµÃ÷',
  `DESCRITPOR_OUTPUT` varchar(64) DEFAULT NULL COMMENT '//Êä³öËµÃ÷',
  `REMARK` varchar(128) DEFAULT NULL COMMENT '//ËµÃ÷',
  `INPUT_TYPE` varchar(128) DEFAULT NULL COMMENT '//ÊäÈë²ÎÊýÀà£¬¶à¸öÊäÈë£¬Ôò±ØÐëÊ¹ÓÃ,·Ö¿ª,Èçjava.util.List,String.',
  `OUTPUT_TYPE` varchar(128) DEFAULT NULL COMMENT '//Êä³öÀàÐÍ,Èçjava.util.List',
  `NAME` varchar(64) DEFAULT NULL COMMENT '//Ãû³Æ',
  PRIMARY KEY (`id`),
  KEY `F_UDDI_INTERFACE_EJB_1` (`SYSTEMID`),
  CONSTRAINT `F_UDDI_INTERFACE_EJB_1` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB AUTO_INCREMENT=1007 DEFAULT CHARSET=utf8;

#
# Structure for table "uddi_log"
#

DROP TABLE IF EXISTS `uddi_log`;
CREATE TABLE `uddi_log` (
  `ul_id` int(11) NOT NULL AUTO_INCREMENT,
  `LOG_ID` decimal(8,0) DEFAULT NULL COMMENT '//ÀàÐÍ',
  `LOG_USER` varchar(10) DEFAULT NULL COMMENT '//¹¤ºÅ',
  `LOG_DATE` datetime DEFAULT NULL COMMENT '//ÈÕÆÚ',
  `LOG_CONTENT` varchar(256) DEFAULT NULL COMMENT '//ÄÚÈÝ',
  `LOG_REMARK` varchar(256) DEFAULT NULL COMMENT '//±¸×¢',
  `LOG_IP` varchar(64) DEFAULT NULL COMMENT '//IP',
  PRIMARY KEY (`ul_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='UDDI²Ù×÷¼ÇÂ¼';

#
# Structure for table "uddi_service"
#

DROP TABLE IF EXISTS `uddi_service`;
CREATE TABLE `uddi_service` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(64) DEFAULT NULL COMMENT '//·þÎñÃû³Æ',
  `LINK` varchar(64) DEFAULT NULL COMMENT '//·þÎñµØÖ·',
  `PARAMETER` varchar(64) DEFAULT NULL COMMENT '//·þÎñµÄ²ÎÊý£¬ÒÔ,¸ô¿ª',
  `SYSTEMID` decimal(8,0) DEFAULT NULL COMMENT '//ÏµÍ³ID',
  `REMARK` varchar(256) DEFAULT NULL COMMENT '//±¸×¢',
  PRIMARY KEY (`service_id`),
  KEY `F_UDDI_SERVICE_1` (`SYSTEMID`),
  CONSTRAINT `F_UDDI_SERVICE_1` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='//¾ÉÏµÍ³ËùÌá¹©µÄXML·þÎñ';

#
# Structure for table "uddi_webservices"
#

DROP TABLE IF EXISTS `uddi_webservices`;
CREATE TABLE `uddi_webservices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(64) DEFAULT NULL COMMENT '//·þÎñµÄÃû×Ö',
  `URL` varchar(128) DEFAULT NULL COMMENT '//·þÎñµØÖ·',
  `METHOD` varchar(64) DEFAULT NULL COMMENT '//·½·¨',
  `ARGMENTS` varchar(128) DEFAULT NULL COMMENT '//²ÎÊý,ÒÔ","¸ô¿ª"a,b"',
  `ARGMENTSTYPE` varchar(128) DEFAULT NULL COMMENT '//²ÎÊýµÄÀàÐÍ£¬ÊýÁ¿ÉÏÓëargments¶ÔÓ¦',
  `RETURNTYPE` varchar(64) DEFAULT NULL COMMENT '//·µ»ØÖµµÄÀàÐÍ',
  `ARGMENTSREMARK` varchar(128) DEFAULT NULL COMMENT '//²ÎÊýËµÃ÷',
  `RETURNREMARK` varchar(128) DEFAULT NULL COMMENT '//·µ»ØÖµËµÃ÷',
  `REMARK` varchar(128) DEFAULT NULL COMMENT '//·þÎñËµÃ÷',
  `USESOAP` decimal(1,0) DEFAULT NULL COMMENT '//ÊÇ·ñÊ¹ÓÃSOAP ACTION,0±íÊ¾ÊÇ£¬1±íÊ¾·ñ',
  `SOAPACTIONURI` varchar(64) DEFAULT NULL COMMENT '//SOAP ACTIONµØÖ·',
  `SYSTEMID` decimal(8,0) DEFAULT NULL COMMENT '//ÏµÍ³ID',
  `ENABLELOG` decimal(8,0) DEFAULT NULL COMMENT '//logÀàÐÍ,0±íÊ¾Òª×öLOG,1±íÊ¾Òª²»×ölog',
  `LOGTYPE` decimal(8,0) DEFAULT NULL COMMENT '//2±íÊ¾Redo webservice 3±íÊ¾²»Redo webservice',
  `VALID` decimal(8,0) DEFAULT NULL COMMENT '//0±íÊ¾¿ÉÕý³£Ê¹ÓÃ,1±íÊ¾²»¿ÉÓÃ.',
  PRIMARY KEY (`id`),
  KEY `F_UDDI_WEBSERVICES_1` (`SYSTEMID`),
  CONSTRAINT `F_UDDI_WEBSERVICES_1` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "uddi_webservices_log"
#

DROP TABLE IF EXISTS `uddi_webservices_log`;
CREATE TABLE `uddi_webservices_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `SYSTEMID` decimal(8,0) DEFAULT NULL COMMENT 'ÏµÍ³ID',
  `WEBSERVICE_ID` decimal(8,0) DEFAULT NULL COMMENT '·þÎñID',
  `PARAMETER` varchar(2048) DEFAULT NULL COMMENT '×Ö·û´®»¯µÄ²ÎÊý',
  `PARAMETER_DETAIL` longblob COMMENT 'ÐòÁÐ»¯µÄ²ÎÊý,¹©ÖØÐÂÖ´ÐÐÊ¹ÓÃ',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '²úÉúÊ±¼ä',
  `INVOKE_RESULT` decimal(8,0) DEFAULT NULL COMMENT '0±íÊ¾´ýÖ´ÐÐ,1±íÊ¾Ö´ÐÐ³É¹¦,2±íÊ¾Ö´ÐÐÊ§°Ü',
  `INVOKE_DATE` datetime DEFAULT NULL COMMENT 'Ö´ÐÐÊ±¼ä',
  `INVOKE_REMARK` varchar(1024) DEFAULT NULL COMMENT 'Ö´ÐÐÐÅÏ¢',
  PRIMARY KEY (`id`),
  KEY `INDEX_COMMON_ID` (`INVOKE_RESULT`),
  KEY `F_UDDI_WEBSERVICES_LOG_1` (`SYSTEMID`),
  KEY `F_UDDI_WEBSERVICES_LOG_2` (`WEBSERVICE_ID`),
  CONSTRAINT `F_UDDI_WEBSERVICES_LOG_1` FOREIGN KEY (`SYSTEMID`) REFERENCES `eia_system` (`SYSTEMID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "user_t"
#

DROP TABLE IF EXISTS `user_t`;
CREATE TABLE `user_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `age` int(11) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `roles` varchar(100) NOT NULL,
  `user_name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

#
# Function "getChildLst"
#

DROP FUNCTION IF EXISTS `getChildLst`;
CREATE FUNCTION `getChildLst`(rootId INT) RETURNS varchar(1000) CHARSET utf8
BEGIN
   DECLARE sTemp VARCHAR(1000);
    DECLARE sTempChd VARCHAR(1000);
    
    SET sTemp = '$';
    SET sTempChd =cast(rootId as CHAR);
 
   WHILE sTempChd is not null DO
   SET sTemp = concat(sTemp,',',sTempChd);
     SELECT group_concat(id) INTO sTempChd FROM treeNodes where FIND_IN_SET(pid,sTempChd)>0;
    END WHILE;
   RETURN sTemp;
  END;

#
# Function "getChildOrganise"
#

DROP FUNCTION IF EXISTS `getChildOrganise`;
CREATE FUNCTION `getChildOrganise`(rootId INT) RETURNS varchar(1000) CHARSET utf8
BEGIN
   DECLARE sTemp VARCHAR(1000);
    DECLARE sTempChd VARCHAR(1000);
    
    SET sTemp = '$';
    SET sTempChd =cast(rootId as CHAR);
   IF rootId = 0   THEN
     SELECT group_concat(or_id) INTO sTemp FROM sy_organise where or_parentid>0;
   ELSE
      WHILE sTempChd is not null DO
        SET sTemp = concat(sTemp,',',sTempChd);
        SELECT group_concat(or_id) INTO sTempChd FROM sy_organise where FIND_IN_SET(or_parentid,sTempChd)>0;
      END WHILE;
   END IF ;  
   RETURN sTemp;
  END;

#
# Procedure "getCustomerList"
#

DROP PROCEDURE IF EXISTS `getCustomerList`;
CREATE PROCEDURE `getCustomerList`(IN inuserid bigint,IN startnum bigint,IN num bigint)
    READS SQL DATA
    COMMENT '获取客户列表'
begin

/**procedure body**/

if (startnum is null or startnum='') then
 set startnum = 0;
end if;

if (num is null or num='') then
 set num=30;
end if;

select u.userid,tag.tagid,tag.tagname,om.ordernum,f.friendId as frienduserid,u2.usernick,u2.usernick as username,u2.avatar_img,u2.sex,u2.mobile from ly_users  u  join  sy_friends f 
on (f.userId = u.userid) join ly_users u2 on
(u2.userid=f.friendId) left join  (select group_concat(t.id) as tagid,group_concat(t.name) as tagname,u.userid from ly_users  u join  ly_user_tag f on (f.userid = u.userid) join ly_tag  t 
on (f.tagid=t.id) group by u.userid) tag on (tag.userid=f.friendId) left join 
 (select ifnull(count(o.userid),0) as ordernum,o.userid from  ly_order o  group by o.userid ) om on (om.userid=f.friendId) where u.userid=inuserid limit startnum,num;

end;
