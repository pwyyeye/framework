package com.xxl.hnust.bo;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.xxl.hnust.vo.PersistentLoginsVo;
import common.businessObject.BaseBusinessObject;
import common.utils.SemAppUtils;

/**
 * Spring Remember me 持久化
 * @author karys
 *
 */
@Entity
@Table(name = "PERSISTENT_LOGINS")
public class PersistentLogins extends BaseBusinessObject {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7101179124697491335L;
	   
	private String series;

    private String userName;

    private String token;

    private Calendar last_used;

    @Id
	@Column(name = "SERIES", unique = true, nullable = false, length = 64)
    public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	@Column(name = "USER_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }
    
    @Column(name = "TOKEN", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_USED", nullable = true)
    public Calendar getLast_used() {
		return last_used;
	}

	public void setLast_used(Calendar last_used) {
		this.last_used = last_used;
	}

	@Override
	public Object toVO() {
		PersistentLoginsVo vo = new PersistentLoginsVo();
		SemAppUtils.BO2VO(this, vo);
		return vo;
	}
	
}