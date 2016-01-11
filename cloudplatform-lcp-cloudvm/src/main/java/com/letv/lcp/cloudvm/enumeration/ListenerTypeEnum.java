package com.letv.lcp.cloudvm.enumeration;

import com.letv.lcp.cloudvm.listener.FloatingIpCreateListener;
import com.letv.lcp.cloudvm.listener.RouterCreateListener;
import com.letv.lcp.cloudvm.listener.VmCreateListener;
import com.letv.lcp.cloudvm.listener.VolumeCreateListener;
import com.letv.portal.enumeration.IntEnum;

/**
 * 监听器类型
 * @author lisuxiao
 *
 */
public enum ListenerTypeEnum implements IntEnum {
	FloatingIpCreateListener(1), RouterCreateListener(2), 
	VmCreateListener(3), VolumeCreateListener(4);

	private Integer code;

    private ListenerTypeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
    

    @Override
    public int toInt() {
        return code;
    }

}
