package common.businessObject;

import java.io.Serializable;
import java.util.*;

import common.value.ItModuleVO;

public class ItModule implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;


	private String name;

	private int status;

	private String rowid;

	private Calendar createDate;

	private int parentModule;

	private Calendar lastModifyDate;

	private String lastModifyUser;

	private String indexPage;

	private int sortID;

	private int messageID;

	private String serviceJndi;

	private String deployServer;

	private String serviceHome;

	private String serviceRemote;
	
	private Integer dirID;

	public Integer getDirID() {
		return dirID;
	}

	public void setDirID(Integer dirID) {
		this.dirID = dirID;
	}

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

	public String getServiceJndi() {
		return serviceJndi;
	}

	public void setServiceJndi(String serviceJndi) {
		this.serviceJndi = serviceJndi;
	}

	public int getMessageID() {
		return messageID;
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}

	public int getSortID() {
		return sortID;
	}

	public void setSortID(int sortID) {
		this.sortID = sortID;
	}

	public String getIndexPage() {
		return indexPage;
	}

	public void setIndexPage(String indexPage) {
		this.indexPage = indexPage;
	}

	public Calendar getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	public Calendar getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Calendar lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public String getLastModifyUser() {
		return lastModifyUser;
	}

	public void setLastModifyUser(String lastModifyUser) {
		this.lastModifyUser = lastModifyUser;
	}

	public int getParentModule() {
		return parentModule;
	}

	public void setParentModule(int parentModule) {
		this.parentModule = parentModule;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String toString() {
		return name;
	}
	


	public Integer getId() {
		return id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Object toVO() {
		ItModuleVO vo = new ItModuleVO(id, name, status, rowid,
				createDate, parentModule, indexPage, sortID, messageID,
				serviceJndi, deployServer, serviceHome, serviceRemote,dirID);
		return vo;
	}

}
