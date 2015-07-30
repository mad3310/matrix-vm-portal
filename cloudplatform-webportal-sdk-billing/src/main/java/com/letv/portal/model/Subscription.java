package com.letv.portal.model;

import java.sql.Date;

import com.letv.common.model.BaseModel;
import com.letv.portal.enumeration.ChargeType;


/**Program Name: Subscription <br>
 * Description:  订阅<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月27日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class Subscription extends BaseModel{

	private static final long serialVersionUID = 6612436637375760981L;

	private Long productId;
	private ChargeType chargeType;
	private Date startTime;
	private Date endTime;
	private long indate;
	private boolean valid;
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public ChargeType getChargeType() {
		return chargeType;
	}
	public void setChargeType(ChargeType chargeType) {
		this.chargeType = chargeType;
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
	
	
}
