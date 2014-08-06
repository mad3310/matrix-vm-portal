package com.letv.common.exception;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.letv.common.util.NumberUtil;
import com.letv.common.util.ResponseUtil;


/**
 * 异常处理
 * 
 * @author Ben Liu
 */
public class DefaultMappingExceptionResolver extends SimpleMappingExceptionResolver {
    private Log logger = LogFactory.getLog(this.getClass());
    @Resource
    private CommonsMultipartResolver multipartResolver;

    @Override
    protected ModelAndView doResolveException(HttpServletRequest req, HttpServletResponse res, Object handler,
            Exception e) {
        if (e instanceof MaxUploadSizeExceededException) {
            // 上传文件大小炒超过限制的异常处理
            long maxSize = multipartResolver.getFileUpload().getSizeMax();
            float max = (float) maxSize / (1024 * 1024);
            String maxFileSizeText = NumberUtil.round(max);
            logger.error("图片文件大小超过限制（最大为" + maxFileSizeText + "M）。", e);
            ResponseUtil.write(res, "ERROR:图片大小超过了最大限制(" + maxFileSizeText + "M)");
            return null;
        } else {
            logger.error(e, e);
            ModelAndView view = new ModelAndView("/error");
            return view;
        }
    }

}
