package com.letv.common.exception;

/**
 *
 * @author yangjz
 */
public class PythonException extends RuntimeException {

	public PythonException() {
	}

	public PythonException(String msg) {
		super(msg);
	}

	public PythonException(String message, Throwable cause) {
		super(message, cause);
	}

	public PythonException(Throwable cause) {
		super(cause);
	}
}
