/*
 *  Copyright (c) 2011 乐视网（letv.com）. All rights reserved
 * 
 *  LETV PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.letv.common.web.rest;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.servlet.view.InternalResourceView;

import com.letv.common.exception.CodedValidationException;
import com.letv.common.exception.CommonError;
import com.letv.common.exception.EntityNotFoundException;
import com.letv.common.exception.ErrorCodeConstant;
import com.letv.common.exception.NotFoundException;
import com.letv.common.exception.SecurityServiceException;
import com.letv.common.exception.ServiceException;
import com.letv.common.exception.SignatureException;
import com.letv.common.exception.ValidateException;


/**
 * This is the {@literal abstract} controller to centralized the exception handler for
 * common {@link #Exception} into one place.
 * It would expected all Spring MVC 3.0 controller extends from this class as currently spring
 * {@link #AnnotationMethodHandlerExceptionResolver} lacks of the ability to apply the common exception hander
 * to all controllers.
 * <p>
 * Please note that this way is {@literal controller} centric, if we including {@literal ErrorCode}, such as
 * @ResponseStatus(HttpStatus.NOT_FOUND) here, it seems it can not capture the same errorCode thrown by other servlets
 * in the same application.
 * <br />
 * So we still keep the error page mapping in {@literal web.xml} for application scope purpose.
 * 
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
public abstract class BaseController {

    private static final Logger log = LoggerFactory.getLogger(BaseController.class);
    
    private HttpServletRequest request;
    
    
    
    /**
	 * @param request the request to set
	 */
    @Autowired
    @Required
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

    @ExceptionHandler({NoSuchRequestHandlingMethodException.class})
    public ModelAndView handleBadRequest(NoSuchRequestHandlingMethodException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("The page not found " + path, ex);

        ModelAndView mav = composeModelAndView(path, HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, "The page not found " + path, ex);
        return mav;
    }

    /**
     * 406 (Not Acceptable).
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public ModelAndView handleNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("The media type is not acceptable when access " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.NOT_ACCEPTABLE.toString(), HttpStatus.NOT_ACCEPTABLE, "The media type is not acceptable when access " + path, ex);
        return mav;
    }

    /**
     * 415 (Unsupported Media Type) into one place.
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ModelAndView handleNotAcceptable(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("The media type is upsupported when access " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, "The media type is upsupported when access " + path, ex);
        return mav;
    }

    /**
     * HttpMessageNotReadableException.
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ModelAndView handleMessageReadException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String path = request.getPathInfo();
       
        log.warn("Can't read from stream when access " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, "Can't read from stream when access " + path, ex);
        return mav;
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ModelAndView handleRequestParameterMissing(MissingServletRequestParameterException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("The required http parameter is missing when access " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, "The required http parameter is missing when access " + path, ex);
        return mav;
    }

    /**
     * HttpMessageNotWritableException.
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({HttpMessageNotWritableException.class})
    public ModelAndView handleMessageWriteException(HttpMessageNotWritableException ex, HttpServletRequest request) {
        String path = request.getPathInfo();
        System.out.println(path);
        log.warn("Can't write to stream when access " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, "Can't write to stream when access " + path, ex);
        return mav;
    }

    /**
     * Can be take place when using xml binding or no binder was registered for a customized type.
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({TypeMismatchException.class})
    public ModelAndView handleTypeMismatchException(TypeMismatchException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("Type is mismatch when access " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, "Type is mismatch when access " + path, ex);
        return mav;
    }

    @ExceptionHandler({ServiceException.class})
    public ModelAndView handleServiceException(ServiceException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("Can't accomplish the request because of service layer error when access " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, "Can't accomplish the request because of service layer error when access " + path, ex);
        return mav;
    }

    @ExceptionHandler({SecurityServiceException.class})
    public ModelAndView handleSecurityServiceException(SecurityServiceException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("Can't accomplish the request because of SecurityServiceException when access " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, "Can't accomplish the request because of SecurityServiceException when access " + path, ex);
        return mav;
    }

    @ExceptionHandler({SignatureException.class})
    public ModelAndView handleSecurityServiceException(SignatureException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("Can't accomplish the request because of SignatureException when access " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.FORBIDDEN.toString(), HttpStatus.FORBIDDEN, "Can't accomplish the request because of SignatureException when access " + path, ex);
        return mav;
    }

    @ExceptionHandler({DataAccessException.class})
    public ModelAndView handleDataAccessException(DataAccessException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("Can't accomplish the request because of data layer error when access " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, "Can't accomplish the request because of data layer error when access " + path, ex);
        return mav;
    }

    @ExceptionHandler({CodedValidationException.class})
    public ModelAndView handleCodedValidationException(CodedValidationException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn(ex.getErrorCode() + " when access " + path, ex);
        ModelAndView mav = composeModelAndView(path, ex.getErrorCode(), HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        return mav;
    }

    @ExceptionHandler({NotFoundException.class})
    public ModelAndView handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn(ex.getEntity() + " was not found when access " + path, ex);
        ModelAndView mav = composeModelAndView(path, ex.getEntity() + "." + "NotFound", HttpStatus.NOT_FOUND, ErrorCodeConstant.ERROR_CODE_NOT_FOUND + "." + ex.getEntity() + " when access to " + path, ex);
        return mav;
    }
    
    @ExceptionHandler({EntityNotFoundException.class})
    public ModelAndView handleNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn(ex.getEntity() + " was not found when access " + path, ex);
        ModelAndView mav = composeModelAndView(path, ex.getEntity() + "." + "NotFound", HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        return mav;
    }
    
    @ExceptionHandler({ValidateException.class})
    public ModelAndView handleNotFoundException(ValidateException ex, HttpServletRequest request) {
    	String path = request.getPathInfo();
    	
    	log.warn("Can't accomplish the request because of data layer error when access " + path, ex);
    	ModelAndView mav = composeModelAndView(path, HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, "Can't accomplish the request because of data layer error when access " + path, ex);
        return mav;
    }

    /**
     * This is the fallback for all other UncaughtException.
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({Exception.class})
    public ModelAndView handleUncaughtException(Exception ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("Can't accomplish the request because an unexpected error when access " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, "Can't accomplish the request because an unexpected error when access " + path, ex);
        return mav;
    }

// This old implementation will output html
//
//    private ModelAndView composeModelAndView(String path, HttpStatus status, String message, Exception ex) {
//        ModelAndView mav = new ModelAndView(COMMON_ERROR);
//
//        CommonError commonError = new CommonError();
//        commonError.setPath(path);
//        commonError.setStatus(status);
//        commonError.setMessage(message);
//        // only output exception information when debug
//        if (log.isDebugEnabled()) {
//            commonError.setException(ex.toString());
//        }
//
//        mav.addObject(COMMON_ERROR_ATTRIBUTE, commonError);
//        return mav;
//    }
    /**
     * return the json representation of CommonError.
     *
     * @param path
     * @param status
     * @param message
     * @param ex
     * @return
     */
    private ModelAndView composeModelAndView(String path, String status, HttpStatus httpStatusCode, String message, Exception ex) {
        // we have to pass in the whole path here (including the prefix 'api') as the path for redirect should be relative to the context path

    	String extension = getExtension(path);
    	String method = request.getMethod();
    	ModelAndView mv = new ModelAndView();
    	if(HttpMethod.POST.toString().equals(method) || HttpMethod.PUT.toString().equals(method))
    	{
    		CommonError error = new CommonError();
    		error.setPath(path);
    		error.setHttpStatusCode(httpStatusCode.value());
    		error.setStatus(status);
    		error.setException(ex.getMessage());
    		error.setMessage(message);
    		mv.addObject(error);
    	}
    	else
    	{
    		mv.setView(new InternalResourceView("/api/error/errorHandler"+ extension +"?path=" + path + "&status=" + status + "&httpStatusCode=" + httpStatusCode.value()
                + "&message=" + message + "&exception=" + ex));
    	}
    	
    	return mv;
    }
    
    private String getExtension(String path) {
    	if(StringUtils.isNotEmpty(path)) {
    		if(path.indexOf(".") != -1) {
    			return path.substring(path.indexOf("."));
    		}
    	}
    	return "";
    }
}
