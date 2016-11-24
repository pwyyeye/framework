package com.xxl.facade;  

import common.exception.BaseException;
import common.temp.vo.TempOrderVo;
import common.value.PageList;
  
public interface TempRemote {  
	public TempOrderVo getTempOrder(Integer id);
	
	public Integer addTempOrder(TempOrderVo vo) throws BaseException;
	
	public PageList getTempOrderList(TempOrderVo searchVO, 
			Integer firstResult, Integer fetchSize) throws BaseException ;
}