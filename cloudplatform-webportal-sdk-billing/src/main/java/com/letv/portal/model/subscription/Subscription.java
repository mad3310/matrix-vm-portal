package com.letv.portal.model.subscription;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.letv.common.model.BaseModel;
import com.letv.portal.model.product.ProductInfoRecord;


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

	private String subscriptionNumber;//订阅编码
	private Long productId;//产品表主键
	private Long baseRegionId;//地域表主键
	private Long userId;//用户ID
	private Integer buyType;//购买类型：0-新购，1-续费
	private Integer chargeType;//计费类型：0-包年包月，1-按量
	private Integer orderTime;//购买时长
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private Integer valid;//是否有效：0-无效，1-有效
	private String descn;//描述
	private Long productInfoRecordId;//产品信息记录ID
	
	private String productName;//产品名称
	private String productDescn;//产品描述
	private String baseRegionName;//地域名称
	
	private ProductInfoRecord productInfoRecord;//商品记录表
	
	public Integer getBuyType() {
		return buyType;
	}
	public void setBuyType(Integer buyType) {
		this.buyType = buyType;
	}
	public ProductInfoRecord getProductInfoRecord() {
		return productInfoRecord;
	}
	public void setProductInfoRecord(ProductInfoRecord productInfoRecord) {
		this.productInfoRecord = productInfoRecord;
	}
	public String getProductDescn() {
		return productDescn;
	}
	public void setProductDescn(String productDescn) {
		this.productDescn = productDescn;
	}
	public String getSubscriptionNumber() {
		return subscriptionNumber;
	}
	public void setSubscriptionNumber(String subscriptionNumber) {
		this.subscriptionNumber = subscriptionNumber;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBaseRegionName() {
		return baseRegionName;
	}
	public void setBaseRegionName(String baseRegionName) {
		this.baseRegionName = baseRegionName;
	}
	public Long getProductInfoRecordId() {
		return productInfoRecordId;
	}
	public void setProductInfoRecordId(Long productInfoRecordId) {
		this.productInfoRecordId = productInfoRecordId;
	}
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
	public Integer getChargeType() {
		return chargeType;
	}
	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
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
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	
}
