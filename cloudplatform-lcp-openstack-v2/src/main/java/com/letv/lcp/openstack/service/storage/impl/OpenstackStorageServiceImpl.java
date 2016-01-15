package com.letv.lcp.openstack.service.storage.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.cinder.v1.features.VolumeApi;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.extensions.VolumeAttachmentApi;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Optional;
import com.letv.lcp.cloudvm.listener.VolumeCreateListener;
import com.letv.lcp.cloudvm.model.storage.VolumeCreateConf;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.cloudvm.model.task.VmCreateContext;
import com.letv.lcp.openstack.exception.APINotAvailableException;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.erroremail.ErrorMailMessageModel;
import com.letv.lcp.openstack.service.base.IOpenStackService;
import com.letv.lcp.openstack.service.base.OpenStackServiceGroup;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;
import com.letv.lcp.openstack.service.erroremail.IErrorEmailService;
import com.letv.lcp.openstack.service.jclouds.ApiSession;
import com.letv.lcp.openstack.service.jclouds.IApiService;
import com.letv.lcp.openstack.service.manage.VolumeManager;
import com.letv.lcp.openstack.service.manage.check.Checker;
import com.letv.lcp.openstack.service.manage.impl.VolumeManagerImpl;
import com.letv.lcp.openstack.service.session.IOpenStackSession;
import com.letv.lcp.openstack.service.storage.IOpenstackStorageService;
import com.letv.lcp.openstack.service.validation.IValidationService;
import com.letv.lcp.openstack.util.RandomUtil;
import com.letv.lcp.openstack.util.ThreadUtil;
import com.letv.lcp.openstack.util.function.Function0;
import com.letv.portal.model.cloudvm.CloudvmVolume;

@Service("openstackStorageService")
public class OpenstackStorageServiceImpl implements IOpenstackStorageService  {
	
	private static final Logger logger = LoggerFactory.getLogger(OpenstackStorageServiceImpl.class);
	
	@Autowired
    private IValidationService validationService;
	@Autowired
    private IApiService apiService;
	@Autowired
    private IErrorEmailService errorEmailService;
	@Autowired
    private IOpenStackService openStackService;
	@Autowired
	private VolumeManager volumeManager;
	@Autowired
	private ApiSession apiSession;

