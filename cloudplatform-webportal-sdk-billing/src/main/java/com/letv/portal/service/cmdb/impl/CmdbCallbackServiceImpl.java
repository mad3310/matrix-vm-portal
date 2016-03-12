package com.letv.portal.service.cmdb.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.letv.common.model.ErrorMailMessageModel;
import com.letv.common.util.HttpClient;
import com.letv.common.util.RetryUtil;
import com.letv.common.util.function.IRetry;
import com.letv.lcp.cloudvm.dispatch.DispatchCenter;
import com.letv.lcp.cloudvm.enumeration.ServiceTypeEnum;
import com.letv.lcp.cloudvm.service.compute.IComputeService;
import com.letv.portal.model.cloudvm.lcp.CloudvmServerModel;
import com.letv.portal.model.order.OrderSub;
import com.letv.portal.model.product.ProductInfoRecord;
import com.letv.portal.service.cmdb.ICmdbCallbackService;
import com.letv.portal.service.lcp.ICloudvmServerService;
import com.letv.portal.service.product.IProductInfoRecordService;

@Service("cmdbCallbackService")
public class CmdbCallbackServiceImpl implements ICmdbCallbackService {
	
	@Autowired
	protected DispatchCenter dispatchCenter;
	@Autowired
    private ICloudvmServerService cloudvmServerService;
	@Autowired
	private IProductInfoRecordService productInfoRecordService;
	@Value("${cmdb.callback.url}")
	private String cmdbCallbackUrl;
	@Autowired
	RetryUtil retryUtil;
	
	private final static Logger logger = LoggerFactory.getLogger(CmdbCallbackServiceImpl.class);

	@Override
	public String getPhysicalHostNameByServerInfo(CloudvmServerModel serverModel) {
		IComputeService computeService = (IComputeService) dispatchCenter.getServiceByStrategy(ServiceTypeEnum.COMPUTE);
		return computeService.getPhysicalHostNameByServerInfo(serverModel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveVmInfo(String groupId) {
		final List<ProductInfoRecord> records = this.productInfoRecordService.selectByGroupId(groupId);
		for (ProductInfoRecord record : records) {
			if(StringUtils.isEmpty(record.getInstanceId())) {
				logger.info("该组订单中还有未完成流程!groupId:{}", groupId);
				return;
			}
		}
		
		final ErrorMailMessageModel errorMailMessage = new ErrorMailMessageModel(null, cmdbCallbackUrl, null, 
				"cmdb接口保存虚机信息失败", "传入参数groupId:"+groupId);
		
		retryUtil.retry(new IRetry<Object, Boolean>() {
			@Override
			public Object execute() {
				List<Object> infos = new ArrayList<Object>();
				Map<String, Object> recordParam = null;
				for (ProductInfoRecord record : records) {
					Map<String, Object> params = new HashMap<String, Object>();
					String serverInstanceId = record.getInstanceId().substring(record.getInstanceId().indexOf("_")+1);
					CloudvmServerModel serverModel = cloudvmServerService.selectByServerInstanceId(serverInstanceId);
					String hostName = getPhysicalHostNameByServerInfo(serverModel);
					logger.info("=============查询私有云主机宿主机name============="+hostName);
					
					params.put("inip", serverModel.getPrivateIp());
					recordParam = JSONObject.parseObject(record.getParams(), Map.class);
					params.put("callbackid", recordParam.get("callbackId"));
					params.put("hostname", hostName);
					params.put("outip", "");
					infos.add(params);
				}
				final Map<String, String> postInfo = new HashMap<String, String>();
				postInfo.put("callbackdata", JSONObject.toJSONString(infos));
				postInfo.put("orderid", (String)recordParam.get("orderId"));
				
				return HttpClient.post(cmdbCallbackUrl, postInfo, 1000, 2000, null, null);
			}

			@Override
			public Boolean analyzeResult(Object r) {
				return analyzeRestServiceResult(r.toString());
			}
			
		}, 1, errorMailMessage);
	}
	
	@SuppressWarnings("unchecked")
	private boolean analyzeRestServiceResult(String result) {
		Map<String, Object> map = JSONObject.parseObject(result, Map.class);
		
		if(map.get("result")!=null && "1".equals((String)map.get("result"))) {
			return true;
		}
		return false;
	}
	
}
