/*
 *  Copyright (c) 2011 乐视网（letv.com）. All rights reserved
 * 
 *  LETV PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.letv.common.exception;

/**
 * A {@link RuntimeException} wraps service layer exception.
 *
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
public class ServiceException extends RuntimeException {

    /**
     * Creates a new instance of <code>ServiceException</code> without detail message.
     */
    public ServiceException() {
    }

    /**
     * Constructs an instance of <code>ServiceException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
