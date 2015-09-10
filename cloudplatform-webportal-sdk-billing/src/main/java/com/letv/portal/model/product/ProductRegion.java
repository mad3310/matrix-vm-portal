package com.letv.portal.model.product;

import org.apache.ibatis.type.Alias;

import com.letv.common.model.BaseModel;
import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.base.BaseRegion;

/**
 * 产品元素
 * @author lisuxiao
 *
 */
@Alias("ProductRegion")
public class ProductRegion extends BaseModel{

	private static final long serialVersionUID = 7865544036106203329L;
	
	private BaseRegion baseRegion;//基础元素
	private HclusterModel hcluster;//机房机群
	private Long productId;//产品ID
	private Long baseRegionId;//基础元素ID
	private Long hclusterId;//机房机群ID
	private String descn;//描述
	
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
	public void setBaseRegion(BaseRegion baseRegion) {
		this.baseRegion = baseRegion;
	}
	public void setHcluster(HclusterModel hcluster) {
		this.hcluster = hcluster;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public BaseRegion getBaseRegion() {
		return baseRegion;
	}
	public HclusterModel getHcluster() {
		return hcluster;
	}
	public String getDescn() {
		return descn;
	}
	
}
