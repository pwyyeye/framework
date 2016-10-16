package com.xxl.baseService.bo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import com.xxl.baseService.vo.UserVo;

import common.businessObject.BaseBusinessObject;
import common.utils.SemAppUtils;

@Entity
@Table(name = "user_t")
public class User extends BaseBusinessObject {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String userName;

    private String password;

    private Integer age;
    
    private String roles;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, length = 11)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "user_name", unique = false, nullable = false, insertable = true, updatable = true, length = 40)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    @Column(name = "password", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    @Column(name = "age", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "roles", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Override
	public Object toVO() {
		UserVo vo = new UserVo();
		SemAppUtils.BO2VO(this, vo);
		return vo;
	}
}