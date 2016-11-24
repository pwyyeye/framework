package com.xxl.temp.bo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import common.businessObject.BaseBusinessObject;
import common.temp.vo.TempOrderVo;
import common.utils.SemAppUtils;

/**
 * 
 * @Description: 临时订单表
 * @author snailxr
 * @date 2014年4月24日 下午10:49:43
 */
@Entity
@Table(name = "temp_order")
public class TempOrder extends BaseBusinessObject {

	private static final long serialVersionUID = 4845111383294255204L;
	
	private Integer id;


	private Calendar createdate;
	
	private String name;
	
	private String address;
	
	private String tel;
	
	private String color;
	
	private String num;
	
	private String remark;
	
	private Double amount;
	
	private String size;

	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, length = 11)
    public Integer getId() {
        return id;
    }
	
	public void setId(Integer id) {
		this.id = id;
	}

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdate", nullable = false)
	public Calendar getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Calendar createdate) {
		this.createdate = createdate;
	}
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "tel")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	@Column(name = "color")
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	@Column(name = "num")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "amount")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Object toVO() {
		TempOrderVo vo = new TempOrderVo();
		SemAppUtils.BO2VO(this, vo);
		return vo;
	}

}