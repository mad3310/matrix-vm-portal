package com.letv.portal.model.order;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.letv.common.model.BaseModel;


/**
 * 订单实体
 * @author lisuxiao
 *
 */
@Alias("Order")
public class Order extends BaseModel{

	private static final long serialVersionUID = 6612436637375760981L;

	private String orderNumber;//订单编码
	private String descn;//描述
	private Integer status;//订单状态：0-未付款，1-失效，2-已付款
	private String payNumber;//支付订单号
	private Date payTime;//支付时间
	private BigDecimal accountPrice;//使用账户金额
	
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public BigDecimal getAccountPrice() {
		return accountPrice;
	}
	public void setAccountPrice(BigDecimal accountPrice) {
		this.accountPrice = accountPrice;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPayNumber() {
		return payNumber;
	}
	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}
	
}
