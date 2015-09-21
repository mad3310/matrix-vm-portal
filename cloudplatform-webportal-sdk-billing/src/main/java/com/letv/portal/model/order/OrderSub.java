package com.letv.portal.model.order;

import org.apache.ibatis.type.Alias;

import com.letv.common.model.BaseModel;
import com.letv.portal.model.subscription.Subscription;


/**
 * 子订单实体
 * @author lisuxiao
 *
 */
@Alias("OrderSub")
public class OrderSub extends BaseModel{

	private static final long serialVersionUID = 6612436637375760981L;

	private Long orderId;//订单ID
	private Long subscriptionId;//订阅主表主键
	private String descn;//描述
	private Double price;//价格
	private Double discountPrice;//折扣价
	private Subscription subscription;//订阅实体
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}
	public Subscription getSubscription() {
		return subscription;
	}
	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}
	
}
