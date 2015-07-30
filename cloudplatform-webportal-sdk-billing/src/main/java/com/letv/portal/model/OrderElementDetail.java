package com.letv.portal.model;

import java.sql.Date;

import com.letv.common.model.BaseModel;


/**Program Name: OrderElementDetail <br>
 * Description:  订单元素详细<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月30日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class OrderElementDetail extends BaseModel{

	private static final long serialVersionUID = 8638330529090072455L;
	
	private Long orderDetailId;
	private Long subscriptionId;
	private Date startTime;
	private Date endTime;
	private float price;
	
	public Long getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	public Long getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	

}
