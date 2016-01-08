package com.letv.lcp.openstack.service.network.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.IP;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.Port;
import org.jclouds.openstack.neutron.v2.domain.Subnet;
import org.jclouds.openstack.neutron.v2.extensions.FloatingIPApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.letv.lcp.cloudvm.listener.FloatingIpCreateListener;
import com.letv.lcp.cloudvm.listener.RouterCreateListener;
import com.letv.lcp.cloudvm.model.network.FloatingIpCreateConf;
import com.letv.lcp.cloudvm.model.network.RouterCreateConf;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.cloudvm.model.task.VmCreateContext;
import com.letv.lcp.openstack.exception.APINotAvailableException;
import com.letv.lcp.openstack.exception.ResourceNotFoundException;
import com.letv.lcp.openstack.service.base.IOpenStackService;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;
import com.letv.lcp.openstack.service.erroremail.IErrorEmailService;
import com.letv.lcp.openstack.service.jclouds.IApiService;
import com.letv.lcp.openstack.service.local.ILocalRcCountService;
import com.letv.lcp.openstack.service.manage.impl.NetworkManagerImpl;
import com.letv.lcp.openstack.service.network.IOpenstackNetworkService;
import com.letv.lcp.openstack.service.session.IOpenStackSession;
import com.letv.lcp.openstack.service.validation.IValidationService;
import com.letv.lcp.openstack.util.RandomUtil;
import com.letv.portal.model.cloudvm.CloudvmRcCountType;

@Service
public class OpenstackNetworkServiceImpl implements IOpenstackNetworkService  {
	
	private static final Logger logger = LoggerFactory.getLogger(OpenstackNetworkServiceImpl.class);
	
	@Autowired
    private IValidationService validationService;
	@Autowired
    private IApiService apiService;
	@Autowired
    private IErrorEmailService errorEmailService;
	@Autowired
    private IOpenStackService openStackService;

	@Override
	public boolean delete(String id) {
		return false;
	}

	@Override
	public String createFloatingIp(Long userId, FloatingIpCreateConf floatingIpCreateConf,
			FloatingIpCreateListener listener, Object listenerUserData, Map<String, Object> params) {
		try {
            validationService.validate(floatingIpCreateConf);
            String sessionId = null;
            if(null != params.get("uuid")) {
            	sessionId = (String) params.get("uuid");
            } else {
            	sessionId = RandomUtil.generateRandomSessionId();
            }
            final IOpenStackSession openStackSession = this.openStackService.createSession(userId);
            openStackSession.init(null);
            try {
                NeutronApi neutronApi = apiService.getNeutronApi(userId, sessionId);
                ((NetworkManagerImpl) openStackSession.getNetworkManager()).createFloatingIp(neutronApi, floatingIpCreateConf, listener, listenerUserData, params);
            } finally {
                apiService.clearCache(userId, sessionId);
            }
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	errorEmailService.sendExceptionEmail(e, "计费调用创建公网IP", userId, floatingIpCreateConf.toString());
        	return e.getMessage();
        }
		return "success";
	}

