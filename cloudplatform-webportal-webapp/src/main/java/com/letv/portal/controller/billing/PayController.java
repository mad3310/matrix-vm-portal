package com.letv.portal.controller.billing;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.letv.common.exception.CommonException;
import com.letv.common.exception.ValidateException;
import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.HttpUtil;
import com.letv.portal.proxy.IDbProxy;
import com.letv.portal.service.order.IOrderSubService;
import com.letv.portal.service.pay.IPayService;
import com.letv.portal.service.product.IProductInfoRecordService;

/**
 * 支付接口
 * @author lisuxiao
 *
 */
@Controller
@RequestMapping("/pay")
public class PayController {
	
	private final static Logger logger = LoggerFactory.getLogger(PayController.class);
	
	@Autowired
	IPayService payService;
	@Autowired
	private IDbProxy dbProxy;
	@Autowired
	private IOrderSubService orderSubService;
	@Autowired
	private IProductInfoRecordService productInfoRecordService;
	@Autowired(required=false)
	private SessionServiceImpl sessionService;

	/**
	  * @Title: pay
	  * @Description: 去支付
	  * @param orderId
	  * @param request
	  * @param response
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月17日 下午5:04:06
	  */
	@RequestMapping(value="/{orderNumber}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject pay(@PathVariable String orderNumber, HttpServletRequest request,HttpServletResponse response, ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		Map<String, Object> ret = this.payService.pay(orderNumber, params);
		if(null!=ret.get("response") && true==(Boolean)ret.get("response")) {
			try {
				response.sendRedirect((String)ret.get("responseUrl"));
			} catch (IOException e) {
				logger.error("pay inteface sendRedirect had error, ", e);
				throw new CommonException(e);
			}
		}
		obj.setData(ret);
		return obj;
	}
	
	
	/**
	  * @Title: callback
	  * @Description: 支付完成后回调
	  * @param orderId
	  * @param request
	  * @param response void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月17日 下午5:11:28
	  */
	@RequestMapping(value="/callback",method=RequestMethod.GET)   
	public void callback(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		Boolean b = this.payService.callback(params);
		try {
			response.getWriter().write(b.toString());
		} catch (IOException e) {
			logger.error("callback response had error : ", e);
		}
	}
	
	/**
	  * @Title: queryStat
	  * @Description: 查询支付结果
	  * @param orderId
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月17日 下午5:13:43
	  */
	@RequestMapping(value="/queryStat/{orderNumber}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject queryStat(@PathVariable String orderNumber, ResultObject obj) {
		Map<String, Object> ret = this.payService.queryState(orderNumber);
		if(ret==null) {
			obj.setResult(0);
		} else {
			obj.setData(ret);
		}
		return obj;
	}
	
	@RequestMapping(value="/qrcodeImage/{orderNumber}",method=RequestMethod.GET)
	public void generateQRcode(@PathVariable String orderNumber, HttpServletRequest request, HttpServletResponse response) {
		logger.info("请求微信生成二维码，userId=" + sessionService.getSession().getUserId() + ", 订单编码=" + orderNumber);
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		Map<String,Object> ret = this.payService.pay(orderNumber, params);
		String content = (String) ret.get("wxurl");// 内容
		if(content!=null) {
			int width = 200; // 图像宽度
			int height = 200; // 图像高度
			String format = "png";
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			try {
				BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
				OutputStream outputStream = response.getOutputStream();
				MatrixToImageWriter.writeToStream(bitMatrix, format, outputStream);
				outputStream.flush();
			} catch (Exception e) {
				logger.error("生产二维码异常:", e);
				throw new ValidateException("生产二维码异常", e);
			}
		} else {
			logger.info("订单已支付，或数据异常!");
		}
		
		
	}
	
}
