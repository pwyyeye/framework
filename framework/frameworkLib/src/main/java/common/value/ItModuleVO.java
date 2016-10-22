package common.value;

import java.util.Calendar;

public class ItModuleVO extends BaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private int status;

	private String rowid;

	private Calendar createDate;

	private int parentModule;

	private String indexPage;

	private int sortID;

	private int messageID;

	private String serviceJndi;

	private String deployServer;

	private String serviceHome;

	private String serviceRemote;
	
	private Integer dirID;

	public String getServiceHome() {
		return serviceHome;
	}

	public void setServiceHome(String serviceHome) {
		this.serviceHome = serviceHome;
	}

	public String getServiceRemote() {
		return serviceRemote;
	}

	public void setServiceRemote(String serviceRemote) {
		this.serviceRemote = serviceRemote;
	}

	public String getDeployServer() {
		return deployServer;
	}

	public void setDeployServer(String deployServer) {
		this.deployServer = deployServer;
	}

	public int getSortID() {
		return sortID;
	}

	public void setSortID(int sortID) {
		this.sortID = sortID;
	}

	public Calendar getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	public String getIndexPage() {
		return indexPage;
	}

	public void setIndexPage(String indexPage) {
		this.indexPage = indexPage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentModule() {
		return parentModule;
	}

	public void setParentModule(int parentModule) {
		this.parentModule = parentModule;
	}

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ItModuleVO(Integer id, String name, int status, String rowid,
			Calendar createDate, int parentModule, String indexPage) {
		super(id);
		this.name = name;
		this.status = status;
		this.rowid = rowid;
		this.createDate = createDate;
		this.parentModule = parentModule;
		this.indexPage = indexPage;
	}

	public ItModuleVO(Integer id, String name, int status, String rowid,
			Calendar createDate, int parentModule, String indexPage,
			int sortID, int messageID, String serviceJndi, String deployServer,
			String serviceHome, String serviceRemote,Integer dirID) {
		this(id, name, status, rowid, createDate, parentModule, indexPage);
		this.sortID = sortID;
		this.messageID = messageID;
		this.serviceJndi = serviceJndi;
		this.deployServer = deployServer;
		this.serviceHome = serviceHome;
		this.serviceRemote = serviceRemote;
		this.dirID=dirID;
	}

	public ItModuleVO() {
		super();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("Module[name: " + name);
		str.append("]");
		return str.toString();
	}

	public int getMessageID() {
		return messageID;
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}

	public String getText() {
		return name;
	}

	public int getParentId() {
		return parentModule;
	}


	public int get_parent() {
		return parentModule;
	}

	public String getServiceJndi() {
		return serviceJndi;
	}

	public void setServiceJndi(String serviceJndi) {
		this.serviceJndi = serviceJndi;
	}

	public Integer getDirID() {
		return dirID;
	}

	public void setDirID(Integer dirID) {
		this.dirID = dirID;
	}

}