	@Override
	public String createRouter(Long userId, RouterCreateConf routerCreateConf,
			RouterCreateListener listener, Object listenerUserData) {
		try {
            validationService.validate(routerCreateConf);
            final String sessionId = RandomUtil.generateRandomSessionId();
            final IOpenStackSession openStackSession = this.openStackService.createSession(userId);
            openStackSession.init(null);
            try {
                NeutronApi neutronApi = apiService.getNeutronApi(userId, sessionId);
                ((NetworkManagerImpl) openStackSession.getNetworkManager()).createRouter(neutronApi, routerCreateConf, listener, listenerUserData);
            } finally {
                apiService.clearCache(userId, sessionId);
            }
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	errorEmailService.sendExceptionEmail(e, "计费调用创建路由", userId, routerCreateConf.toString());
        	return e.getMessage();
        }
		return "success";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void rollBackFloatingIpWithCreateVmFail(Map<String, Object> params) {
		long userId = (long) params.get("userId");
		VMCreateConf2 vmCreateConf = (VMCreateConf2) params.get("vmCreateConf");
		List<VmCreateContext> context = (List<VmCreateContext>) params.get("vmCreateContexts");
		String region = vmCreateConf.getRegion();
		ILocalRcCountService localRcCountService = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalRcCountService();
		FloatingIPApi floatingIPApi = null;
		try {
			Optional<FloatingIPApi> floatingIPApiOptional = apiService.getNeutronApi(userId, (String)params.get("uuid")).getFloatingIPApi(region);
			if (!floatingIPApiOptional.isPresent()) {
				throw new APINotAvailableException(FloatingIPApi.class);
			}
			floatingIPApi = floatingIPApiOptional.get();
		} catch (APINotAvailableException e) {
			logger.error(e.getMessage(), e);
        	errorEmailService.sendExceptionEmail(e, "创建云主机中的公网Ip rollback异常", userId, vmCreateConf.toString());
        	return;
		}
		for (VmCreateContext vmCreateContext : context) {
			if (null != vmCreateContext.getFloatingIpId()) {
				boolean isSuccess = floatingIPApi.delete(vmCreateContext.getFloatingIpId());
                if(isSuccess) {
                	vmCreateContext.setFloatingIpId(null);
                    localRcCountService.decRcCount(userId, userId, region, CloudvmRcCountType.FLOATING_IP);
					localRcCountService.decRcCount(userId, region, CloudvmRcCountType.BAND_WIDTH, vmCreateConf.getBandWidth());
                }
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String createSubnetPorts(Map<String, Object> params) {
		VMCreateConf2 vmCreateConf = (VMCreateConf2) params.get("vmCreateConf");
		List<VmCreateContext> context = (List<VmCreateContext>) params.get("vmCreateContexts");
		Long userId = (Long)params.get("userId");
		Subnet privateSubnet = null;
		Network privateNetwork = null;
		if (StringUtils.isNotEmpty(vmCreateConf.getPrivateSubnetId())) {
			try {
				privateSubnet = getPrivateSubnetById(userId, (String)params.get("uuid"), vmCreateConf, privateNetwork);
			} catch (ResourceNotFoundException e) {
				logger.error(e.getMessage(), e);
	        	errorEmailService.sendExceptionEmail(e, "创建云主机中的子网获取异常", userId, vmCreateConf.toString());
	        	return e.getMessage();
			}
		} else {
			return "PrivateSubnetId is null";
		}

		for (VmCreateContext vmCreateContext : context) {
			Port subnetPort = apiService.getNeutronApi(userId, (String)params.get("uuid")).getPortApi(vmCreateConf.getRegion())
					.create(Port
							.createBuilder(privateNetwork.getId())
							.fixedIps(
									ImmutableSet.<IP> of(IP
											.builder()
											.subnetId(privateSubnet.getId()).build()))
							.build());
			vmCreateContext.setSubnetPortId(subnetPort.getId());;
		}
		params.put("vmCreateContexts", context);
		
		return "success";
	}
	
	private Subnet getPrivateSubnetById(Long userId, String uuid, 
			VMCreateConf2 vmCreateConf, Network privateNetwork) throws ResourceNotFoundException {
		Subnet privateSubnet = apiService.getNeutronApi(userId, uuid).getSubnetApi(vmCreateConf.getRegion())
				.get(vmCreateConf.getPrivateSubnetId());
		if (privateSubnet == null) {
			throw new ResourceNotFoundException("Subnet", "子网",
					vmCreateConf.getPrivateSubnetId());
		} else {
			privateNetwork = apiService.getNeutronApi(userId, uuid)
					.getNetworkApi(vmCreateConf.getRegion()).get(privateSubnet.getNetworkId());
			if (privateNetwork == null || privateNetwork.getShared()
					|| privateNetwork.getExternal()) {
				throw new ResourceNotFoundException("Private Subnet",
						"私有子网", vmCreateConf.getPrivateSubnetId());
			}
		}
		return privateSubnet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void rollBackSubnetPortsWithCreateVmFail(Map<String, Object> params) {
		VMCreateConf2 vmCreateConf = (VMCreateConf2) params.get("vmCreateConf");
		List<VmCreateContext> context = (List<VmCreateContext>) params.get("vmCreateContexts");
		Long userId = (Long)params.get("userId");
		Subnet privateSubnet = null;
		Network privateNetwork = null;
		if (StringUtils.isNotEmpty(vmCreateConf.getPrivateSubnetId())) {
			try {
				privateSubnet = getPrivateSubnetById(userId, (String)params.get("uuid"), vmCreateConf, privateNetwork);
			} catch (ResourceNotFoundException e) {
				logger.error(e.getMessage(), e);
	        	errorEmailService.sendExceptionEmail(e, "创建云主机中的子网获取异常", userId, vmCreateConf.toString());
	        	return;
			}
		} else {
			return;
		}

		for (VmCreateContext vmCreateContext : context) {
			if (null != vmCreateContext.getSubnetPortId()) {
				apiService.getNeutronApi(userId, (String)params.get("uuid")).getPortApi(vmCreateConf.getRegion())
						.delete(vmCreateContext.getSubnetPortId());
			}
		}
	}


}
