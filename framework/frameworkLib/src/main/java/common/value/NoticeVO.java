package common.value;

import java.util.Calendar;

public class NoticeVO extends BaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String module;

	private Integer moduleID;

	private String subject;

	private Integer valid;

	private String content;

	private Calendar startDate;

	private Calendar endDate;
	
	private Integer attach;
	private String attachName;

	public Integer getAttach() {
		return attach;
	}

	public void setAttach(Integer attach) {
		this.attach = attach;
	}

	public NoticeVO(Integer id, String module, Integer moduleID,
			String subject, Integer valid, String content, Calendar startDate,
			Calendar endDate,Integer attach,String attachName) {
		super(id);
		this.module = module;
		this.moduleID = moduleID;
		this.subject = subject;
		this.valid = valid;
		this.content = content;
		this.startDate = startDate;
		this.endDate = endDate;
		this.attach=attach;
		this.attachName=attachName;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public NoticeVO() {
		super();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("Event[name: " + subject);
		str.append(" | module: " + module);
		str.append("]");
		return str.toString();
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getModuleID() {
		return moduleID;
	}

	public void setModuleID(Integer moduleID) {
		this.moduleID = moduleID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getValidStr(){
		return (valid==null||valid.intValue()==0)?"正常公告":"重要公告";
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

}
