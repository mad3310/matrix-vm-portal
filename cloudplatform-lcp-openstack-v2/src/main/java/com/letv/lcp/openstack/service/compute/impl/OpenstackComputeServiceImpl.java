package com.letv.lcp.openstack.service.compute.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jclouds.openstack.cinder.v1.domain.VolumeType;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.IP;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.Port;
import org.jclouds.openstack.neutron.v2.domain.Router;
import org.jclouds.openstack.neutron.v2.domain.Subnet;
import org.jclouds.openstack.neutron.v2.extensions.FloatingIPApi;
import org.jclouds.openstack.neutron.v2.extensions.RouterApi;
import org.jclouds.openstack.neutron.v2.features.NetworkApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.FloatingIP;
import org.jclouds.openstack.nova.v2_0.domain.Image;
import org.jclouds.openstack.nova.v2_0.domain.InterfaceAttachment;
import org.jclouds.openstack.nova.v2_0.domain.KeyPair;
import org.jclouds.openstack.nova.v2_0.domain.Quota;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.domain.ServerCreated;
import org.jclouds.openstack.nova.v2_0.domain.ServerExtendedStatus;
import org.jclouds.openstack.nova.v2_0.extensions.AttachInterfaceApi;
import org.jclouds.openstack.nova.v2_0.extensions.KeyPairApi;
import org.jclouds.openstack.nova.v2_0.extensions.QuotaApi;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;
import org.jclouds.openstack.nova.v2_0.options.CreateServerOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.letv.common.email.bean.MailMessage;
import com.letv.lcp.cloudvm.listener.VmCreateListener;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.cloudvm.model.task.VmCreateContext;
import com.letv.lcp.openstack.exception.APINotAvailableException;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.exception.ResourceNotFoundException;
import com.letv.lcp.openstack.exception.UserOperationException;
import com.letv.lcp.openstack.service.base.IOpenStackService;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;
import com.letv.lcp.openstack.service.compute.IOpenstackComputeService;
import com.letv.lcp.openstack.service.erroremail.IErrorEmailService;
import com.letv.lcp.openstack.service.jclouds.IApiService;
import com.letv.lcp.openstack.service.local.ILocalCommonQuotaSerivce;
import com.letv.lcp.openstack.service.local.ILocalVolumeService;
import com.letv.lcp.openstack.service.manage.VMManager;
import com.letv.lcp.openstack.service.session.IOpenStackSession;
import com.letv.lcp.openstack.service.validation.IValidationService;
import com.letv.portal.model.common.CommonQuotaType;
import com.letv.portal.model.common.UserModel;
import com.letv.portal.service.cloudvm.ICloudvmRegionService;
import com.letv.portal.service.common.IUserService;

@Service("openstackComputeService")
public class OpenstackComputeServiceImpl implements IOpenstackComputeService  {
	
	private static final Logger logger = LoggerFactory.getLogger(OpenstackComputeServiceImpl.class);
	
