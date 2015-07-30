package com.letv.portal.model;

import com.letv.common.model.BaseModel;
import com.letv.portal.enumeration.BillingType;

/**Program Name: BasePrice <br>
 * Description:  基础资源定价<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月27日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class BasePrice extends BaseModel{

	private static final long serialVersionUID = 2938602851391168471L;
	
	private long elementId;
	private String elementStandard;
	private String elementQuality;
	private BillingType billingType;
	private String byTime;
	private String byUsedLadder;
	private float basePrice;
	

	public long getElementId() {
		return elementId;
	}

	public String getElementStandard() {
		return elementStandard;
	}

	public void setElementStandard(String elementStandard) {
		this.elementStandard = elementStandard;
	}

	public String getElementQuality() {
		return elementQuality;
	}

	public void setElementQuality(String elementQuality) {
		this.elementQuality = elementQuality;
	}

	public BillingType getBillingType() {
		return billingType;
	}

	public void setBillingType(BillingType billingType) {
		this.billingType = billingType;
	}

	public String getByTime() {
		return byTime;
	}

	public void setByTime(String byTime) {
		this.byTime = byTime;
	}

	public String getByUsedLadder() {
		return byUsedLadder;
	}

	public void setByUsedLadder(String byUsedLadder) {
		this.byUsedLadder = byUsedLadder;
	}

	public float getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(float basePrice) {
		this.basePrice = basePrice;
	}

	public void setElementId(long elementId) {
		this.elementId = elementId;
	}
	
	
}
