package com.letv.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.model.ErrorMailMessageModel;
import com.letv.common.util.function.IRetry;


/**
 * Created by zhouxianguang on 2015/11/20.
 */
@Service("retryUtil")
public class RetryUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(RetryUtil.class);
	
	@Autowired
	ExceptionEmailServiceUtil exceptionEmailServiceUtil;

    /**
      * @Title: retry
      * @Description: 重试机制
      * @param process 执行方法
      * @param times 执行次数
      * @param mailMessageModel 当执行相应次数后未成功发送邮件   
      * @throws 
      * @author lisuxiao
      * @date 2015年12月29日 下午2:06:26
      */
    public void retry(IRetry<Object, Boolean> process, int times, ErrorMailMessageModel mailMessageModel) {
    	Object result = null;
        for (int i = 0; i < times; i++) {
        	try {
				result = process.execute();
				if (process.analyzeResult(result)) {
				    return;
				}
			} catch (Exception e1) {
				if(null != mailMessageModel) {
		        	exceptionEmailServiceUtil.sendErrorEmail(mailMessageModel.getExceptionMessage(), 
							mailMessageModel.getExceptionContent()+"返回结果:"+result, mailMessageModel.getRequestUrl());
		        }
			}
            try {
				Thread.sleep(1000l);
			} catch (InterruptedException e) {
				logger.error("重试方法中线程sleep出错", e);
			}
        }
        if(null != mailMessageModel) {
        	exceptionEmailServiceUtil.sendErrorEmail(mailMessageModel.getExceptionMessage(), 
					mailMessageModel.getExceptionContent()+"返回结果:"+result, mailMessageModel.getRequestUrl());
        }
    }
}