	@Autowired
    private IValidationService validationService;
	@Autowired
    private IErrorEmailService errorEmailService;
	@Autowired
    private IOpenStackService openStackService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IApiService apiService;
    @Autowired
    private VMManager vmManager;
    @Autowired
    private ICloudvmRegionService cloudvmRegionService;
    

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String createVm(Long userId, VMCreateConf2 vmCreateConf, VmCreateListener vmCreateListener, Object listenerUserData, Map<String, Object> params) {
		List<JSONObject> vmCreateContexts = JSONObject.parseObject((String)params.get("vmCreateContexts"), List.class);
		List<VmCreateContext> contexts = new ArrayList<VmCreateContext>();
		for (JSONObject jsonObject : vmCreateContexts) {
			VmCreateContext context = JSONObject.parseObject(jsonObject.toString(), VmCreateContext.class);
			createOneVm(userId, vmCreateConf, context, params);
			contexts.add(context);
		}
		params.put("vmCreateContexts", contexts);
		return "success";
	}

	
	private String createOneVm(Long userId, VMCreateConf2 vmCreateConf, VmCreateContext context, Map<String, Object> params) {
		CreateServerOptions options = new CreateServerOptions();
		String sessionId = (String)params.get("uuid");
		String region = vmCreateConf.getRegion();

		NovaApi novaApi = apiService.getNovaApi(userId, sessionId);
		try {
			if (StringUtils.isNotEmpty(vmCreateConf.getKeyPairName())) {
				Optional<KeyPairApi> keyPairApiOptional = novaApi.getKeyPairApi(region);
				if (!keyPairApiOptional.isPresent()) {
					throw new APINotAvailableException(KeyPairApi.class);
				}
				KeyPair keyPair = keyPairApiOptional.get().get(vmCreateConf.getKeyPairName());
				if (keyPair == null) {
					throw new ResourceNotFoundException("KeyPair", "密钥对", vmCreateConf.getKeyPairName());
				}
				options.keyPairName(keyPair.getName());
			} else {
				options.adminPass(vmCreateConf.getAdminPass());
			}

			String imageRef = null;
			Image snapshot = novaApi.getImageApi(region)
					.get(StringUtils.isNotEmpty(vmCreateConf.getImageId())?vmCreateConf.getImageId():vmCreateConf.getSnapshotId());
			if (snapshot == null) {
				throw new ResourceNotFoundException("Image", "快照/镜像", vmCreateConf.getSnapshotId());
			}
			imageRef = snapshot.getId();

			options.securityGroupNames("default");
			
			if (StringUtils.isNotEmpty(vmCreateConf.getPrivateSubnetId())) {
				options.novaNetworks(ImmutableSet.<org.jclouds.openstack.nova.v2_0.domain.Network> of(org.jclouds.openstack.nova.v2_0.domain.Network.builder()
						.portUuid(context.getSubnetPortId()).build()));
			} else {
				NeutronApi neutronApi = apiService.getNeutronApi(userId, sessionId);
				NetworkApi networkApi = neutronApi.getNetworkApi(region);
				Network sharedNetwork = networkApi.get(vmCreateConf.getSharedNetworkId());
				if (sharedNetwork == null || !sharedNetwork.getShared()) {
					throw new ResourceNotFoundException("Shared Network", "共享网络", vmCreateConf.getSharedNetworkId());
				}
				options.networks(sharedNetwork.getId());
			}

			Flavor flavor = novaApi.getFlavorApi(region).get(vmCreateConf.getFlavorId());
			if (flavor == null) {
				throw new ResourceNotFoundException("Flavor", "云主机配置", vmCreateConf.getFlavorId());
			}
			ServerCreated serverCreated = novaApi
					.getServerApi(region)
					.create(context.getResourceName(), imageRef, vmCreateConf.getFlavorId(), options);
			context.setServerCreatedId(serverCreated.getId());
			Server server = novaApi.getServerApi(region).get(serverCreated.getId());
			context.setServerId(server.getId());

			vmManager.recordVmCreated(userId, region, null, flavor);

			if (null != context.getVolumeId()) {
			    ILocalVolumeService localVolumeService = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalVolumeService();
			    localVolumeService.updateVmIdAndVmName(userId
						, userId
						, region
						, context.getVolumeId()
						, server.getId()
				        , server.getName());
			}
		} catch (OpenStackException e) {
			logger.error(e.getMessage(), e);
	    	errorEmailService.sendExceptionEmail(e, "云主机创建异常", userId, (String) params.get("vmCreateConf"));
	    	return e.getMessage();
		}
		return "success";
	}

	@Override
	public String getVmCreatePrepare(Map<String, Object> params) {
		VMCreateConf2 vmCreateConf = JSONObject.parseObject((String)params.get("vmCreateConf"), VMCreateConf2.class);
		if (StringUtils.isEmpty(vmCreateConf.getSharedNetworkId())) {
            vmCreateConf.setBindFloatingIp(false);
        }
		try {
            validationService.validate(vmCreateConf);
            params.put("uuid", UUID.randomUUID().toString());
            IOpenStackSession openStackSession = openStackService.createSession((Long)params.get("userId"));
            openStackSession .init(null);
            OpenStackServiceImpl.getOpenStackServiceGroup().getResourceService()
            	.createDefaultSecurityGroupAndRule(this.apiService.getNeutronApi((Long)params.get("userId"), (String)params.get("uuid")));
		} catch (Exception e) {
	    	logger.error(e.getMessage(), e);
	    	errorEmailService.sendExceptionEmail(e, "云主机创建准备异常", (Long)params.get("userId"), (String) params.get("vmCreateConf"));
	    	return e.getMessage();
		}
        
        return "success";
	}
	

