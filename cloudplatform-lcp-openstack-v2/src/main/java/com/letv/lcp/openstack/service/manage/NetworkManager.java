package com.letv.lcp.openstack.service.manage;

import java.util.List;

import com.letv.common.paging.impl.Page;
import com.letv.lcp.cloudvm.model.network.FloatingIpCreateConf;
import com.letv.lcp.cloudvm.model.network.RouterCreateConf;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.exception.RegionNotFoundException;
import com.letv.lcp.openstack.exception.ResourceNotFoundException;
import com.letv.lcp.openstack.model.network.FloatingIpResource;
import com.letv.lcp.openstack.model.network.NetworkResource;
import com.letv.lcp.openstack.model.network.PortResource;
import com.letv.lcp.openstack.model.network.RouterResource;
import com.letv.lcp.openstack.model.network.SubnetResource;

public interface NetworkManager extends ResourceManager {
	List<NetworkResource> list(String region) throws RegionNotFoundException,
			OpenStackException;

	NetworkResource get(String region, String id)
			throws RegionNotFoundException, ResourceNotFoundException,
			OpenStackException;

	NetworkResource getPrivate(String region, String id)
			throws OpenStackException;

	Page listPrivate(String regionGroup, String name, Integer currentPage,
			Integer recordsPerPage) throws OpenStackException;

	void createPrivate(String region, String name) throws OpenStackException;

	void editPrivate(String region, String networkId, String name)
			throws OpenStackException;

	void deletePrivate(String region, String networkId)
			throws OpenStackException;

	void createPrivateSubnet(String region, String networkId, String name,
			String cidr, boolean autoGatewayIp, String gatewayIp,
			boolean enableDhcp) throws OpenStackException;

	void createPrivateNetworkAndSubnet(String region, String networkName, String subnetName,
									   String cidr, boolean autoGatewayIp, String gatewayIp,
									   boolean enableDhcp) throws OpenStackException;

	SubnetResource getPrivateSubnet(String region, String subnetId)
			throws OpenStackException;

	void editPrivateSubnet(String region, String subnetId, String name,
			String gatewayIp, boolean enableDhcp) throws OpenStackException;

	void deletePrivateSubnet(String region, String subnetId)
			throws OpenStackException;

	Page listRouter(String regionGroup, String name, Integer currentPage,
			Integer recordsPerPage) throws OpenStackException;

	RouterResource getRouter(String region, String id)
			throws OpenStackException;

	void createRouter(RouterCreateConf routerCreateConf)
			throws OpenStackException;

	void editRouter(String region, String routerId, String name,
			boolean enablePublicNetworkGateway, String publicNetworkId)
			throws OpenStackException;

	Page listPrivateSubnet(String region, String name, Integer currentPage,
			Integer recordsPerPage) throws OpenStackException;

	void deleteRouter(String region, String routerId) throws OpenStackException;

	PortResource getPort(String region, String portId)
			throws OpenStackException;

	List<NetworkResource> listAvailableSubnetsForRouterInterface(String region)
			throws OpenStackException;

	void associateSubnetWithRouter(String region, String routerId,
			String subnetId) throws OpenStackException;

	void separateSubnetFromRouter(String region, String routerId,
			String subnetId) throws OpenStackException;

	FloatingIpResource getFloatingIp(String region, String floatingIpId)
			throws OpenStackException;

	void deleteFloaingIp(String region, String floatingIpId)
			throws OpenStackException;

	List<NetworkResource> listPublic(String region) throws OpenStackException;

	Page listFloatingIp(String region, String name, Integer currentPage,
			Integer recordsPerPage) throws OpenStackException;

	void createFloatingIp(FloatingIpCreateConf floatingIpCreateConf) throws OpenStackException;

	void editFloatingIp(String region, String floatingIpId, String name/*,
			int bandWidth*/) throws OpenStackException;
	
	List<NetworkResource> listShared(String region) throws OpenStackException;

	void checkCreateFloatingIp(FloatingIpCreateConf floatingIpCreateConf) throws OpenStackException;

	void checkCreateRouter(RouterCreateConf routerCreateConf) throws OpenStackException;

	// void editPort(String region, String portId, String name)
	// throws OpenStackException;
	//
	// void addRouterInterfaceForSubnet(String region, String routerId,
	// String subnetId) throws OpenStackException;

	// void removeRouterInterfaceForSubnet(String region, String routerId,
	// String subnetId) throws OpenStackException;

}
