package com.letv.portal.model.product;

import org.apache.ibatis.type.Alias;

import com.letv.common.model.BaseModel;

/**
 * 
 * @author lisuxiao
 * 商品信息记录表
 *
 */
@Alias("ProductInfoRecord")
public class ProductInfoRecord extends BaseModel{

	private static final long serialVersionUID = 7865544036106203329L;
	
	private String params;//页面参数
	private String productType;//商品类型,2-云主机
	private String invokeType;//调用类型，1-去调用，0-不调用
	private String descn;
	private String instanceId;//实例ID
	
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getInvokeType() {
		return invokeType;
	}
	public void setInvokeType(String invokeType) {
		this.invokeType = invokeType;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	
}
