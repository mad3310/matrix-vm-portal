package com.letv.portal.controller.billing;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.service.openstack.billing.CheckResult;
import com.letv.portal.service.product.IProductManageService;
import com.letv.portal.service.product.IProductService;
import com.letv.portal.service.subscription.ISubscriptionService;

/**Program Name: BaseProductController <br>
 * Description:  产品相关api<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月28日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/billing")
public class ProductController {
	
	@Autowired
	IProductService productService;
	@Autowired
	ISubscriptionService subscriptionService;
	@Autowired
	IProductManageService productManageService;
	
	
	@RequestMapping(value="/product/{id}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject queryProductDetail(@PathVariable Long id, ResultObject obj) {
		obj.setData(this.productService.queryProductDetailById(id));
		return obj;
	}
	
	
	
	
	@RequestMapping(value="/buy/{id}",method=RequestMethod.POST)   
	public @ResponseBody ResultObject buy(@PathVariable Long id, String paramsData, String displayData, ResultObject obj) {
		//去服务提供方验证参数是否合法
		CheckResult validateResult = productManageService.validateParamsDataByServiceProvider(id, paramsData);
		if(!validateResult.isSuccess()) {
			obj.setResult(0);
			obj.addMsg(validateResult.getFailureReason());
			return obj;
		}
		if(!productManageService.buy(id, paramsData, displayData, obj)) {
			obj.setResult(0);
			obj.addMsg("参数合法性验证失败");
		}
		
		return obj;
	}
	
	/**
	  * @Title: dailyConsume
	  * @Description: 每日消费api接口
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年10月21日 下午5:06:48
	  */
	@RequestMapping(value="/product/daily/consume",method=RequestMethod.GET)  
	public @ResponseBody ResultObject dailyConsume(ResultObject obj) {
		obj.setData(this.productService.dailyConsume());
		return obj;
	}
	

	@RequestMapping(value="/service/warn",method=RequestMethod.GET)   
	public @ResponseBody ResultObject serviceWarn(HttpServletRequest request,ResultObject obj) {
		this.subscriptionService.serviceWarn();
		return obj;
	}
	
}
