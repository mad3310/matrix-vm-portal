package com.letv.portal.model.adminoplog;

public enum AoLogType implements IntEnum {
	NULL(0, ""), INSERT(1, "创建"), UPDATE(2, "修改"), DELETE(3, "删除"), START(4,"启动"), STOP(5,"停止"), RESTART(6,"重启"), VALIDATE(7,"验证"), SYNC(8,"同步");

	private Integer code;
	private String name;

	private AoLogType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int toInt() {
		return code;
	}

}
