package com.letv.portal.model;

import java.sql.Date;

import com.letv.common.model.BaseModel;
import com.letv.portal.enumeration.PriceType;

/**Program Name: BaseProductPrice <br>
 * Description:  基础产品定价<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月30日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class BaseProductPrice extends BaseModel{

	private static final long serialVersionUID = 3242326535238989722L;

	private Long productId;
	private Long hclusterId;
	private Long basePriceId;
	private int version;
	private Date startTime;
	private Date endTime;
	private boolean valid;
	private float price;
	private PriceType priceType;
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getHclusterId() {
		return hclusterId;
	}
	public void setHclusterId(Long hclusterId) {
		this.hclusterId = hclusterId;
	}
	public Long getBasePriceId() {
		return basePriceId;
	}
	public void setBasePriceId(Long basePriceId) {
		this.basePriceId = basePriceId;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
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
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public PriceType getPriceType() {
		return priceType;
	}
	public void setPriceType(PriceType priceType) {
		this.priceType = priceType;
	}
	
}
