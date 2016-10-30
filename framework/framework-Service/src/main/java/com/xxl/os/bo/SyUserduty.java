package com.xxl.os.bo;
/*
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 *
 * Created Mon Dec 15 12:11:20 CST 2014 by MyEclipse Hibernate Tool.
 */


import java.io.Serializable;

import common.os.vo.UserdutyVO;
import common.utils.SemAppUtils;


public  class SyUserduty 
    extends common.businessObject.BaseBusinessObject
    implements Serializable
{
   
    private int hashValue = 0;


    public SyUserduty()
    {
    }

  
    public SyUserduty(Integer id)
    {
        this.setId(id);
    }

 
  
    public boolean equals(Object rhs)
    {
        if (rhs == null)
            return false;
        if (! (rhs instanceof SyUserduty))
            return false;
        SyUserduty that = (SyUserduty) rhs;
        if (this.getId() == null || that.getId() == null)
            return false;
        return (this.getId().equals(that.getId()));
    }

    /**
     * Implementation of the hashCode method conforming to the Bloch pattern with
     * the exception of array properties (these are very unlikely primary key types).
     * @return int
     */
    public int hashCode()
    {
        if (this.hashValue == 0)
        {
            int result = 17;
            if (this.getId() == null)
            {
                result = super.hashCode();
            }
            else
            {
                result = this.getId().hashCode();
            }
            this.hashValue = result;
        }
        return this.hashValue;
    }

	public Object toVO() {
		UserdutyVO vo=new UserdutyVO(getId());
		SemAppUtils.beanCopy(this, vo);
		return vo;
	}
}