package com.common.security;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidDataSourceEx extends DruidDataSource {

	private static final long serialVersionUID = 1L;

	public void setPassword(String password) {
		//暂时不用解密
		//EncryptTool tool = new EncryptTool();
		//password = tool.decrypt(password);
		super.setPassword(password);
	}

}
