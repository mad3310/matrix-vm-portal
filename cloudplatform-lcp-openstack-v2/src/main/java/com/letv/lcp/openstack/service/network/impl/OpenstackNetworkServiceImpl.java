package com.letv.lcp.openstack.service.network.impl;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONObject;
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

@Service("openstackNetworkService")
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

	@Override
	public boolean deleteFloatingIpById(Long userId, String region, String instanceId, Map<String, Object> params) {
		VMCreateConf2 vmCreateConf = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateConf")), VMCreateConf2.class);
		
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
        	errorEmailService.sendExceptionEmail(e, "删除公网Ip异常", userId, instanceId);
        	return false;
		}
		if (null != instanceId && null != floatingIPApi) {
			boolean isSuccess = floatingIPApi.delete(instanceId);
            if(isSuccess) {
                localRcCountService.decRcCount(userId, userId, region, CloudvmRcCountType.FLOATING_IP);
				localRcCountService.decRcCount(userId, region, CloudvmRcCountType.BAND_WIDTH, vmCreateConf.getBandWidth());
				return true;
            }
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String createSubnetPorts(Map<String, Object> params) {
		VMCreateConf2 vmCreateConf = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateConf")), VMCreateConf2.class);
		if (StringUtils.isEmpty(vmCreateConf.getPrivateSubnetId())) {
			return "success";
		}
		List<JSONObject> vmCreateContexts = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateContexts")), List.class);
		List<VmCreateContext> contexts = new ArrayList<VmCreateContext>();
		for (JSONObject jsonObject : vmCreateContexts) {
			VmCreateContext context = JSONObject.parseObject(jsonObject.toString(), VmCreateContext.class);
			contexts.add(context);
		}
		params.put("vmCreateContexts", contexts);
		
		Long userId = Long.parseLong((String)params.get("userId"));
		Subnet privateSubnet = apiService.getNeutronApi(userId, (String)params.get("uuid")).getSubnetApi(vmCreateConf.getRegion())
				.get(vmCreateConf.getPrivateSubnetId());
		Network privateNetwork = null;
		try {
			privateNetwork = getPrivateNetwork(userId, (String)params.get("uuid"), vmCreateConf, privateSubnet);
		} catch (ResourceNotFoundException e) {
			logger.error(e.getMessage(), e);
        	errorEmailService.sendExceptionEmail(e, "创建云主机中的子网获取异常", userId, vmCreateConf.toString());
        	return e.getMessage();
		}

		for (VmCreateContext vmCreateContext : contexts) {
			Port subnetPort = apiService.getNeutronApi(userId, (String)params.get("uuid")).getPortApi(vmCreateConf.getRegion())
					.create(Port
							.createBuilder(privateNetwork.getId())
							.fixedIps(
									ImmutableSet.<IP> of(IP
											.builder()
											.subnetId(privateSubnet.getId()).build()))
							.build());
			vmCreateContext.setSubnetPortInstanceId(subnetPort.getId());;
		}
		
		return "success";
	}
	
	private Network getPrivateNetwork(Long userId, String uuid, 
			VMCreateConf2 vmCreateConf, Subnet privateSubnet) throws ResourceNotFoundException {
		Network privateNetwork = null;
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
		return privateNetwork;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void rollBackSubnetPortsWithCreateVmFail(Map<String, Object> params) {
		VMCreateConf2 vmCreateConf = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateConf")), VMCreateConf2.class);
		if (vmCreateConf.getPrivateSubnetId() == null) {
			return;
		}
		List<JSONObject> context = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateContexts")), List.class);
		Long userId = (Long)params.get("userId");

		for (JSONObject jsonObj : context) {
			VmCreateContext vmCreateContext = JSONObject.parseObject(jsonObj.toJSONString(), VmCreateContext.class);
			if (null != vmCreateContext.getSubnetPortInstanceId()) {
				apiService.getNeutronApi(userId, (String)params.get("uuid")).getPortApi(vmCreateConf.getRegion())
						.delete(vmCreateContext.getSubnetPortInstanceId());
			}
		}
	}


}
