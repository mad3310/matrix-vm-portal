package com.letv.portal.model.base;


import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.NotEmpty;

import com.letv.common.model.BaseModel;
import com.letv.portal.enumeration.BillingType;

/**Program Name: BasePrice <br>
 * Description:  基础资源定价<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月27日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Alias("BasePrice")
public class BasePrice extends BaseModel{

	private static final long serialVersionUID = 2938602851391168471L;
	
	private long elementId;
	private String elementStandard; //计费单元  1G，1M等
	private String elementQuality;	//元素质量类型，各元素不同，由页面决定
	private BillingType billingType;
	private String byTime;
	private String byUsedLadder;
	private float basePrice;
	
	public BasePrice(){}
	
	public BasePrice(Long id) {
		super.setId(id);
	}
	
	public long getElementId() {
		return elementId;
	}
	@NotEmpty
	public String getElementStandard() {
		return elementStandard;
	}
	@NotEmpty
	public String getElementQuality() {
		return elementQuality;
	}
	@NotEmpty
	public BillingType getBillingType() {
		return billingType;
	}
	public String getByTime() {
		return byTime;
	}
	public String getByUsedLadder() {
		return byUsedLadder;
	}
	@NotEmpty
	public float getBasePrice() {
		return basePrice;
	}
	
	public void setElementId(long elementId) {
		this.elementId = elementId;
	}
	public void setElementStandard(String elementStandard) {
		this.elementStandard = elementStandard;
	}
	public void setElementQuality(String elementQuality) {
		this.elementQuality = elementQuality;
	}
	public void setBillingType(BillingType billingType) {
		this.billingType = billingType;
	}
	public void setByTime(String byTime) {
		this.byTime = byTime;
	}
	public void setByUsedLadder(String byUsedLadder) {
		this.byUsedLadder = byUsedLadder;
	}
	public void setBasePrice(float basePrice) {
		this.basePrice = basePrice;
	}
	
}
