package common.bussiness;

import java.util.Calendar;

import common.utils.SemAppUtils;

public class CommonLogger {
  private Integer empID;
  private Calendar date;
  private String remark;
  private String type;
  private String content;
  private String ip;
  public CommonLogger(Integer empID,String type,String remark,String content,String ip) {
    this.empID=empID;
    this.type=type;
    this.remark=remark;
    this.content=content;
    date=Calendar.getInstance();
    this.ip=ip;
  }
  public Integer getEmpID() {
    return empID;
  }
  public void setEmpID(Integer empID) {
    this.empID = empID;
  }
  public Calendar getDate() {
    return date;
  }
  public void setDate(Calendar date) {
    this.date = date;
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public String getRemark() {
    return remark;
  }
  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String toString() {
    return "'"+empID + "','" + SemAppUtils.getStdDate(date) + " " +
    SemAppUtils.getStdTime(date) + "','" + type + "','" + content + "','" +
        remark  + "','" + ip+ "'";
  }
  public String getIp() {
    return ip;
  }
  public void setIp(String ip) {
    this.ip = ip;
  }

}