	@Override
	public String checkVmCreateConf(Map<String, Object> params) {
		VMCreateConf2 vmCreateConf = JSONObject.parseObject((String)params.get("vmCreateConf"), VMCreateConf2.class);
		Long userId = (Long) params.get("userId");
		String sessionId = (String) params.get("uuid");
		String region = vmCreateConf.getRegion();
		
		NeutronApi neutronApi = apiService.getNeutronApi(userId, sessionId);
		NetworkApi networkApi = neutronApi.getNetworkApi(region);
		NovaApi novaApi = apiService.getNovaApi(userId, sessionId);

		try {
			Subnet privateSubnet = null;
			Network privateNetwork = null;
			if (StringUtils.isNotEmpty(vmCreateConf.getPrivateSubnetId())) {
				privateSubnet = neutronApi.getSubnetApi(region).get(vmCreateConf.getPrivateSubnetId());
				if (privateSubnet == null) {
					throw new ResourceNotFoundException("Subnet", "子网",
							vmCreateConf.getPrivateSubnetId());
				} else {
					privateNetwork = networkApi.get(privateSubnet.getNetworkId());
					if (privateNetwork == null || privateNetwork.getShared()
							|| privateNetwork.getExternal()) {
						throw new ResourceNotFoundException("Private Subnet",
								"私有子网", vmCreateConf.getPrivateSubnetId());
					}
				}
			} else {
				Network sharedNetwork = networkApi.get(vmCreateConf.getSharedNetworkId());
				if (sharedNetwork == null || !sharedNetwork.getShared()) {
					throw new ResourceNotFoundException("Shared Network", "共享网络", vmCreateConf.getSharedNetworkId());
				}
			}
			
			Flavor flavor = novaApi.getFlavorApi(region).get(vmCreateConf.getFlavorId());
			if (flavor == null) {
				throw new ResourceNotFoundException("Flavor", "云主机配置", vmCreateConf.getFlavorId());
			}
			
			Image image = novaApi.getImageApi(region).get(StringUtils.isNotEmpty(vmCreateConf.getImageId())?
					vmCreateConf.getImageId():vmCreateConf.getSnapshotId());
			if (image == null) {
				throw new ResourceNotFoundException("Image", "镜像/快照",
						vmCreateConf.getImageId());
			}

			if (vmCreateConf.getVolumeSize() < 0) {
				throw new UserOperationException("Data disk size cannot be less than zero", "数据盘大小不能小于0");
			} else if (vmCreateConf.getVolumeSize() > 0) {
				VolumeType volumeType = apiService.getCinderApi(userId, sessionId).getVolumeTypeApi(region).get(vmCreateConf.getVolumeTypeId());
				if (volumeType == null) {
					throw new ResourceNotFoundException("Volume Type", "快照", vmCreateConf.getVolumeTypeId());
				}
			}

			if (vmCreateConf.getBindFloatingIp()) {
				if (vmCreateConf.getBandWidth() <= 0) {
					throw new UserOperationException("The bandwidth must be greater than zero.", "带宽必须大于0");
				}

				if (StringUtils.isEmpty(vmCreateConf.getFloatingNetworkId())) {
					vmCreateConf.setFloatingNetworkId(OpenStackServiceImpl.getOpenStackServiceGroup()
			        		.getLocalNetworkService().getPublicNetworkId(neutronApi, vmCreateConf.getRegion()));
				}
				Network floatingNetwork = networkApi.get(vmCreateConf.getFloatingNetworkId());
				if (floatingNetwork == null || !floatingNetwork.getExternal()) {
					throw new ResourceNotFoundException("Floating Network", "公网",
							vmCreateConf.getFloatingNetworkId());
				}

				if (StringUtils.isNotEmpty(vmCreateConf.getPrivateSubnetId())) {
					String routerId = null;
					findRouterId: for (Port port : neutronApi.getPortApi(region).list().concat().toList()) {
						if ("network:router_interface"
								.equals(port.getDeviceOwner())) {
							if (privateSubnet.getNetworkId().equals(port.getNetworkId())) {
								ImmutableSet<IP> fixedIps = port.getFixedIps();
								if (fixedIps != null) {
									for (IP ip : fixedIps) {
										if (privateSubnet.getId().equals(ip.getSubnetId())) {
											routerId = port.getDeviceId();
											break findRouterId;
										}
									}
								}
							}
						}
					}
					if (routerId == null) {
						throw new UserOperationException(
								"Private subnet is not associate with router.","私有子网未关联路由");
					}
					
					Optional<RouterApi> routerApiOptional = neutronApi.getRouterApi(region);
					if (!routerApiOptional.isPresent()) {
						throw new APINotAvailableException(RouterApi.class);
					}
					RouterApi routerApi = routerApiOptional.get();
					Router router = routerApi.get(routerId);
					if (router == null) {
						throw new ResourceNotFoundException("Router", "路由", routerId);
					}
					if (router.getExternalGatewayInfo() == null
							|| StringUtils.isEmpty(router.getExternalGatewayInfo()
									.getNetworkId())) {
						throw new UserOperationException(MessageFormat.format(
								"Router \"{0}\" is not enable gateway.", routerId),
								MessageFormat.format("私有子网关联的路由“{0}”未设置网关",
										routerId));
					}
					if (!StringUtils.equals(router.getExternalGatewayInfo()
							.getNetworkId(), floatingNetwork.getId())) {
						throw new UserOperationException(
								MessageFormat.format(
										"The carrier of Router \"{0}\" is not the carrier of Floating IP.",
										routerId), MessageFormat.format(
										"路由“{0}”的线路和公网IP的线路不是同一个线路", routerId));
					}
				}
			}

			if (StringUtils.isNotEmpty(vmCreateConf.getKeyPairName())) {
				Optional<KeyPairApi> keyPairApiOptional = novaApi.getKeyPairApi(region);
				if (!keyPairApiOptional.isPresent()) {
					throw new APINotAvailableException(KeyPairApi.class);
				}
				KeyPair keyPair = keyPairApiOptional.get().get(vmCreateConf.getKeyPairName());
				if (keyPair == null) {
					throw new ResourceNotFoundException("KeyPair", "密钥对", vmCreateConf.getKeyPairName());
				}
			} else {
				OpenStackServiceImpl.getOpenStackServiceGroup().getPasswordService().validateUserAdminPass(vmCreateConf.getAdminPass());
			}

			if (vmCreateConf.getCount() <= 0) {
				throw new UserOperationException(
						"Virtual machine number must be greater than zero.", "虚拟机数量必须大于0");
			}
		} catch (OpenStackException e) {
			logger.error(e.getMessage(), e);
	    	errorEmailService.sendExceptionEmail(e, "校验云主机创建参数异常", userId, vmCreateConf.toString());
	    	return e.getMessage();
		}
		return "success";
	}

