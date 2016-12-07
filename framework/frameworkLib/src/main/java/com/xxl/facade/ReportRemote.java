package com.xxl.facade;

import java.rmi.RemoteException;
import java.util.List;


import common.exception.CommonException;
import common.value.PageList;
import common.value.ReportModuleVO;
import common.value.ReportScheduleVO;

public interface ReportRemote {
	public PageList getReportModuleList(Integer systemID,
			ReportModuleVO searchVO, Integer firstResult, Integer fetchSize)
			throws CommonException, RemoteException;

	public PageList getReportModuleList(Integer systemID)
			throws CommonException, RemoteException;

	public PageList getReportModuleList() throws CommonException,
			RemoteException;

	public void updateReportModule(ReportModuleVO vo) throws CommonException,
			RemoteException;

	public void deleteReportModule(Integer id) throws CommonException,
			RemoteException;

	public Integer addReportModule(ReportModuleVO vo) throws CommonException,
			RemoteException;

	public ReportModuleVO getReportModuleByID(Integer reportModuleID)
			throws CommonException, RemoteException;

	public List getReportModuleList(Integer moduleID, Integer root)
			throws CommonException, RemoteException;

	public void closeReportModule(Integer oper) throws CommonException,
			RemoteException;

	public void scheduleReport() throws CommonException, RemoteException;

	public void scheduleOneReport(Integer reportScheduleID)
			throws CommonException, RemoteException;

	public void scheduleOneReport(Integer reportScheduleID,boolean refreshNextRunningTime)
			throws CommonException, RemoteException;


	public PageList getReportScheduleList(Integer systemID,
			ReportScheduleVO searchVO, Integer firstResult, Integer fetchSize)
			throws CommonException, RemoteException;

	public PageList getReportScheduleList(Integer systemID)
			throws CommonException, RemoteException;

	public PageList getReportScheduleList() throws CommonException,
			RemoteException;

	public ReportModuleVO getReportScheduleByID(Integer reportScheduleID)
			throws CommonException, RemoteException;

	public void updateScheduleModule(ReportScheduleVO vo)
			throws CommonException, RemoteException;

	public void deleteReportSchedule(Integer id) throws CommonException,
			RemoteException;

	public Integer addReportSchedule(ReportScheduleVO vo)
			throws CommonException, RemoteException;
}
