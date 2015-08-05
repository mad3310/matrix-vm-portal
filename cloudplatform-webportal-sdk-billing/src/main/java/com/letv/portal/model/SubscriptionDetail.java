package com.letv.portal.model;

import java.sql.Date;

import com.letv.common.model.BaseModel;

/**Program Name: SubscriptionDetail <br>
 * Description:  订阅详细<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月27日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class SubscriptionDetail extends BaseModel{

	private static final long serialVersionUID = 727699783805627057L;

	private Long subscriptionId;
	private Long productPriceId;
	private Date startTime;
	private Date endTime;
	private long indate;
	private boolean valid;
	
	private long buySize; //购买大小
	private long buyTime;
	
	public Long getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public Long getProductPriceId() {
		return productPriceId;
	}
	public void setProductPriceId(Long productPriceId) {
		this.productPriceId = productPriceId;
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
	public long getIndate() {
		return indate;
	}
	public void setIndate(long indate) {
		this.indate = indate;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public long getBuySize() {
		return buySize;
	}
	public void setBuySize(long buySize) {
		this.buySize = buySize;
	}
	public long getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(long buyTime) {
		this.buyTime = buyTime;
	}
	
}
