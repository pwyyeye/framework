package common.filter;

import common.os.vo.UsersVO;



public interface CheckAccessInterface {
  public boolean performCheck(UsersVO user,String  actionName);
}
