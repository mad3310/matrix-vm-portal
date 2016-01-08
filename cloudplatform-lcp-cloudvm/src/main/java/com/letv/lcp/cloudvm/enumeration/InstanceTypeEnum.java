package com.letv.lcp.cloudvm.enumeration;

import com.letv.portal.enumeration.IntEnum;

/**
 * 实例类型(资源类型的实现方)
 * @author lisuxiao
 *
 */
public enum InstanceTypeEnum implements IntEnum {
    OPENSTACK(1, "openstack");
    
    private Integer code;
    private String name;

    private InstanceTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }
    
    public String getName() {
    	return name;
    }

    @Override
    public int toInt() {
        return code;
    }

}