	@Override
	public String create(Long userId, VolumeCreateConf storage, VolumeCreateListener listener, Object listenerUserData, Map<String, Object> params) {
		try {
            validationService.validate(storage);
            final String sessionId = RandomUtil.generateRandomSessionId();
            final IOpenStackSession openStackSession = this.openStackService.createSession(userId);
            openStackSession.init(null);
            try {
                CinderApi cinderApi = apiService.getCinderApi(userId, sessionId);
                ((VolumeManagerImpl) openStackSession.getVolumeManager()).create(cinderApi, storage, listener, listenerUserData, params);
            } finally {
                apiService.clearCache(userId, sessionId);
            }
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	errorEmailService.sendExceptionEmail(e, "计费调用创建云硬盘", userId, storage.toString());
        	return e.getMessage();
        }
		return "success";
	};
	
	
	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}


	@SuppressWarnings("unchecked")
	@Override
	public void rollBackWithCreateVmFail(Map<String, Object> params) {
		Long userId = (Long) params.get("userId");
		String uuid = (String)params.get("uuid");
		VMCreateConf2 vmCreateConf = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateConf")), VMCreateConf2.class);
		List<JSONObject> contexts =  JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateContexts")), List.class);
		List<VmCreateContext> vmCreateContexts = new ArrayList<VmCreateContext>();
		
		Checker<Volume> volumeChecker = new Checker<Volume>() {
			@Override
			public boolean check(Volume volume) throws Exception {
				return volume.getStatus() == Volume.Status.CREATING;
			}
		};
		OpenStackServiceGroup openStackServiceGroup = OpenStackServiceImpl.getOpenStackServiceGroup();
		VolumeApi volumeApi = apiService.getCinderApi(userId, uuid).getVolumeApi(vmCreateConf.getRegion());
		for (JSONObject json : contexts) {
			VmCreateContext vmCreateContext = JSONObject.parseObject(json.toJSONString(), VmCreateContext.class);
			vmCreateContexts.add(vmCreateContext);
			if (null != vmCreateContext.getVolumeInstanceId()) {
				final String volumeId = vmCreateContext.getVolumeInstanceId();
				try {
					volumeManager.waitingVolume(volumeApi, volumeId, 100, volumeChecker);
				} catch (OpenStackException e) {
					logger.error(e.getMessage(), e);
		        	errorEmailService.sendExceptionEmail(e, "创建云主机中的公网Ip rollback异常", userId, vmCreateConf.toString());
				}
				boolean isSuccess = volumeApi.delete(volumeId);
				if (isSuccess) {
					vmCreateContext.setVolumeInstanceId(null);
					openStackServiceGroup.getLocalVolumeService().delete(userId, vmCreateConf.getRegion(), volumeId);
				} else {
					openStackServiceGroup.getErrorEmailService()
							.sendErrorEmail(
									new ErrorMailMessageModel()
											.exceptionMessage("创建云主机的云硬盘回滚时删除失败")
											.exceptionParams(MessageFormat.format("userId={0},region={1},volumeId={2}", userId, vmCreateConf.getRegion(), volumeId))
											.toMap());
				}
			}
		}
		params.put("vmCreateContexts", vmCreateContexts);
	}


	@SuppressWarnings("unchecked")
	@Override
	public String addVolume(Map<String, Object> params) {
		Long userId = Long.parseLong((String)params.get("userId"));
		String sessionId = (String)params.get("uuid");
		VMCreateConf2 vmCreateConf = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateConf")), VMCreateConf2.class);
        List<JSONObject> vmCreateContexts = JSONObject.parseObject(JSONObject.toJSONString(params.get("vmCreateContexts")), List.class );
		String region = vmCreateConf.getRegion();

        OpenStackServiceGroup openStackServiceGroup = OpenStackServiceImpl.getOpenStackServiceGroup();
        final VolumeApi volumeApi = apiService.getCinderApi(userId, sessionId).getVolumeApi(region);
        final ServerApi serverApi = apiService.getNovaApi(userId, sessionId).getServerApi(region);
        
        Optional<VolumeAttachmentApi> volumeAttachmentApiOptional = apiService
				.getNovaApi(userId, sessionId).getVolumeAttachmentApi(region);
        try {
			if (!volumeAttachmentApiOptional.isPresent()) {
				throw new APINotAvailableException(VolumeAttachmentApi.class);
			}
			VolumeAttachmentApi volumeAttachmentApi = volumeAttachmentApiOptional.get();
        
			for (JSONObject jsonObject : vmCreateContexts) {
				VmCreateContext vmCreateContext = JSONObject.parseObject(jsonObject.toString(), VmCreateContext. class);
			    boolean volumeUpdated = false;
			    if (vmCreateContext.getVolumeInstanceId() != null) {
			        final String volumeId = vmCreateContext.getVolumeInstanceId();
			        ThreadUtil.waiting(new Function0<Boolean>() {
			            @Override
			            public Boolean apply() throws Exception {
			                Volume volume = volumeApi.get(volumeId);
			                return !(volume == null || volume.getStatus() != Volume.Status.CREATING);
			            }
			        },500L);
			        Server server = serverApi.get(vmCreateContext.getServerInstanceId());
			        Volume volume = volumeApi.get(vmCreateContext.getVolumeInstanceId());
			        if (volume != null && server != null && volume.getStatus() == Volume.Status.AVAILABLE && server.getStatus() != Server.Status.ERROR) {
			        	volumeAttachmentApi.attachVolumeToServerAsDevice(
			                            vmCreateContext.getVolumeInstanceId(),
			                            vmCreateContext.getServerInstanceId(), "");
			            ThreadUtil.waiting(new Function0<Boolean>() {
			            	Volume volume = null;
			                @Override
			                public Boolean apply() throws Exception {
			                    volume = volumeApi.get(volumeId);
			                    if(volume == null){
			                        return false;
			                    }
			                    Volume.Status status = volume.getStatus();
			                    return !((status == Volume.Status.IN_USE
			                            && !volume.getAttachments().isEmpty())
			                            || status == Volume.Status.AVAILABLE
			                            || status == Volume.Status.ERROR);
			                }
			            },500L);
			            Volume v = volumeApi.get(volumeId);
			            openStackServiceGroup.getLocalVolumeService().update(userId,userId,region,v);
			            volumeUpdated = true;
			        }
			    }
			    if (!volumeUpdated) {
			        if (vmCreateContext.getVolumeInstanceId() != null) {
			            Volume volume = volumeApi.get(vmCreateContext.getVolumeInstanceId());
			            if (volume != null) {
			                CloudvmVolume cloudvmVolume = openStackServiceGroup.getCloudvmVolumeService()
			                        .selectByVolumeId(userId, region, volume.getId());
			                if (cloudvmVolume != null) {
			                    final String volumeId = vmCreateContext.getVolumeInstanceId();
			                    ThreadUtil.waiting(new Function0<Boolean>() {
			                        @Override
			                        public Boolean apply() throws Exception {
			                            Volume volume = volumeApi.get(volumeId);
			                            if(volume == null){
			                                return false;
			                            }
			                            Volume.Status status = volume.getStatus();
			                            return status == Volume.Status.ATTACHING || status == Volume.Status.CREATING;
			                        }
			                    },500L);
			                    openStackServiceGroup.getLocalVolumeService().update(userId,userId,region,volumeApi.get(volumeId));
			                }
			            }
			        }
			    }
			}
		} catch (OpenStackException e) {
			logger.error(e.getMessage(), e);
	    	errorEmailService.sendExceptionEmail(e, "云主机创建完成后绑定云硬盘异常", userId, vmCreateConf.toString());
	    	return e.getMessage();
		}
    
		return "success";
	}


}
