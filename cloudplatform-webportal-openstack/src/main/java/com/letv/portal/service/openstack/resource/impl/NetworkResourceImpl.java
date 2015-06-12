package com.letv.portal.service.openstack.resource.impl;

import com.letv.portal.service.openstack.resource.NetworkResource;
import com.letv.portal.service.openstack.resource.SubnetResource;
import org.jclouds.openstack.neutron.v2.domain.Network;

import java.util.List;

public class NetworkResourceImpl extends AbstractResource implements
        NetworkResource {

    private String region;
    private Network network;
    private List<SubnetResource> subnetResources;

    public NetworkResourceImpl(String region, Network network, List<SubnetResource> subnetResources) {
        this.region = region;
        this.network = network;
        this.subnetResources = subnetResources;
    }

    @Override
    public String getRegion() {
        return region;
    }

    @Override
    public String getId() {
        return this.network.getId();
    }

    @Override
    public String getName() {
        return this.network.getName();
    }

    @Override
    public List<SubnetResource> getSubnetResources() {
        return subnetResources;
    }
}
