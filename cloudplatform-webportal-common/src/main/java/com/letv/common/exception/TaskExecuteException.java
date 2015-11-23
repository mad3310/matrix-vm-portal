package com.letv.common.exception;

/**
 *
 * @author yangjz
 */
public class TaskExecuteException extends RuntimeException {

	public TaskExecuteException() {
	}

	public TaskExecuteException(String msg) {
		super(msg);
	}

	public TaskExecuteException(String message, Throwable cause) {
		super(message, cause);
	}

	public TaskExecuteException(Throwable cause) {
		super(cause);
	}
}
