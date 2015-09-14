package com.letv.portal.model.order;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.letv.common.model.BaseModel;
import com.letv.portal.model.subscription.SubscriptionDetail;

/**
 * 订单详情
 * @author lisuxiao
 *
 */
@Alias("OrderDetail")
public class OrderDetail extends BaseModel{

	private static final long serialVersionUID = 727699783805627057L;

	private Long subscriptionDetailId;//订阅详情ID
	private Long orderId;//订单ID
	private Double amount;//使用量
	private Double price;//产品元素单价*购买时长*数量
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private String descn;//描述
	
	private SubscriptionDetail subscriptionDetail;//订单详情
	
	public SubscriptionDetail getSubscriptionDetail() {
		return subscriptionDetail;
	}
	public void setSubscriptionDetail(SubscriptionDetail subscriptionDetail) {
		this.subscriptionDetail = subscriptionDetail;
	}
	public Long getSubscriptionDetailId() {
		return subscriptionDetailId;
	}
	public void setSubscriptionDetailId(Long subscriptionDetailId) {
		this.subscriptionDetailId = subscriptionDetailId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
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
