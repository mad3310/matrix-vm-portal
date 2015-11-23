package com.letv.portal.model.image;

import com.letv.common.model.BaseModel;
import com.letv.portal.model.dictionary.Dictionary;

/**
 * 镜像实体类
 *
 */
public class Image extends BaseModel {

	private static final long serialVersionUID = 7566619415070703541L;
	
	/*
	 * 字典
	 */
	private Dictionary dictionary;
	/*
	 * 名称
	 */
	private String name;
	/*
	 * 字典ID
	 */
	private Long dictionaryId;
	/*
	 * 用途
	 */
	private String purpose;
	/*
	 * 接口地址
	 */
	private String url;
	/*
	 * 镜像版本
	 */
	private String tag;
	/*
	 * 是否使用：1使用，0未使用
	 */
	private Integer isUsed;
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
	public Long getDictionaryId() {
		return dictionaryId;
	}
	public void setDictionaryId(Long dictionaryId) {
		this.dictionaryId = dictionaryId;
	}
	public Dictionary getDictionary() {
		return dictionary;
	}
	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Integer getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}

}
