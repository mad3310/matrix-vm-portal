package com.letv.common.exception;

public class NoSessionException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3394710434258751338L;

	public NoSessionException(String message, Throwable cause) {
	      super(message, cause);
	}
	
	public NoSessionException(String message) {
	      super(message);
	}

}
