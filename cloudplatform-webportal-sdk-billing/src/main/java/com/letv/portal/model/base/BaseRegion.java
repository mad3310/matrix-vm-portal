package com.letv.portal.model.base;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.NotEmpty;

import com.letv.common.model.BaseModel;
   
/**
 * 地域实体表
 * @author lisuxiao
 *
 */
@Alias("BaseRegion")
public class BaseRegion extends BaseModel{

	private static final long serialVersionUID = 4187977926839991202L;
	
	private String code;
	
	private String name;
	
	private String descn;
	
	public BaseRegion() {}
	
	public BaseRegion(Long id) {
		super.setId(id);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}
	
	@NotEmpty(message="{billing.element.name.notEmpty.message}")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
