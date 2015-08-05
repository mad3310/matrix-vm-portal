package com.letv.portal.model;

import java.sql.Date;

import com.letv.common.model.BaseModel;


/**Program Name: SubscriptionUsed <br>
 * Description:  订阅用量<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年8月4日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class SubscriptionUsed extends BaseModel{

	private static final long serialVersionUID = 7226418118466063856L;
	
	private Long subscriptionDetailId;
	private Date startTime;
	private Date endTime;
	private float used;
	
	public Long getSubscriptionDetailId() {
		return subscriptionDetailId;
	}
	public void setSubscriptionDetailId(Long subscriptionDetailId) {
		this.subscriptionDetailId = subscriptionDetailId;
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
	public float getUsed() {
		return used;
	}
	public void setUsed(float used) {
		this.used = used;
	}
	
	
}
