package com.letv.portal.model.cloudvm;

import com.letv.common.model.BaseModel;

/**
 * Created by zhouxianguang on 2015/9/25.
 */
public class CloudvmServerLink extends BaseModel{
    private static final long serialVersionUID = 8597337733350610446L;
    private String region;
    private String serverId;
    private String relation;
    private String type;
    private String href;

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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "CloudvmServerLink{" +
                "region='" + region + '\'' +
                ", serverId='" + serverId + '\'' +
                ", relation='" + relation + '\'' +
                ", type='" + type + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
