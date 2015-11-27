package com.letv.portal.controller.user;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Producer;
import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.mms.cache.ICacheService;
import com.letv.mms.cache.factory.CacheFactory;
import com.letv.portal.constant.Constant;

@Controller
@RequestMapping("/kaptcha")
public class CaptchaController {
	
	@Autowired
	private Producer captchaProducer;
	
	@Autowired
	private SessionServiceImpl sessionService;
	
	private ICacheService<?> cacheService = CacheFactory.getCache();
	 
	
	private final static Logger logger = LoggerFactory.getLogger(CaptchaController.class);
	

	/**
	  * @Title: showCaptcha
	  * @Description: 生成验证码
	  * @param request
	  * @param response
	  * @throws Exception void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年11月27日 下午2:20:00
	  */
	@RequestMapping(method = RequestMethod.GET)
	public void showCaptcha(HttpServletRequest request,HttpServletResponse response) throws Exception {
		// create the text for the image  
        String capText = captchaProducer.createText();
        cacheService.set(Constant.KAPTCHA_COOKIE_NAME + this.sessionService.getSession().getUserId(), capText, 600000);
	        
		response.setDateHeader("Expires", 0);  
        // Set standard HTTP/1.1 no-cache headers.  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        // Set standard HTTP/1.0 no-cache header.  
        response.setHeader("Pragma", "no-cache");  
        // return a jpeg  
        response.setContentType("image/jpeg");  
        // create the image with the text  
        BufferedImage bi = captchaProducer.createImage(capText);  
        ServletOutputStream out = response.getOutputStream();  
        // write the data out  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {
            out.close();  
        }  
	}
	
	 /**
	  * @Title: validate
	  * @Description: 验证验证码
	  * @param request
	  * @param result
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年11月27日 下午2:20:19
	  */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ResultObject validate(HttpServletRequest request, ResultObject result) {
		 String kaptcha = request.getParameter("kaptcha");
		 if(StringUtils.isEmpty(kaptcha)) {
			 result.setData(false);
			 return result;
		 }
		 String cacheKaptcha = (String) cacheService.get(Constant.KAPTCHA_COOKIE_NAME + this.sessionService.getSession().getUserId(), null);
		 result.setData(kaptcha.equals(cacheKaptcha));
		 return result;
	}
	
	/**
	  * @Title: isNeed
	  * @Description: 是否需要验证码
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年11月27日 下午5:03:28
	  */
	@RequestMapping(value="/isNeed",method=RequestMethod.GET)   
	public @ResponseBody ResultObject isNeed(ResultObject obj){
		Object o = cacheService.get(Constant.KAPTCHA_VERIFY_COUNT + this.sessionService.getSession().getUserId(), null);
		if(o!=null && (Integer)o>=3) {
			obj.setData(true);
		} else {
			obj.setData(false);
		}
		return obj;
	}
	
}
