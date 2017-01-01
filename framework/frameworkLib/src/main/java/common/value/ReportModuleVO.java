package common.value;

public class ReportModuleVO extends BaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 100;

	final static int KEY_NEED_FLAG = 1;

	private String name;

	private String report;

	private String module;

	private Integer moduleID;

	private Integer needDateKey;

	private Integer needLine;

	private Integer needModel;

	private Integer needSeries;

	private Integer needWorkshop;

	private Integer needDepartment;

	private Integer needLog;

	private Integer dateKeyType;

	private Integer needColor;

	private Integer needControlPoint;

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

	public String getText() {
		return name;
	}

	public Integer getSortID() {
		return sortID;
	}

	public void setSortID(Integer sortID) {
		this.sortID = sortID;
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

	public Integer getNeedColor() {
		return needColor;
	}

	public void setNeedColor(Integer needColor) {
		this.needColor = needColor;
	}

	public Integer getDateKeyType() {
		return dateKeyType;
	}

	public void setDateKeyType(Integer dateKeyType) {
		this.dateKeyType = dateKeyType;
	}

	public ReportModuleVO(Integer id, String name, String report,
			String module, Integer moduleID, Integer needDateKey,
			Integer needLine, Integer needModel, Integer needSeries,
			Integer needWorkshop, Integer needLog, Integer dateKeyType,
			Integer needColor, Integer needTimekey, String otherDatekeyMode,
			String timekeyMode, String customKey, String remark,
			Integer parent, Integer needControlePoint, Integer sortID,
			Integer valid, Integer needDepartment, Integer oldVersion,
			Integer parameterModule,String javascript,String submitScript,Integer exportType) {
		super(id);
		this.name = name;
		this.report = report;
		this.module = module;
		this.moduleID = moduleID;
		this.needDateKey = needDateKey;
		this.needLine = needLine;
		this.needModel = needModel;
		this.needSeries = needSeries;
		this.needWorkshop = needWorkshop;
		this.needLog = needLog;
		this.dateKeyType = dateKeyType;
		this.needColor = needColor;
		this.needTimekey = needTimekey;
		this.otherDatekeyMode = otherDatekeyMode;
		this.needControlPoint = needControlePoint;
		this.timekeyMode = timekeyMode;
		this.customKey = customKey;
		this.remark = remark;
		this.parent = parent;
		this.sortID = sortID;
		this.javascript=javascript;
		this.submitScript=submitScript;
		this.valid = valid;
		this.needDepartment = needDepartment;
		this.oldVersion = oldVersion;
		this.parameterModule = parameterModule;
		this.javascript=javascript;
		this.submitScript=submitScript;
		this.exportType=exportType;
	}

	public ReportModuleVO() {
		super();
	}

	public ReportModuleVO(Integer id) {
		super(id);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("ReportModule[name: " + name);
		str.append(" | module: " + module);
		str.append(" | report: " + report);
		str.append("]");
		return str.toString();
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Integer getModuleID() {
		return moduleID;
	}

	public void setModuleID(Integer moduleID) {
		this.moduleID = moduleID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public Integer getNeedDateKey() {
		return needDateKey;
	}

	public void setNeedDateKey(Integer needDateKey) {
		this.needDateKey = needDateKey;
	}

	public Integer getNeedLine() {
		return needLine;
	}

	public void setNeedLine(Integer needLine) {
		this.needLine = needLine;
	}

	public Integer getNeedModel() {
		return needModel;
	}

	public void setNeedModel(Integer needModel) {
		this.needModel = needModel;
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

	private boolean isNeed(Integer obj) {
		return obj != null && obj.intValue() == this.KEY_NEED_FLAG;
	}

	public boolean isDateKeyNeed() {
		return needDateKey != null && needDateKey.intValue() > 0;
	}

	public boolean isLineNeed() {
		return isNeed(this.needLine);
	}

	public boolean isModelNeed() {
		return isNeed(this.needModel);
	}

	public boolean isTimekeyNeed() {
		return isNeed(this.needTimekey);
	}

	public boolean isWorkshopNeed() {
		return isNeed(this.needWorkshop);
	}

	public boolean isLogFlagNeed() {
		return isNeed(this.needLog);
	}

	public boolean isSeriesNeed() {
		return isNeed(this.needSeries);
	}

	public boolean isColorNeed() {
		return isNeed(needColor);
	}

	public boolean isControlPointNeed() {
		return isNeed(this.needControlPoint);
	}

	public boolean isDepartmentNeed() {
		return isNeed(this.needDepartment);
	}

	private String getNeedName(Integer obj) {
		return isNeed(obj) ? "是" : "否";
	}

	public String getValidName() {
		return valid.intValue() == 0 ? "打开" : "关闭";
	}

	public String getOldVersionName() {
		return oldVersion.intValue() == 0 ? "新版本" : "旧版本";
	}

	public boolean isDateRange() {
		return isNeed(this.dateKeyType);
	}

	public String getNeedDateKeyName() {
		int type = needDateKey == null ? 0 : needDateKey.intValue();
		switch (type) {
		default:
			return "   ";
		case 1:
			return "yyyy-MM-dd";
		case 2:
			return "yyyy-MM";
		case 3:
			return "yyyy";
		case 4:
			return "yyyy-E";
		case 5:
			return "yyyy-Q";
		case 6:
			return "yyyy-MM-dd HH";
		case 7:
			return "yyyy-MM-dd HH:mm";
		case 8:
			return "yyyy-MM-dd HH:mm:ss";
		case 9:
			return "yyyyMM";
		case 10:
			return "yyyyMMdd";
		}
	}
	public String getNeedDateKeyStr() {
		int type = needDateKey == null ? 0 : needDateKey.intValue();
		switch (type) {
		default:
			return "   ";
		case 1:
			return "Y-m-d";
		case 2:
			return "Y-m";
		case 3:
			return "Y";
		case 4:
			return "Y-W";
		case 5:
			return "Y-Q";
		case 6:
			return "Y-m-d H";
		case 7:
			return "Y-m-d H:i";
		case 8:
			return "Y-m-d H:i:s";
		case 9:
			return "Ym";
		case 10:
			return "Ymd";
		}
	}

	public String getDateKeyTypeStr() {
		return isNeed(this.getDateKeyType()) ? "日期范围型" : "正常日期型";
	}
	
	public String reportTypeStr(){
		if(exportType==null) return "";
		switch(exportType.intValue()){
		default:
			return "输出HTML";
		case 1:
			return "输出EXCEL";
		case 2:
			return "输出PDF";
		case 3:
			return "输出WORD";
		case 4:
			return "输出TXT";
		}
	}

	public String getNeedLineStr() {
		return getNeedName(this.needLine);
	}

	public String getNeedColorStr() {
		return getNeedName(needColor);
	}

	public String getDepartmentStr() {
		return getNeedName(needDepartment);
	}

	public String getNeedControlPointStr() {
		return getNeedName(needControlPoint);
	}

	public String getNeedModelStr() {
		return getNeedName(this.needModel);
	}

	public String getNeedWorkshopStr() {
		return getNeedName(this.needWorkshop);
	}

	public String getNeedSeriesStr() {
		return getNeedName(this.needSeries);
	}

	public String getNeedLogStr() {
		return getNeedName(this.needLog);
	}

	public String getNeedTimekeyStr() {
		return getNeedName(this.needTimekey);
	}

	public Integer getNeedLog() {
		return needLog;
	}

	public void setNeedLog(Integer needLog) {
		this.needLog = needLog;
	}

	public boolean isNeedSelector() {
		return isDateKeyNeed() || isLineNeed() || isModelNeed()
				|| isSeriesNeed() || isWorkshopNeed() || isControlPointNeed()
				|| this.isColorNeed() || this.isDepartmentNeed()
				|| this.isTimekeyNeed();
	}

	public boolean isOldReportVersion() {
		return oldVersion.intValue() == 1;
	}

	public boolean isNewParameterModule() {
		return parameterModule.intValue() == 1;
	}

	public Integer getNeedControlPoint() {
		return needControlPoint;
	}

	public void setNeedControlPoint(Integer needControlPoint) {
		this.needControlPoint = needControlPoint;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getNeedDepartment() {
		return needDepartment;
	}

	public void setNeedDepartment(Integer needDepartment) {
		this.needDepartment = needDepartment;
	}

}
