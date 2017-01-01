package com.xxl.baseService.bo;

import common.businessObject.BaseBusinessObject;
import common.value.ReportModuleVO;

public class ReportModule extends BaseBusinessObject {
	final static int KEY_NEED_FLAG = 1;

	private String name;

	private String report;

	private ItModule module;

	private Integer needDatekey;

	private Integer needLine;

	private Integer needSeries;

	private Integer needModel;

	private Integer needWorkshop;

	private Integer dateKeyType;

	private Integer needLog;

	private Integer needColor;

	private Integer needControlPoint;

	private Integer needDepartment;

	private Integer needTimekey;

	private String otherDatekeyMode;

	private String timekeyMode;

	private String customKey;

	private String remark;

	private Integer parent;

	private Integer sortID;

	private Integer valid;

	private Integer oldVersion;

	private Integer parameterModule;
	
	private String javascript;
	private String submitScript;
	private Integer exportType;

	public Integer getExportType() {
		return exportType;
	}

	public void setExportType(Integer exportType) {
		this.exportType = exportType;
	}

	public Integer getParameterModule() {
		return parameterModule;
	}

	public void setParameterModule(Integer parameterModule) {
		this.parameterModule = parameterModule;
	}

	public Integer getOldVersion() {
		return oldVersion;
	}

	public void setOldVersion(Integer oldVersion) {
		this.oldVersion = oldVersion;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getSortID() {
		return sortID;
	}

	public void setSortID(Integer sortID) {
		this.sortID = sortID;
	}

	public Integer getNeedColor() {
		return needColor;
	}

	public void setNeedColor(Integer needColor) {
		this.needColor = needColor;
	}

	public Integer getNeedLog() {
		return needLog;
	}

	public void setNeedLog(Integer needLog) {
		this.needLog = needLog;
	}

	public ItModule getModule() {
		return module;
	}

	public void setModule(ItModule module) {
		this.module = module;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNeedDatekey() {
		return needDatekey;
	}

	public void setNeedDatekey(Integer needDatekey) {
		this.needDatekey = needDatekey;
	}

	public Integer getNeedLine() {
		return needLine;
	}

	public void setNeedLine(Integer needLine) {
		this.needLine = needLine;
	}

	public Integer getNeedSeries() {
		return needSeries;
	}

	public void setNeedSeries(Integer needSeries) {
		this.needSeries = needSeries;
	}

	public Integer getNeedWorkshop() {
		return needWorkshop;
	}

	public void setNeedWorkshop(Integer needWorkshop) {
		this.needWorkshop = needWorkshop;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	private boolean isNeed(Integer obj) {
		return obj != null && obj.intValue() == this.KEY_NEED_FLAG;
	}

	public boolean isDateKeyNeed() {
		return isNeed(this.needDatekey);
	}

	public boolean isLineNeed() {
		return isNeed(this.needLine);
	}

	public boolean isSeriesNeed() {
		return isNeed(this.needSeries);
	}

	public boolean isWorkshopNeed() {
		return isNeed(this.needWorkshop);
	}

	public Object toVO() {
		ReportModuleVO vo = new ReportModuleVO((Integer)getId(), name, report, module
				.getName(), module.getId(), needDatekey, needLine, needModel,
				needSeries, needWorkshop, needLog, dateKeyType, needColor,
				needTimekey, otherDatekeyMode, timekeyMode, customKey, remark,
				parent, needControlPoint, sortID, valid, needDepartment,
				oldVersion, parameterModule,javascript,submitScript,exportType);

		return vo;
	}

	private boolean isModelNeed() {
		return isNeed(this.needModel);
	}

	public Integer getNeedModel() {
		return needModel;
	}

	public void setNeedModel(Integer needModel) {
		this.needModel = needModel;
	}

	public Integer getDateKeyType() {
		return dateKeyType;
	}

	public void setDateKeyType(Integer dateKeyType) {
		this.dateKeyType = dateKeyType;
	}

	public String getCustomKey() {
		return customKey;
	}

	public void setCustomKey(String customKey) {
		this.customKey = customKey;
	}

	public Integer getNeedTimekey() {
		return needTimekey;
	}

	public void setNeedTimekey(Integer needTimekey) {
		this.needTimekey = needTimekey;
	}

	public String getOtherDatekeyMode() {
		return otherDatekeyMode;
	}

	public void setOtherDatekeyMode(String otherDatekeyMode) {
		this.otherDatekeyMode = otherDatekeyMode;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTimekeyMode() {
		return timekeyMode;
	}

	public void setTimekeyMode(String timekeyMode) {
		this.timekeyMode = timekeyMode;
	}

	public Integer getNeedControlPoint() {
		return needControlPoint;
	}

	public void setNeedControlPoint(Integer needControlPoint) {
		this.needControlPoint = needControlPoint;
	}

	public boolean isControlPointNeed() {
		return isNeed(this.needControlPoint);
	}

	public Integer getNeedDepartment() {
		return needDepartment;
	}

	public void setNeedDepartment(Integer needDepartment) {
		this.needDepartment = needDepartment;
	}

	public String getJavascript() {
		return javascript;
	}

	public void setJavascript(String javascript) {
		this.javascript = javascript;
	}

	public String getSubmitScript() {
		return submitScript;
	}

	public void setSubmitScript(String submitScript) {
		this.submitScript = submitScript;
	}

}
