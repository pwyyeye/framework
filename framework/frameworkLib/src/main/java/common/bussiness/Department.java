package common.bussiness;

import java.io.Serializable;

public class Department implements Serializable{
  private String name;
  private String id;
  private String leadID;
  private String level;
  private User leader;
  private Integer parentID;
  public Department() {
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getLeadID() {
    return leadID;
  }
  public void setLeadID(String leadID) {
    this.leadID = leadID;
  }
  public String getLevel() {
    return level;
  }
  public void setLevel(String level) {
    this.level = level;
  }
  public User getLeader() {
    return leader;
  }
  public void setLeader(User leader) {
    this.leader = leader;
  }
  public Integer getParentID() {
    return parentID;
  }
  public void setParentID(Integer parentID) {
    this.parentID = parentID;
  }
}
