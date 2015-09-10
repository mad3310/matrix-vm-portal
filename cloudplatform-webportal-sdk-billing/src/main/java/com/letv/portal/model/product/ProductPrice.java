package com.letv.portal.model.product;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import com.letv.common.model.BaseModel;
import com.letv.portal.model.base.BasePrice;

/**
 * 产品规格价格
 * @author lisuxiao
 *
 */
@Alias("ProductPrice")
public class ProductPrice extends BaseModel{

	private static final long serialVersionUID = 3242326535238989722L;
	
	private Long productId;
	private Long baseRegionId;
	private Long basePriceId;
	private Double price;
	private String version;
	private Boolean used;
	private Timestamp startTime;
	private Timestamp endTime;
	private Integer priority;//优先级
	private String descn;
	
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Long getBaseRegionId() {
		return baseRegionId;
	}
	public void setBaseRegionId(Long baseRegionId) {
		this.baseRegionId = baseRegionId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getBasePriceId() {
		return basePriceId;
	}
	public void setBasePriceId(Long basePriceId) {
		this.basePriceId = basePriceId;
	}
	public Double getPrice() {
		return price;
	}
	public String getVersion() {
		return version;
	}
	public Boolean getUsed() {
		return used;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public String getDescn() {
		return descn;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setUsed(Boolean used) {
		this.used = used;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	
}