	@Override
	public String checkQuota(Map<String, Object> params) {
		Long userId = (Long) params.get("userId");
		VMCreateConf2 vmCreateConf = JSONObject.parseObject((String)params.get("vmCreateConf"), VMCreateConf2.class);
		
		String uuid = (String) params.get("uuid");
		NovaApi novaApi = apiService.getNovaApi(userId, uuid);
		
		int serverTotalCount = 0, serverTotalVcpus = 0, serverTotalRam = 0;
		List<Server> servers = novaApi.getServerApi(vmCreateConf.getRegion())
				.listInDetail().concat().toList();
		Map<String, Flavor> idToFlavor = new HashMap<String, Flavor>();
		for (Server server : servers) {
			serverTotalCount++;
			String flavorId = server.getFlavor().getId();
			Flavor flavor = idToFlavor.get(flavorId);
			if (flavor == null) {
				flavor = novaApi.getFlavorApi(vmCreateConf.getRegion()).get(flavorId);
				idToFlavor.put(flavorId, flavor);
			}
			serverTotalVcpus += flavor.getVcpus();
			serverTotalRam += flavor.getRam();
		}

		ILocalCommonQuotaSerivce localCommonQuotaSerivce = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce();
		Flavor flavor = novaApi.getFlavorApi(vmCreateConf.getRegion()).get(vmCreateConf.getFlavorId());
		try {
			if (flavor == null) {
				throw new ResourceNotFoundException("Flavor", "云主机配置",vmCreateConf.getFlavorId());
			}
			localCommonQuotaSerivce.checkQuota(userId, vmCreateConf.getRegion(), CommonQuotaType.CLOUDVM_VM, servers.size() + vmCreateConf.getCount());
			localCommonQuotaSerivce.checkQuota(userId, vmCreateConf.getRegion(), CommonQuotaType.CLOUDVM_CPU, serverTotalVcpus + vmCreateConf.getCount() * flavor.getVcpus());
			localCommonQuotaSerivce.checkQuota(userId, vmCreateConf.getRegion(), CommonQuotaType.CLOUDVM_MEMORY, (serverTotalRam + vmCreateConf.getCount() * flavor.getRam()) / 1024);

			Optional<QuotaApi> quotaApiOptional = novaApi.getQuotaApi(vmCreateConf.getRegion());
			if (!quotaApiOptional.isPresent()) {
				throw new APINotAvailableException(QuotaApi.class);
			}
			
			IOpenStackSession openStackSession = openStackService.createSession((Long)params.get("userId"));
            openStackSession.init(null);
			Quota novaQuota = quotaApiOptional.get().getByTenant(openStackSession.getOpenStackUser().getTenantId());
			if (novaQuota == null) {
				throw new OpenStackException("VM quota is not available.",
						"虚拟机配额不可用。");
			}

			if (serverTotalCount + vmCreateConf.getCount() > novaQuota
					.getInstances()) {
				throw new UserOperationException("VM count exceeding the quota.",
						"虚拟机数量超过配额。");
			}

			if (serverTotalVcpus + vmCreateConf.getCount()
					* flavor.getVcpus() > novaQuota.getCores()) {
				throw new UserOperationException("Vcpu count exceeding the quota.",
						"虚拟CPU数量超过配额。");
			}

			if (serverTotalRam + vmCreateConf.getCount()
					* flavor.getRam() > novaQuota.getRam()) {
				throw new UserOperationException(
						"Ram amounts exceeding the quota.", "内存总量超过配额。");
			}
		} catch (OpenStackException e) {
			logger.error(e.getMessage(), e);
	    	errorEmailService.sendExceptionEmail(e, "云主机创建未通过配额校验", (Long)params.get("userId"), (String) params.get("vmCreateConf"));
	    	return e.getMessage();
		}
		return "success";
	}

