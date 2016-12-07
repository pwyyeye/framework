package common.filter;

import common.bussiness.User;
import common.os.vo.UsersVO;



public interface SessionUserInterface {
	public User getCommonUser();
	public UsersVO getCommonUsersVO(); 

}
