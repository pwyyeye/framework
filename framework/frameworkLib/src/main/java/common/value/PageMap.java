package common.value;


import java.io.Serializable;
import java.util.Map;
/**
 * 分页专用的Map，用于存放查询记录总数、该页返回的记录集
 * @author curry
 *
 */

public class PageMap 	implements Serializable{
	private static final long serialVersionUID = 1000002L;
	private long results;//查询记录总数
	private Map items;//返回的记录集
	public Map getItems() {
		return items;
	}
	public void setItems(Map items) {
		this.items = items;
	}
	public long getResults() {
		return results;
	}
	public void setResults(long results) {
		this.results = results;
	}
	
	
	

}
