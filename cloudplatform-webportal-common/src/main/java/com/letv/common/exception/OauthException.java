package com.letv.common.exception;

/**
 *
 * @author yangjz
 */
public class OauthException extends RuntimeException {

	public OauthException() {
	}

	public OauthException(String msg) {
		super(msg);
	}

	public OauthException(String message, Throwable cause) {
		super(message, cause);
	}

	public OauthException(Throwable cause) {
		super(cause);
	}
}
