/*
 *  Copyright (c) 2011 乐视网（letv.com）. All rights reserved
 * 
 *  LETV PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.letv.common.exception;

/**
 * A {@link RuntimeException} wraps exception while invoking RestTemplate call.
 * The Client can catch this exception then extract the {@link CommonError} object, which encapsulates
 * the error code and error message.
 *
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
public class RestClientException extends RuntimeException {

    /**
     * Creates a new instance of <code>RestClientException</code> without detail message.
     */
    public RestClientException() {
    }

    /**
     * Constructs an instance of <code>RestClientException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public RestClientException(String msg) {
        super(msg);
    }

    public RestClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestClientException(Throwable cause) {
        super(cause);
    }
}
