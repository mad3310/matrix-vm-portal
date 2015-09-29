package com.letv.portal.model.cloudvm;

import com.letv.common.model.BaseModel;

/**
 * Created by zhouxianguang on 2015/9/25.
 */
public class CloudvmServerAddress extends BaseModel {

    private static final long serialVersionUID = 5753166004717853721L;

    private String region;
    private String serverId;
    private String networkName;
    private String addr;
    private int version;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
