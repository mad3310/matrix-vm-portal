package com.letv.portal.model.dictionary;

import com.letv.common.model.BaseModel;

/**
 * 字典表实体类
 *
 */
public class Dictionary extends BaseModel {

	private static final long serialVersionUID = 4894529482499154716L;

	/*
	 * 名称
	 */
	private String name;
	/*
	 * 类型
	 */
	private Integer type;
	/*
	 * 描述
	 */
	private String descn;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

}
