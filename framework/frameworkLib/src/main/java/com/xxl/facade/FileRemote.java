package com.xxl.facade;  

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import common.exception.BaseBusinessException;
import common.exception.BaseException;
  
public interface FileRemote {  
	public String getUploadFileTokenForWeb();
    
	public String addQiNiuFile(List<MultipartFile> Fileitems) throws BaseException, BaseBusinessException ;
}