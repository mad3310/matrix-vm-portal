package com.letv.common.exception;

/**
 *
 * @author yangjz
 */
public class ApiNotFoundException extends RuntimeException {

	public ApiNotFoundException() {
	}

	public ApiNotFoundException(String msg) {
		super(msg);
	}

	public ApiNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiNotFoundException(Throwable cause) {
		super(cause);
	}
}
