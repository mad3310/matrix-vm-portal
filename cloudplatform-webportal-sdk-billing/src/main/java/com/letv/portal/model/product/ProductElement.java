package com.letv.portal.model.product;

import org.apache.ibatis.type.Alias;

import com.letv.common.model.BaseModel;
import com.letv.portal.model.base.BaseElement;

/**
 * 产品元素
 * @author lisuxiao
 *
 */
@Alias("ProductElement")
public class ProductElement extends BaseModel{

	private static final long serialVersionUID = 7865544036106203329L;
	
	private Long productId;//产品ID
	private Long baseElementId;//基础元素ID
	private String descn;//描述
	
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public String getDescn() {
		return descn;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getBaseElementId() {
		return baseElementId;
	}
	public void setBaseElementId(Long baseElementId) {
		this.baseElementId = baseElementId;
	}
}
