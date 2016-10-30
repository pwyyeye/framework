package com.xxl.os.dao;

import java.util.List;

import common.dao.BaseDAO;
import common.os.vo.exception.OSException;
import common.value.PageList;


public interface OrganiseDAO extends BaseDAO{
	public List getSubOrganises(final int organise)throws OSException;
	public PageList getSubOrganiseList(final int organise) throws OSException ;
}

