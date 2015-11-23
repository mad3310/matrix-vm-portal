/*
 *  Copyright (c) 2011 乐视网（letv.com）. All rights reserved
 * 
 *  LETV PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.letv.common.exception;

/**
 * A RuntimeException defined for no record was found with a <b>entity</b> and freestyle message.
 * The errorCode can be used by the client to interpret a particular error.
 * 
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
public class NotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6832433538357181178L;
	private String entity;

    /**
     * Creates a new instance of <code>NotFoundException</code> without detail message.
     */
    public NotFoundException() {
    }

    /**
     * Constructs an instance of <code>NotFoundException</code> with the specified detail message.
     * @param message the detail message.
     */
    public NotFoundException(String entity, String message) {
        super(message);
        this.entity = entity;
    }

    public NotFoundException(String entity, String message, Throwable cause) {
        super(message, cause);
        this.entity = entity;
    }

    public String getEntity() {
        return entity;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.getClass().getName()).append("[");
        buffer.append("entity=").append(entity);
        buffer.append(", message=").append(getMessage());
        buffer.append("]");
        return buffer.toString();
    }
}