	@SuppressWarnings("unchecked")
	@Override
	public String waitingVmsCreated(Map<String, Object> params) {
		List<JSONObject> contexts = JSONObject.parseObject((String) params.get("vmCreateContexts"), List.class);
		VMCreateConf2 vmCreateConf = JSONObject.parseObject((String)params.get("vmCreateConf"), VMCreateConf2.class);
		int i=0;
		try {
            List<VmCreateContext> unFinishedVms = new LinkedList<VmCreateContext>();
            for (JSONObject jsonObject : contexts) {
            	VmCreateContext vmCreateContext = JSONObject.parseObject(jsonObject.toJSONString(), VmCreateContext.class);
                if (vmCreateContext.getServerCreatedId() != null) {
                    unFinishedVms.add(vmCreateContext);
                }
            }
            NeutronApi neutronApi = apiService.getNeutronApi((Long)params.get("userId"), (String)params.get("uuid"));
    		NetworkApi networkApi = neutronApi.getNetworkApi(vmCreateConf.getRegion());

            final String vmNetworkName;
            if (StringUtils.isNotEmpty(vmCreateConf.getPrivateSubnetId())) {
            	Subnet privateSubnet = neutronApi.getSubnetApi(vmCreateConf.getRegion()).get(vmCreateConf.getPrivateSubnetId());
				if (privateSubnet == null) {
					throw new ResourceNotFoundException("Subnet", "子网",
							vmCreateConf.getPrivateSubnetId());
				} else {
					Network privateNetwork = networkApi.get(privateSubnet.getNetworkId());
					if (privateNetwork == null || privateNetwork.getShared()
							|| privateNetwork.getExternal()) {
						throw new ResourceNotFoundException("Private Subnet",
								"私有子网", vmCreateConf.getPrivateSubnetId());
					}
					vmNetworkName = privateNetwork.getName();
				}
            } else {
            	Network sharedNetwork = networkApi.get(vmCreateConf.getSharedNetworkId());
				if (sharedNetwork == null || !sharedNetwork.getShared()) {
					throw new ResourceNotFoundException("Shared Network", "共享网络", vmCreateConf.getSharedNetworkId());
				}
                vmNetworkName = sharedNetwork.getName();
            }
            while (!unFinishedVms.isEmpty()) {
                for (VmCreateContext vmCreateContext : unFinishedVms
                        .toArray(new VmCreateContext[0])) {
                    Server server = apiService.getNovaApi((Long)params.get("userId"), (String)params.get("uuid")).getServerApi(vmCreateConf.getRegion())
                            .get(vmCreateContext.getServerCreatedId());
                    if (server == null) {
                        unFinishedVms.remove(vmCreateContext);
                    } else {
                        ServerExtendedStatus serverExtendedStatus = server.getExtendedStatus().get();
                        String taskState = serverExtendedStatus.getTaskState();
                        String vmState = serverExtendedStatus.getVmState();
                        Server.Status serverStatus = server.getStatus();
                        if (serverStatus == Server.Status.ERROR || serverStatus == Server.Status.SHUTOFF) {
                            unFinishedVms.remove(vmCreateContext);
                        } else if (serverStatus == Server.Status.ACTIVE && taskState == null && "active".equals(vmState) && !server.getAddresses().get(vmNetworkName).isEmpty()) {
                            unFinishedVms.remove(vmCreateContext);
                        }
                    }
                }
                Thread.sleep(1000);
                if(i<600) {
                	i++;
                } else {
                	return "等待云主机创建完成时状态异常";
                }
            }
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
	    	errorEmailService.sendExceptionEmail(e, "等待云主机创建完成", (Long)params.get("userId"), (String) params.get("vmCreateConf"));
	    	return e.getMessage();
        }
		return "success";
	}

