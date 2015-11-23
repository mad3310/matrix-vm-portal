package com.letv.portal.enumeration;
//--implements这里有实现
public enum ContentStatusEnum implements ByteEnum{
	/** 正在审核 */
	APPROVEING(0),
	/** 审核通过 */
	APPROVED(1),
	/** 拒绝审核 */
	REFUSED_APPROVE(2),
	/**审核预通过*/
	PRE_APPROVED(3);

	private Integer value;

	private ContentStatusEnum(Integer value) {
		this.value = value;
	}
	@Override
	public Integer getValue() {
		 return value;
	}

}
