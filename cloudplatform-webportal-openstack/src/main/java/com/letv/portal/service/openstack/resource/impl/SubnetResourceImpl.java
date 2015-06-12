package com.letv.portal.service.openstack.resource.impl;

import com.letv.portal.service.openstack.resource.SubnetResource;
import org.jclouds.openstack.neutron.v2.domain.Subnet;

/**
 * Created by zhouxianguang on 2015/6/12.
 */
public class SubnetResourceImpl extends AbstractResource implements
        SubnetResource {

    private String region;
    private Subnet subnet;

    public SubnetResourceImpl(String region, Subnet subnet) {
        this.region = region;
        this.subnet = subnet;
    }

    @Override
    public String getName() {
        return subnet.getName();
    }

    @Override
    public String getRegion() {
        return region;
    }

    @Override
    public String getId() {
        return subnet.getId();
    }
}