	@SuppressWarnings("unchecked")
	@Override
	public String bindFloatingIp(Map<String, Object> params) {
		VMCreateConf2 vmCreateConf = JSONObject.parseObject((String)params.get( "vmCreateConf"), VMCreateConf2.class);
        List<JSONObject> vmCreateContexts = JSONObject.parseObject((String)params.get( "vmCreateContexts"), List.class );
        List<VmCreateContext> contexts = new ArrayList<VmCreateContext>();
        params.put("vmCreateContexts", contexts);
        
		NovaApi novaApi = apiService.getNovaApi((Long)params.get("userId"), (String)params.get("uuid"));
		for (JSONObject jsonObject : vmCreateContexts) {
			VmCreateContext vmCreateContext = JSONObject.parseObject(jsonObject.toString(), VmCreateContext. class);
            contexts.add(vmCreateContext);
            
			Server server = novaApi.getServerApi(vmCreateConf.getRegion())
                    .get(vmCreateContext.getServerCreatedId());
			Optional<org.jclouds.openstack.nova.v2_0.extensions.FloatingIPApi> floatingIPApiOptional = novaApi.getFloatingIPApi(vmCreateConf.getRegion());
			try {
				if (!floatingIPApiOptional.isPresent()) {
					throw new APINotAvailableException(
							org.jclouds.openstack.nova.v2_0.extensions.FloatingIPApi.class);
				}
				FloatingIP floatingIP = floatingIPApiOptional.get().get(vmCreateContext.getFloatingIpId());
				if (server != null && server.getStatus() != Server.Status.ERROR && floatingIP != null && floatingIP.getInstanceId() == null) {
					Optional<AttachInterfaceApi> attachInterfaceApiOptional = novaApi.getAttachInterfaceApi(vmCreateConf.getRegion());
				    if (!attachInterfaceApiOptional.isPresent()) {
				        throw new APINotAvailableException(AttachInterfaceApi.class);
				    }
					AttachInterfaceApi attachInterfaceApi = attachInterfaceApiOptional.get();
					
					Optional<FloatingIPApi> floatingIPApiOptional2 = apiService
							.getNeutronApi((Long)params.get("userId"), (String)params.get("uuid")).getFloatingIPApi(vmCreateConf.getRegion());
					if (!floatingIPApiOptional2.isPresent()) {
						throw new APINotAvailableException(FloatingIPApi.class);
					}
					FloatingIPApi floatingIPApi = floatingIPApiOptional2.get();
					String floatingIpId = floatingIP.getId();
					List<InterfaceAttachment> interfaceAttachmentList = attachInterfaceApi.list(server.getId()).toList();
					if (!interfaceAttachmentList.isEmpty()) {
						String portId = interfaceAttachmentList.get(0).getPortId();
						floatingIPApi.update(floatingIpId
								, org.jclouds.openstack.neutron.v2.domain.FloatingIP.UpdateFloatingIP.updateBuilder().portId(
								portId).build());
						vmCreateContext.setFloatingIpBindDate(new Date());
					}
				}
			} catch (APINotAvailableException e) {
				logger.error(e.getMessage(), e);
		    	errorEmailService.sendExceptionEmail(e, "云主机创建完成后绑定公网ip异常", (Long)params.get("userId"), (String) params.get("vmCreateConf"));
		    	return e.getMessage();
			}
		}
		return "success";
	}

