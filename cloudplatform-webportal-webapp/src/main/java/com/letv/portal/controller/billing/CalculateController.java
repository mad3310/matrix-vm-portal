package com.letv.portal.controller.billing;

import java.util.HashMap;
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
import com.letv.common.util.HttpUtil;
import com.letv.portal.dao.base.IBaseRegionDao;
import com.letv.portal.model.base.BaseRegion;
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
	

	@RequestMapping(value="/price/{id}",method=RequestMethod.POST)   
	public @ResponseBody ResultObject queryProductPrice( @PathVariable Long id, HttpServletRequest request, ResultObject obj) {
		Map<String,Object> map = HttpUtil.requestParam2Map(request);
		Double ret = null;
		
		Long regionId = productService.getRegionIdByCode((String)map.get("region"));
		if(regionId!=null) {
			map.put("region", regionId);
		}
		
		if(id==2) {//云主机走自己的验证和计算
			if(hostProductService.validateData(id, map)) {
				ret =  hostCalculateService.calculatePrice(id, map);
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
			obj.setData(ret);
		}
		return obj;
	}
}
