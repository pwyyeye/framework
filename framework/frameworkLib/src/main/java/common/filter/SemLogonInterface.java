package common.filter;


import javax.servlet.http.HttpServletRequest;

import common.os.vo.UsersVO;

public interface SemLogonInterface {
  public boolean performLogon(UsersVO user,HttpServletRequest request);
}
