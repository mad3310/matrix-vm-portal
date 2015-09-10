package com.letv.portal.model.order;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.letv.common.model.BaseModel;
import com.letv.portal.model.subscription.Subscription;


/**
 * 订单实体
 * @author lisuxiao
 *
 */
@Alias("Order")
public class Order extends BaseModel{

	private static final long serialVersionUID = 6612436637375760981L;

	private Long subscriptionId;//订阅主表主键
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private String descn;//描述
	private Double price;//价格
	private Integer status;//订单状态：0-未付款，1-失效，2-已付款
	private Subscription subscription;//订阅实体
	
	public Subscription getSubscription() {
		return subscription;
	}
	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
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
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	
}
