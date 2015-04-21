package com.letv.portal.proxy.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.exception.ValidateException;
import com.letv.portal.model.cbase.CbaseBucketModel;
import com.letv.portal.model.task.service.ITaskEngine;
import com.letv.portal.proxy.ICbaseProxy;
import com.letv.portal.python.service.ICbasePythonService;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.cbase.ICbaseBucketService;
import com.letv.portal.service.cbase.ICbaseClusterService;
import com.letv.portal.service.cbase.ICbaseContainerService;

@Component
public class CbaseProxyImpl extends BaseProxyImpl<CbaseBucketModel> implements
		ICbaseProxy {

	private final static Logger logger = LoggerFactory
			.getLogger(CbaseProxyImpl.class);

	@Autowired
	private ICbaseBucketService cbaseBucketService;
	@Autowired
	private ICbasePythonService cbasePythonService;
	@Autowired
	private ICbaseClusterService cbaseClusterService;
	@Autowired
	private ICbaseContainerService cbaseContainerService;
	@Autowired
	private ITaskEngine taskEngine;

	@Value("${error.email.to}")
	private String ERROR_MAIL_ADDRESS;

	@Autowired
	private ITemplateMessageSender defaultEmailSender;

	@Override
	public void saveAndBuild(CbaseBucketModel cbaseBucketModel) {
		if (cbaseBucketModel == null)
			throw new ValidateException("参数不合法");
		Map<String, Object> params = this.cbaseBucketService
				.save(cbaseBucketModel);
		this.build(params);
	}

	private void build(Map<String, Object> params) {
		this.taskEngine.run("CBASE_BUY", params);
	}

	@Override
	public IBaseService<CbaseBucketModel> getService() {
		return cbaseBucketService;
	}

}
