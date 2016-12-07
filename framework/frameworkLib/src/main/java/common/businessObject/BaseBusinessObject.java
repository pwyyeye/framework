package common.businessObject;

/**
 * An abstract super class that many business objects will extend.
 */
public abstract class BaseBusinessObject extends BaseAbstractBo {
	private static final long serialVersionUID = 1L;
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public void setId(java.io.Serializable id){
		this.id=(Integer)id;
	}

}
