package com.letv.lcp.cloudvm.enumeration;

import com.letv.portal.enumeration.IntEnum;

/**
 * 资源服务类型
 * @author lisuxiao
 *
 */
public enum ServiceTypeEnum implements IntEnum {
    COMPUTE(1, "ComputeService"), STORAGE(2, "StorageService"), NETWORK(3, "NetworkService"), IMAGE(4, "ImageService");

    private Integer code;
    private String name;

    private ServiceTypeEnum(Integer code, String name) {
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
