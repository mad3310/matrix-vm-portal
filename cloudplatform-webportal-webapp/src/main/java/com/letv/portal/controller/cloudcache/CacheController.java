package com.letv.portal.controller.cloudcache;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.HttpUtil;
import com.letv.common.util.StringUtil;
import com.letv.portal.model.cbase.CbaseBucketModel;
import com.letv.portal.proxy.ICbaseProxy;
import com.letv.portal.service.cbase.ICbaseBucketService;

@Controller
@RequestMapping("/cache")
public class CacheController {

	@Autowired(required = false)
	private SessionServiceImpl sessionService;
	@Autowired
	private ICbaseBucketService cbaseBucketService;
	@Autowired
	private ICbaseProxy cbaseProxy;

	private final static Logger logger = LoggerFactory
			.getLogger(CacheController.class);

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResultObject list(Page page,
			HttpServletRequest request, ResultObject result) {
		Map<String, Object> params = HttpUtil.requestParam2Map(request);
		params.put("createUser", sessionService.getSession().getUserId());

		String cacheName = (String) params.get("cacheName");
		if (!StringUtils.isEmpty(cacheName))
			params.put("cacheName", StringUtil.transSqlCharacter(cacheName));

		result.setData(this.cbaseBucketService.findPagebyParams(params, page));
		return result;
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ResultObject save(CbaseBucketModel cbaseBucketModel,
			ResultObject obj) {
		if (cbaseBucketModel == null
				|| StringUtils.isEmpty(cbaseBucketModel.getBucketName())) {
			throw new ValidateException("参数不合法");
		} else {

		}

		cbaseBucketModel.setCreateUser(this.sessionService.getSession()
				.getUserId());
		this.cbaseProxy.saveAndBuild(cbaseBucketModel);
		return obj;
	}

}