	@SuppressWarnings("unchecked")
	@Override
	public String emailVmsCreated(Map<String, Object> params) {
		List<JSONObject> contexts = JSONObject.parseObject((String) params.get("vmCreateContexts"), List.class);
		VMCreateConf2 vmCreateConf = JSONObject.parseObject((String)params.get("vmCreateConf"), VMCreateConf2.class);
		
		UserModel user = this.userService.getUserById((Long)params.get("userId"));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<String, Object> mailMessageModel = new HashMap<String, Object>();
        mailMessageModel.put("userName", user.getUserName());

        List<Map<String, Object>> vmModelList = new LinkedList<Map<String, Object>>();
        mailMessageModel.put("vmList", vmModelList);
        
        ServerApi serverApi = apiService.getNovaApi((Long)params.get("userId"), (String)params.get("uuid")).getServerApi(vmCreateConf.getRegion());
                
        Optional<FloatingIPApi> floatingIPApiOptional2 = apiService
				.getNeutronApi((Long)params.get("userId"), (String)params.get("uuid")).getFloatingIPApi(vmCreateConf.getRegion());
		if (!floatingIPApiOptional2.isPresent()) {
			try {
				throw new APINotAvailableException(FloatingIPApi.class);
			} catch (APINotAvailableException e) {
				logger.error(e.getMessage(), e);
		    	errorEmailService.sendExceptionEmail(e, "云主机创建完成后发送邮件异常", (Long)params.get("userId"), (String) params.get("vmCreateConf"));
		    	return e.getMessage();
			}
		}
		FloatingIPApi floatingIPApi = floatingIPApiOptional2.get();
		
		String regionDisplayName = this.cloudvmRegionService.selectByCode(vmCreateConf.getRegion()).getDisplayName();
		
        for (JSONObject json : contexts) {
        	VmCreateContext vmContext = JSONObject.parseObject(json.toJSONString(), VmCreateContext.class);
            if (vmContext.getServerCreatedId() != null) {
            	Server server = serverApi.get(vmContext.getServerCreatedId());
                Map<String, Object> vmModel = new HashMap<String, Object>();
                vmModel.put("region", regionDisplayName);
                vmModel.put("vmId", vmContext.getServerCreatedId());
                vmModel.put("vmName", server.getName());
                vmModel.put("adminUserName", "root");
                if (StringUtils.isNotEmpty(vmCreateConf.getKeyPairName())) {
                    vmModel.put("keyPairName", vmCreateConf.getKeyPairName());
                } else {
                    vmModel.put("password", vmCreateConf.getAdminPass());
                }
                vmModel.put("createTime", format.format((server.getCreated())));
                if (vmContext.getFloatingIpId()!= null && vmContext.getFloatingIpBindDate() != null) {
                    vmModel.put("ip", floatingIPApi.get(vmContext.getFloatingIpId()).getFloatingIpAddress());
                    vmModel.put("port", 22);
                    vmModel.put("bindTime", format.format(vmContext.getFloatingIpBindDate()));
                }
                vmModelList.add(vmModel);
            }
        }

        if (!vmModelList.isEmpty()) {
            MailMessage mailMessage = new MailMessage("乐视云平台web-portal系统", user.getEmail(),
                    "乐视云平台web-portal系统通知", "cloudvm/createVms.ftl",
                    mailMessageModel);
            mailMessage.setHtml(true);
            OpenStackServiceImpl.getOpenStackServiceGroup().getDefaultEmailSender()
                    .sendMessage(mailMessage);
        }
		return "success";
	}

}
