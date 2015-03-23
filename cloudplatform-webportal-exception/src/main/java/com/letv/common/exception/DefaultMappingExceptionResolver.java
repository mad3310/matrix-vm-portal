package com.letv.common.exception;

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
import com.letv.log.LoggerFactory;
import com.letv.log.bean.LogBean;
import com.letv.log.service.ILogService;


/**
 * 异常处理
 * 
 * @author liunaikun
 */
public class DefaultMappingExceptionResolver extends SimpleMappingExceptionResolver {
	private static final String ERROR_SYSTEM_ERROR = "系统出现异常，请稍后再试";

	private static ILogService logger = LoggerFactory.getLogger(DefaultMappingExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest req, HttpServletResponse res, Object handler,
            Exception e) {
		if (e instanceof ValidateException) {
			responseJson(req, res, e.getMessage());
            return null;
		} else if (e instanceof CommonException) {
			responseJson(req, res, ERROR_SYSTEM_ERROR);

			LogBean logBean = ((CommonException) e).getLogBean();
			if (logBean == null) {
				logBean = createLogBean(req, res, ERROR_SYSTEM_ERROR, null);
			}
			logger.error(logBean);
			return null;
		} else {
			responseJson(req, res, ERROR_SYSTEM_ERROR);

			LogBean logBean = createLogBean(req, res, ERROR_SYSTEM_ERROR, "doResolveException");
			logger.error(logBean);
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
			LogBean logBean = createLogBean(req, res, "在取得PrintWriter时出现异常", "responseJson");
			logger.error(logBean);
		}
		ResultObject resultObject = new ResultObject(0);
		resultObject.addMsg(message);
		out.print(JSON.toJSONString(resultObject, SerializerFeature.WriteMapNullValue));
		out.flush();
		out.close();

	}
    
	/**
	 * 创建LogBean
	 * 
	 * @return
	 */
	private LogBean createLogBean(HttpServletRequest req, HttpServletResponse res, String message, String methdoName) {
		LogBean logBean = new LogBean();
		logBean.setUsedMethodName(methdoName);
		logBean.setRequestIp(req.getRemoteAddr());
		logBean.setRequestPort(String.valueOf(req.getRemotePort()));
		logBean.setErrorResult(message);

		return logBean;
	}
}
