package com.letv.portal.controller.billing;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.common.util.DataFormat;
import com.letv.common.util.HttpUtil;
import com.letv.portal.constant.Constants;
import com.letv.portal.model.base.BaseStandard;
import com.letv.portal.service.calculate.ICalculateService;
import com.letv.portal.service.calculate.IHostCalculateService;
import com.letv.portal.service.product.IHostProductService;
import com.letv.portal.service.product.IProductService;

/**Program Name: CalculateController <br>
 * Description:  计算价格<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月28日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/billing/calculate")
public class CalculateController {
	private final static Logger logger = LoggerFactory.getLogger(CalculateController.class);
	
	@Autowired
	IProductService productService;
	@Autowired
	IHostProductService hostProductService;
	@Autowired
	ICalculateService calculateService;
	@Autowired
	IHostCalculateService hostCalculateService;
	
	/**
	  * @Title: transferParams
	  * @Description: 转换参数（匹配计费参数）
	  * @param params
	  * @param id void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年10月13日 下午3:29:17
	  */
	private void transferParams(Map<String, Object> params, Long id) {
		//多个验证会修改order_time值
		params.put("orderTime", params.get("order_time"));
		if(id==Constants.PRODUCT_VM) {//云主机参数转换
			params.put("os_cpu_ram", params.get("cpu_ram"));
			params.put("os_cpu_ram_type", params.get("cpu_ram"));
			params.put("os_storage", params.get("volumeSize")+"");
			params.put("os_storage_type", params.get("volumeType")+"");
		} else if(id==Constants.PRODUCT_VOLUME) {//云硬盘
			params.put("os_storage", params.get("volumeSize")+"");
			params.put("os_storage_type", params.get("volumeType")+"");
		} else if(id==Constants.PRODUCT_FLOATINGIP) {//公网IP
			
		} else if(id==Constants.PRODUCT_ROUTER) {//路由器
			
		}
	}

	@RequestMapping(value="/price/{id}",method=RequestMethod.POST)   
	public @ResponseBody ResultObject queryProductPrice( @PathVariable Long id, HttpServletRequest request, ResultObject obj) {
		Map<String,Object> map = HttpUtil.requestParam2Map(request);
		BigDecimal ret = null;
		
		Long regionId = productService.getRegionIdByCode((String)map.get("region"));
		if(regionId!=null) {
			map.put("region", regionId);
		}
		
		transferParams(map, id);
			
		if(id==Constants.PRODUCT_VM) {//云主机走自己的验证和计算
			List<BaseStandard> vmBaseStandards = this.productService.selectBaseStandardByProductId(Constants.PRODUCT_VM);
			List<BaseStandard> volumeBaseStandards = this.productService.selectBaseStandardByProductId(Constants.PRODUCT_VOLUME);
			List<BaseStandard> flatingIpBaseStandards = this.productService.selectBaseStandardByProductId(Constants.PRODUCT_FLOATINGIP);
			if(hostProductService.validateData(Constants.PRODUCT_VM, map, vmBaseStandards) && hostProductService.validateData(Constants.PRODUCT_VOLUME, map, volumeBaseStandards) 
					&& hostProductService.validateData(Constants.PRODUCT_FLOATINGIP, map, flatingIpBaseStandards)) {
				ret =  hostCalculateService.calculatePrice(Constants.PRODUCT_VM, map, vmBaseStandards).add(hostCalculateService.calculatePrice(Constants.PRODUCT_VOLUME, map, volumeBaseStandards))
						.add(hostCalculateService.calculatePrice(Constants.PRODUCT_FLOATINGIP, map, flatingIpBaseStandards));
			}
		} else if(id==Constants.PRODUCT_FLOATINGIP || id==Constants.PRODUCT_VOLUME || id==Constants.PRODUCT_ROUTER) {
			List<BaseStandard> baseStandards = this.productService.selectBaseStandardByProductId(id);
			if(hostProductService.validateData(id, map, baseStandards)) {
				ret =  hostCalculateService.calculatePrice(id, map, baseStandards);
			}
		} else {
			if(productService.validateData(id, map)) {//规划的通用逻辑
				ret = calculateService.calculatePrice(id, map);
			}
		}
		if(ret==null) {
			obj.setResult(0);
			obj.addMsg("输入参数不合法");
		} else {
			obj.setData(DataFormat.formatBigDecimalToString(ret));
		}
		return obj;
	}
}
