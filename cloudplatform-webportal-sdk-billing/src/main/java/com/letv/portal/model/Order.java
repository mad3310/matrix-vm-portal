package com.letv.portal.model;

import java.sql.Date;

import com.letv.common.model.BaseModel;

/**Program Name: Order <br>
 * Description:  订单<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月27日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class Order extends BaseModel{

	private static final long serialVersionUID = 144874650078658421L;

	private Long productId;
	private Long subscriptionId;
	private Date startTime;
	private Date endTime;
	private float price;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
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
