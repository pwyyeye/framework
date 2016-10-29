package common.value;

public class EventVO extends BaseVO{
    private String module;
    private Integer moduleID;
    private String name;
    private int type;
    private int status;
    private String typeName;
    
    public String getTypeName(){
    	return type==1?"定时触发":"用户触发";
    }
    
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public EventVO(){
		super();
	}
	public EventVO(Integer id,String module, String name, int type, int status,Integer moduleID) {
		super(id);
		this.module = module;
		this.name = name;
		this.type = type;
		this.status = status;
		this.moduleID=moduleID;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	public String toString() {
	    StringBuffer str = new StringBuffer();
	    str.append("Event[name: "+name);
	    str.append(" | module: "+module);
	    str.append(" | type: "+ type);
	    str.append("]");
	    return str.toString();
	}
	public Integer getModuleID() {
		return moduleID;
	}
	public void setModuleID(Integer moduleID) {
		this.moduleID = moduleID;
	}

}
