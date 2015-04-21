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
import com.letv.common.exception.ValidateException;
import com.letv.portal.constant.Constant;
import com.letv.portal.enumeration.CbaseBucketStatus;
import com.letv.portal.enumeration.MclusterStatus;
import com.letv.portal.model.HostModel;
import com.letv.portal.model.UserModel;
import com.letv.portal.model.cbase.CbaseBucketModel;
import com.letv.portal.model.cbase.CbaseClusterModel;
import com.letv.portal.model.cbase.CbaseContainerModel;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.cbase.ICbaseBucketService;
import com.letv.portal.service.cbase.ICbaseClusterService;
import com.letv.portal.service.cbase.ICbaseContainerService;

@Component("baseCbaseTaskService")
public class BaseTask4CbaseServiceImpl implements IBaseTaskService {

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
		CbaseBucketModel cbase = this.getCbaseBucket(params);
		CbaseClusterModel cluster = this.getCbaseCluster(params);

		if (tr.isSuccess()) {
			cbase.setStatus(CbaseBucketStatus.NORMAL.getValue());
			cluster.setStatus(MclusterStatus.RUNNING.getValue());
			Map<String, Object> emailParams = new HashMap<String, Object>();
			emailParams.put("cacheName", cbase.getBucketName());
			this.email4User(emailParams, cbase.getCreateUser(),
					"cache/createCache.ftl");
		} else {
			cbase.setStatus(CbaseBucketStatus.BUILDFAIL.getValue());
			cluster.setStatus(MclusterStatus.BUILDFAIL.getValue());
		}
		this.cbaseBucketService.updateBySelective(cbase);
		this.cbaseClusterService.updateBySelective(cluster);
	}

	@Override
	public void callBack(TaskResult tr) {

	}

	@SuppressWarnings("unchecked")
	public TaskResult analyzeRestServiceResult(String result) {
		TaskResult tr = new TaskResult();
		Map<String, Object> map = transToMap(result);
		if (map == null) {
			tr.setSuccess(false);
			tr.setResult("api connect failed");
			return tr;
		}
		Map<String, Object> meta = (Map<String, Object>) map.get("meta");

		boolean isSucess = Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String
				.valueOf(meta.get("code")));
		tr.setSuccess(isSucess);
		if (isSucess) {
			Map<String, Object> response = (Map<String, Object>) map
					.get("response");
			tr.setResult((String) response.get("message"));
			tr.setParams(response);
		} else {
			tr.setResult((String) meta.get("errorType") + ":"
					+ (String) meta.get("errorDetail"));
		}
		return tr;

	}

	public void buildResultToMgr(String buildType, String result,
			String detail, String to) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("buildType", buildType);
		map.put("buildResult", result);
		map.put("errorDetail", detail);
		MailMessage mailMessage = new MailMessage("乐视云平台web-portal系统",
				StringUtils.isEmpty(to) ? ERROR_MAIL_ADDRESS : to,
				"乐视云平台web-portal系统通知", "buildForMgr.ftl", map);
		defaultEmailSender.sendMessage(mailMessage);
	}

	public void email4User(Map<String, Object> params, Long to, String ftlName) {
		UserModel user = this.userService.selectById(to);
		if (null != user) {
			MailMessage mailMessage = new MailMessage("乐视云平台web-portal系统",
					user.getEmail(), "乐视云平台web-portal系统通知", ftlName, params);
			mailMessage.setHtml(true);
			defaultEmailSender.sendMessage(mailMessage);
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> transToMap(String params) {
		if (StringUtils.isEmpty(params))
			return null;
		ObjectMapper resultMapper = new ObjectMapper();
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		try {
			jsonResult = resultMapper.readValue(params, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResult;
	}

	public String transToString(Object params) {
		if (params == null)
			return null;
		ObjectMapper resultMapper = new ObjectMapper();
		String jsonResult = "";
		try {
			jsonResult = resultMapper.writeValueAsString(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResult;
	}

	public Long getLongFromObject(Object o) {
		Long value = null;
		if (o instanceof String)
			value = Long.parseLong((String) o);
		if (o instanceof Integer)
			value = Long.parseLong(((Integer) o).toString());
		if (o instanceof Long)
			value = (Long) o;

		return value;
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

}
