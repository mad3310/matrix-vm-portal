package com.letv.portal.model.product;

import org.apache.ibatis.type.Alias;

import com.letv.common.model.BaseModel;

/**Program Name: BaseProduction <br>
 * Description:  基础产品<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月27日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Alias("Product")
public class Product extends BaseModel{

	private static final long serialVersionUID = 7865544036106203329L;
	
	private String name;//产品名称
	private String descn;
	
	public String getName() {
		return name;
	}
	public String getDescn() {
		return descn;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	
}
