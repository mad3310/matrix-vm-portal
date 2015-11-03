package com.letv.portal.service.openstack.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.letv.common.exception.MatrixException;
import com.letv.common.exception.ValidateException;
import com.letv.common.result.ResultObject;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.UserOperationException;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
public class ExceptionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);

    public static Throwable getCause(Throwable ex) {
        Throwable cause = ex.getCause();
        if (cause != null) {
            return cause;
        } else {
            return ex;
        }
    }

    public static void throwException(Throwable ex) throws OpenStackException {
        if (ex instanceof ValidateException) {
            throw (ValidateException) ex;
        } else if (ex instanceof MatrixException) {
            throw (MatrixException) ex;
        } else if (ex instanceof OpenStackException) {
            throw (OpenStackException) ex;
        }
        throw new OpenStackException("后台错误", ex);
    }

    public static void throwMatrixException(Exception ex) throws MatrixException {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        } else if (ex instanceof OpenStackException) {
            throw ((OpenStackException) ex).matrixException();
        } else {
            throw new MatrixException("后台错误", ex);
        }
    }

    public static String getUserMessage(Exception e) {
        if (e instanceof OpenStackException) {
            return ((OpenStackException) e).getUserMessage();
        } else {
            return "后台错误";
        }
    }

    public static void processBillingException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        OpenStackServiceImpl.getOpenStackServiceGroup().getErrorEmailService().sendExceptionEmail(ex, "计费系统", null, "");
    }

    public static void logAndEmail(Exception e) {
        logger.error(e.getMessage(), e);
        OpenStackServiceImpl.getOpenStackServiceGroup().getErrorEmailService().sendExceptionEmail(e, "", null, "");
    }

    public static ResponseEntity getResponseEntityFromException(Exception ex) {
        ResultObject result = new ResultObject();
        result.setResult(0);
        if (ex instanceof OpenStackException) {
            result.addMsg(((OpenStackException) ex).getUserMessage());
        } else {
            result.addMsg("后台错误");
        }
        String resultJson = JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (!(ex instanceof UserOperationException)) {
            ExceptionUtil.logAndEmail(ex);
        }
        return new ResponseEntity<String>(resultJson, headers, HttpStatus.OK);
    }
}
