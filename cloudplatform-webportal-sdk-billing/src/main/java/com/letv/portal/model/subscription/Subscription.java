package com.letv.portal.model.subscription;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.letv.common.model.BaseModel;
import com.letv.portal.enumeration.ChargeType;


/**Program Name: Subscription <br>
 * Description:  订阅<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月27日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Alias("Subscription")
public class Subscription extends BaseModel{

	private static final long serialVersionUID = 6612436637375760981L;

	private Long productId;//产品表主键
	private Long baseRegionId;//地域表主键
	private Long hclusterId;//机房机群主键
	private Long userId;//用户ID
	private Integer chargeType;//计费类型：0-包年包月，1-按量
	private Integer orderNum;//购买数量
	private Integer orderTime;//购买时长
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private boolean valid;//是否有效：false-无效，true-有效
	private String descn;//描述
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getBaseRegionId() {
		return baseRegionId;
	}
	public void setBaseRegionId(Long baseRegionId) {
		this.baseRegionId = baseRegionId;
	}
	public Long getHclusterId() {
		return hclusterId;
	}
	public void setHclusterId(Long hclusterId) {
		this.hclusterId = hclusterId;
	}
	public Integer getChargeType() {
		return chargeType;
	}
	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Integer orderTime) {
		this.orderTime = orderTime;
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
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	
}
