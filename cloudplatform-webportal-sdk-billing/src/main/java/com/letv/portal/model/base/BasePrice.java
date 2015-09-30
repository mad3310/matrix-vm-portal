package com.letv.portal.model.base;


import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;

import com.letv.common.model.BaseModel;

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
	
	private Long baseStandardId;//基础规格ID
	private String type;//计费类型：0-基础价格，1-阶梯，2-线性
	private String amount;//使用量
	private String unit;//单位
	private BigDecimal price;//单位价格
	private String descn;//描述
	
	public String getType() {
		return type;
	}
	public String getUnit() {
		return unit;
	}
	public String getDescn() {
		return descn;
	}
	public Long getBaseStandardId() {
		return baseStandardId;
	}
	public void setBaseStandardId(Long baseStandardId) {
		this.baseStandardId = baseStandardId;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
