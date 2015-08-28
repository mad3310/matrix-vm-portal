package com.letv.portal.model.base;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.letv.common.model.BaseModel;
import com.letv.portal.anotation.validator.Unique;
   
/**Program Name: BaseElementModel <br>
 * Description:  基础资源元素<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月27日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Alias("BaseElement")
public class BaseElement extends BaseModel{

	private static final long serialVersionUID = 4187977926839991202L;
	
	private String name;
	
	private String descn;
	
	public BaseElement() {}
	
	public BaseElement(Long id) {
		super.setId(id);
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}
	
	@NotEmpty(message="{billing.element.name.notEmpty.message}")
	@Length(min=6,max=20,message="{billing.element.name.length.message}")
	@Unique(message="{billing.element.name.unique.message}",service="baseElementService")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
