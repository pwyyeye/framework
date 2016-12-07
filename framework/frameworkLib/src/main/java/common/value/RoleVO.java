package common.value;

public class RoleVO extends BaseVO{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String module;
    private Integer moduleID;
    private Integer organise;
    private String organiseName;
    public Integer getOrganise() {
		return organise;
	}
	public void setOrganise(Integer organise) {
		this.organise = organise;
	}
	public String getOrganiseName() {
		return organiseName;
	}
	public void setOrganiseName(String organiseName) {
		this.organiseName = organiseName;
	}
	private String name;
    private Integer valid;
    private String description;
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
	
	public RoleVO(Integer id,String module, String name, Integer valid,String description,Integer moduleID) {
		super(id);
		this.module = module;
		this.name = name;
		this.valid = valid;
		this.description=description;
		this.moduleID=moduleID;
	}
	public RoleVO() {
		super();
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
	    str.append("]");
	    return str.toString();
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
}
