package com.letv.portal.clouddb.controller;

import java.awt.image.BufferedImage;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Producer;
import com.letv.common.result.ResultObject;
import com.letv.common.util.CookieUtil;
import com.letv.mms.cache.ICacheService;
import com.letv.mms.cache.factory.CacheFactory;
import com.letv.portal.constant.Constant;

@Controller
@RequestMapping("/kaptcha")
public class CaptchaController {
	
	@Autowired
	private Producer captchaProducer;
	
	private ICacheService<?> cacheService = CacheFactory.getCache();
	 
	
	private final static Logger logger = LoggerFactory.getLogger(CaptchaController.class);
	

	@RequestMapping(method = RequestMethod.GET)
	public void showCaptcha(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		// create the text for the image  
        String capText = captchaProducer.createText();
		String captchaId = UUID.randomUUID().toString();
        CookieUtil.addCookie(response, Constant.KAPTCHA_COOKIE_NAME, captchaId, 30);
        cacheService.set(Constant.KAPTCHA_COOKIE_NAME + captchaId, capText);
	        
        logger.debug(capText);
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
	
	 @RequestMapping(method = RequestMethod.POST)
	 public @ResponseBody ResultObject validate(HttpServletRequest request,ResultObject result) {
		 String kaptcha = request.getParameter("kaptcha");
		 if(StringUtils.isEmpty(kaptcha)) {
			 result.setData(false);
			 return result;
		 }
		 
		 String captchaId = null;
		 captchaId = CookieUtil.getCookieByName(request, Constant.KAPTCHA_COOKIE_NAME).getValue();
	     if (StringUtils.isEmpty(captchaId)) {
	    	 result.setData(false);
	    	 return result;
	     }
	     
	     String cacheKaptcha = (String) cacheService.get(Constant.KAPTCHA_COOKIE_NAME + captchaId, null);
         result.setData(kaptcha.equals(cacheKaptcha));
         return result;
 		
	 }
	
}
