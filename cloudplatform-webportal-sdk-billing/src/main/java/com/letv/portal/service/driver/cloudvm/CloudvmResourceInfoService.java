package com.letv.portal.service.driver.cloudvm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.portal.constant.Constants;
import com.letv.portal.enumeration.ProductType;
import com.letv.portal.service.openstack.billing.BillingResource;
import com.letv.portal.service.openstack.billing.ResourceLocator;
import com.letv.portal.service.openstack.billing.ResourceQueryService;

/**
 * 云主机信息
 * @author lisuxiao
 *
 */
@Component
public class CloudvmResourceInfoService {
	
	@Autowired
	ResourceQueryService resourceQueryService;
	
	/**
	  * @Title: getCloudvmResourceNameById
	  * @Description: 调用云主机服务查询服务名称
	  * @param userId
	  * @param productId
	  * @param region
	  * @param id
	  * @return String   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年11月13日 下午2:37:51
	  */
	@SuppressWarnings("unchecked")
	public String getCloudvmResourceNameById(Long userId, Long productId, String region, String id) {
		List<ResourceLocator> ress = new ArrayList<ResourceLocator>();
		Object obj = ProductType.idToType(Constants.SERVICE_PROVIDER_OPENSTACK, productId);
		ress.add(new ResourceLocator().id(id).region(region).type((Class<? extends BillingResource>) obj));
		//调用openstack接口
		Map<ResourceLocator, BillingResource> re = resourceQueryService.getResources(userId, ress);
		if(re!=null && re.values().iterator().hasNext()) {
			return re.values().iterator().next().getName();
		}
		return null;
	}
	
}
