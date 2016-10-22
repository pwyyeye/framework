package common.value;

public class MenuVO extends BaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String module;

	private Integer moduleID;

	private String name;

	private String frame;

	private int parentModule;

	private int sortID;

	private String link;
	
	private int singleMode;
	
	private boolean leaf;

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public int getSingleMode() {
		return singleMode;
	}

	public void setSingleMode(int singleMode) {
		this.singleMode = singleMode;
	}

	public String getFrame() {
		return frame;
	}

	public void setFrame(String frame) {
		this.frame = frame;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public int getParentModule() {
		return parentModule;
	}

	public void setParentModule(int parentModule) {
		this.parentModule = parentModule;
	}

	public int getSortID() {
		return sortID;
	}

	public void setSortID(int sortID) {
		this.sortID = sortID;
	}

	public String getText() {
		return name;
	}

	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("MenuVO[name: " + name);
		str.append(" | module: " + module);
		str.append("]");
		return str.toString();
	}

	public MenuVO(Integer id) {
		super(id);
	}

	public MenuVO(Integer id, String module, Integer moduleID, String name,
			String frame, int parentModule, int sortID, String link,int singleMode) {
		super(id);
		this.module = module;
		this.moduleID = moduleID;
		this.name = name;
		this.frame = frame;
		this.parentModule = parentModule;
		this.sortID = sortID;
		this.link = link;
		this.singleMode=singleMode;
	}

}
