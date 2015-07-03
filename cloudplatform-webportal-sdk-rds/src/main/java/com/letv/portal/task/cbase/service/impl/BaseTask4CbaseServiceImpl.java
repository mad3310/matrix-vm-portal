package com.letv.portal.task.cbase.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.email.bean.MailMessage;
import com.letv.common.exception.TaskExecuteException;
import com.letv.common.exception.ValidateException;
import com.letv.common.util.JsonUtils;
import com.letv.portal.constant.Constant;
import com.letv.portal.enumeration.CbaseBucketStatus;
import com.letv.portal.enumeration.MclusterStatus;
import com.letv.portal.model.HostModel;
import com.letv.portal.model.UserModel;
import com.letv.portal.model.cbase.CbaseBucketModel;
import com.letv.portal.model.cbase.CbaseClusterModel;
import com.letv.portal.model.cbase.CbaseContainerModel;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.BaseTaskServiceImpl;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.cbase.ICbaseBucketService;
import com.letv.portal.service.cbase.ICbaseClusterService;
import com.letv.portal.service.cbase.ICbaseContainerService;

@Component("baseCbaseTaskService")
public class BaseTask4CbaseServiceImpl extends BaseTaskServiceImpl implements IBaseTaskService {

	private static String cbaseManagePort = "8091";
	@Value("${error.email.to}")
	private String ERROR_MAIL_ADDRESS;
	@Autowired
	private ITemplateMessageSender defaultEmailSender;

	@Autowired
	private IHostService hostService;
	@Autowired
	private ICbaseClusterService cbaseClusterService;
	@Autowired
	private ICbaseBucketService cbaseBucketService;
	@Autowired
	private ICbaseContainerService cbaseContainerService;
	@Autowired
	private IUserService userService;

	private final static Logger logger = LoggerFactory
			.getLogger(BaseTask4CbaseServiceImpl.class);

	@Override
	public void beforExecute(Map<String, Object> params) {
		CbaseBucketModel cbaseBucket = this.getCbaseBucket(params);
		CbaseClusterModel cluster = this.getCbaseCluster(params);
		if (cbaseBucket.getStatus() != CbaseBucketStatus.BUILDDING.getValue()) {
			cbaseBucket.setStatus(CbaseBucketStatus.BUILDDING.getValue());
			this.cbaseBucketService.updateBySelective(cbaseBucket);
		}
		if (cbaseBucket.getStatus() != MclusterStatus.BUILDDING.getValue()) {
			cluster.setStatus(MclusterStatus.BUILDDING.getValue());
			this.cbaseClusterService.updateBySelective(cluster);
		}
	}

	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = new TaskResult();
		if (params == null || params.isEmpty()) {
			tr.setResult("params is empty");
			tr.setSuccess(false);
		}
		tr.setParams(params);
		return tr;
	}

	@Override
	public void rollBack(TaskResult tr) {
		// 发送邮件
		this.buildResultToMgr("OCS服务创建", tr.isSuccess() ? "创建成功" : "创建失败",
				tr.getResult(), ERROR_MAIL_ADDRESS);
		// 业务处理
		this.serviceOver(tr);
	}

	private void serviceOver(TaskResult tr) {
		Map<String, Object> params = (Map<String, Object>) tr.getParams();
		CbaseBucketModel bucket = this.getCbaseBucket(params);
		CbaseClusterModel cluster = this.getCbaseCluster(params);

		if (tr.isSuccess()) {
			bucket.setStatus(CbaseBucketStatus.NORMAL.getValue());
			cluster.setStatus(MclusterStatus.RUNNING.getValue());
			Map<String, Object> emailParams = new HashMap<String, Object>();
			emailParams.put("cacheName", bucket.getBucketName());
			Map<String, Object> moxiParams = cbaseBucketService
					.getMoxiConfig(bucket.getId());
			try {
				emailParams.put("moxiStr", JsonUtils.writeObject(moxiParams));
			} catch (Exception e) {
				throw new TaskExecuteException(
						"email params exception with moxi config");
			}
			this.email4User(emailParams, bucket.getCreateUser(),
					"cache/createCache.ftl");
		} else {
			bucket.setStatus(CbaseBucketStatus.BUILDFAIL.getValue());
			cluster.setStatus(MclusterStatus.BUILDFAIL.getValue());
		}
		this.cbaseBucketService.updateBySelective(bucket);
		this.cbaseClusterService.updateBySelective(cluster);
	}

	@Override
	public void callBack(TaskResult tr) {

	}

	public CbaseBucketModel getCbaseBucket(Map<String, Object> params) {
		Long cacheId = getLongFromObject(params.get("cacheId"));
		if (cacheId == null)
			throw new ValidateException("params's cacheId is null");

		CbaseBucketModel cbaseBucket = this.cbaseBucketService
				.selectById(cacheId);
		if (cbaseBucket == null)
			throw new ValidateException("cbaseBucket is null by cacheId:"
					+ cacheId);

		return cbaseBucket;
	}

	public CbaseClusterModel getCbaseCluster(Map<String, Object> params) {
		Long cbaseClusterId = getLongFromObject(params.get("cbaseClusterId"));
		if (cbaseClusterId == null)
			throw new ValidateException("params's cbaseClusterId is null");

		CbaseClusterModel cbaseCluster = this.cbaseClusterService
				.selectById(cbaseClusterId);
		if (cbaseCluster == null)
			throw new ValidateException(
					"cbaseCluster is null by cbaseClusterId:" + cbaseClusterId);

		return cbaseCluster;
	}

	public HostModel getHost(Long hclusterId) {
		if (hclusterId == null)
			throw new ValidateException("hclusterId is null :" + hclusterId);
		HostModel host = this.hostService.getHostByHclusterId(hclusterId);
		if (host == null)
			throw new ValidateException("host is null by hclusterIdId:"
					+ hclusterId);

		return host;
	}

	public List<CbaseContainerModel> getContainers(Map<String, Object> params) {
		Long cbaseClusterId = getLongFromObject(params.get("cbaseClusterId"));
		if (cbaseClusterId == null)
			throw new ValidateException("params's cbaseClusterId is null");

		List<CbaseContainerModel> cbaseContainers = this.cbaseContainerService
				.selectByCbaseClusterId(cbaseClusterId);
		if (cbaseContainers == null || cbaseContainers.isEmpty())
			throw new ValidateException(
					"cbaseCluster is null by cbaseClusterId:" + cbaseClusterId);
		return cbaseContainers;
	}

	public static String getCbaseManagePort() {
		return cbaseManagePort;
	}

	public static void setCbaseManagePort(String cbaseManagePort) {
		BaseTask4CbaseServiceImpl.cbaseManagePort = cbaseManagePort;
	}

}
