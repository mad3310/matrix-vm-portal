/*
 *  Copyright (c) 2011 乐视网（letv.com）. All rights reserved
 * 
 *  LETV PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.letv.common.exception;

/**
 * A {@link RuntimeException} wraps Security Service layer exception.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class SecurityServiceException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1977092683764020677L;

	/**
     * Creates a new instance of <code>SecurityServiceException</code> without detail message.
     */
    public SecurityServiceException() {
    }

    /**
     * Constructs an instance of <code>SecurityServiceException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public SecurityServiceException(String msg) {
        super(msg);
    }

    public SecurityServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityServiceException(Throwable cause) {
        super(cause);
    }
}
