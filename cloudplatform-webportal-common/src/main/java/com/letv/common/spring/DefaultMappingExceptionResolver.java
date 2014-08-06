package com.letv.common.spring;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.letv.common.exception.CommonException;
import com.letv.common.exception.ValidateException;
import com.letv.common.result.ResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 异常处理
 * 
 * @author liunaikun
 */
public class DefaultMappingExceptionResolver extends SimpleMappingExceptionResolver {
	private static final String ERROR_SYSTEM_ERROR = "系统出现异常，请稍后再试";

	private Logger logger = LoggerFactory.getLogger(DefaultMappingExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest req, HttpServletResponse res, Object handler,
            Exception e) {
		if (e instanceof ValidateException) {
			responseJson(req, res, e.getMessage());
            return null;
		} else if (e instanceof CommonException) {
			responseJson(req, res, ERROR_SYSTEM_ERROR);

			logger.error(ERROR_SYSTEM_ERROR, e);
			return null;
		} else {
			responseJson(req, res, ERROR_SYSTEM_ERROR);

			logger.error(ERROR_SYSTEM_ERROR, e);
			return null;
        }
    }

	/**
	 * 生成json并返回给客户端
	 * 
	 * @param req
	 * @param res
	 * @param message
	 */
	private void responseJson(HttpServletRequest req, HttpServletResponse res, String message) {
    	PrintWriter out = null;
		try {
			res.setCharacterEncoding("utf-8");
			out = res.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
//			logger.error("在取得PrintWriter时出现异常",e1);
		}
		ResultObject resultObject = new ResultObject(0);
		resultObject.addMsg(message);
		out.print(JSON.toJSONString(resultObject, SerializerFeature.WriteMapNullValue));
		out.flush();
		out.close();

	}
}
