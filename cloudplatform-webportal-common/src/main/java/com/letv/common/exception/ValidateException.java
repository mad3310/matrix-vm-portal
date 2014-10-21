package com.letv.common.exception;

/**
 *
 * @author yangjz
 */
public class ValidateException extends RuntimeException {

	public ValidateException() {
	}

	public ValidateException(String msg) {
		super(msg);
	}

	public ValidateException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidateException(Throwable cause) {
		super(cause);
	}
}
