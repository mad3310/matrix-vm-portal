package com.letv.portal.model.subscription;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.letv.common.model.BaseModel;

/**Program Name: SubscriptionDetail <br>
 * Description:  订阅详细<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月27日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Alias("SubscriptionDetail")
public class SubscriptionDetail extends BaseModel{

	private static final long serialVersionUID = 727699783805627057L;

	private Long subscriptionId;//订阅主表ID
	private Long userId;//用户ID
	private String standardName;//规格名称
	private String standardValue;//规格值
	private Integer orderTime;//购买时长
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private boolean valid;//是否有效：false-无效，true-有效
	private String descn;//描述
	private BigDecimal price;//价格-用于记录各规格价格
	
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public String getStandardName() {
		return standardName;
	}
	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}
	public String getStandardValue() {
		return standardValue;
	}
	public void setStandardValue(String standardValue) {
		this.standardValue = standardValue;
	}
	public Integer getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Integer orderTime) {
		this.orderTime = orderTime;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
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
	
}
