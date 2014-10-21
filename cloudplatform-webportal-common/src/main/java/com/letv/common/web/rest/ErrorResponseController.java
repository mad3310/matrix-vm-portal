/*
 *  Copyright (c) 2011 乐视网（letv.com）. All rights reserved

 * 
 *  LETV PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.letv.common.web.rest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.exception.CommonError;


/**
 * A controller to return the {@link CommonError} when Exception throw from the {@literal Spring MVC} and its under-layers.
 * <p>
 * <b>This is a workground for JSON support, by default, the response only supports {@literal HTML}.</b>
 *    See detail <a href="https://jira.springsource.org/browse/SPR-6902">@ResponseBody does not work with @ExceptionHandler</a>
 * </p>
 * @author Pi Zhi Gang(pizhigang@letv.com)
 */
@Controller
@Component
public class ErrorResponseController {

    private static final Logger log = LoggerFactory.getLogger(ErrorResponseController.class);

    /**
     * We cannot specify the 'headers = "Accept=application/json"' as this handler was routered in by a ModelAndView.
     * @param commonError
     * @return 
     */
    @RequestMapping(value = "/error/errorHandler", method = {RequestMethod.GET, RequestMethod.POST}/*, headers = "Accept=application/json"*/)
    public //@ResponseBody
            CommonError responseWithError(
            @RequestParam("path") String path,
            @RequestParam("status") String status,
            @RequestParam("httpStatusCode") int httpStatusCode,
            @RequestParam("message") String message,
            @RequestParam("exception") String exception,
            HttpServletRequest request) {
    	System.out.println(path);
        CommonError commonError = new CommonError();
        commonError.setPath(path);
        commonError.setStatus(status);
        commonError.setHttpStatusCode(HttpStatus.valueOf(httpStatusCode).value());
        commonError.setMessage(message);

        // only show exceptoin message in debug mode
        if (log.isDebugEnabled()) {
            commonError.setException(exception);
        } else {
            commonError.setException("");
        }
        
        log.info("Rest response erro: {}", commonError);
        return commonError;
    }

    /**
     * We cannot specify the 'headers = "Accept=application/json"' as this handler was routered in by a ModelAndView.
     * @param commonError
     * @return 
     */
    @RequestMapping(value = "/error/errorHandler", method = RequestMethod.POST/*, headers = "Accept=application/json"*/)
    public //@ResponseBody
            CommonError responseWithError(@RequestBody CommonError error) {
        log.info("Rest response erro: {}", error);
        return error;
    }
    
    @RequestMapping("/error/ccc")
    public @ResponseBody String
             responseWithError() {
       // log.info("Rest response erro: {}", error);
        return "dddd";
    }
}
