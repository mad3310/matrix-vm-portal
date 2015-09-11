package com.letv.portal.model.base;

import org.apache.ibatis.type.Alias;

import com.letv.common.model.BaseModel;
   
/**
 * 基础规格实体类
 * @author lisuxiao
 *
 */
@Alias("BaseStandard")
public class BaseStandard extends BaseModel{

	private static final long serialVersionUID = 4187977926839991202L;
	
	private Long baseElementId;//基础元素ID
	private Long fatherId;//父节点ID 
	private String standard;//产品规格
	private String value;//产品规则值
	private String type;//产品类型
	private String unit;//单位
	private String descn;//描述
	private BasePrice basePrice;//基础价格
	
	public BasePrice getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(BasePrice basePrice) {
		this.basePrice = basePrice;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getStandard() {
		return standard;
	}
	public String getType() {
		return type;
	}
	public String getDescn() {
		return descn;
	}
	public Long getBaseElementId() {
		return baseElementId;
	}
	public void setBaseElementId(Long baseElementId) {
		this.baseElementId = baseElementId;
	}
	public Long getFatherId() {
		return fatherId;
	}
	public void setFatherId(Long fatherId) {
		this.fatherId = fatherId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	

}
