package com.letv.common.exception;

import com.letv.log.bean.LogBean;


public class CommonException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8597829683133573474L;
	/**
	 * 
	 */
	private LogBean logBean;

	/**
	 * Creates a new instance of <code>SecurityServiceException</code> without
	 * detail message.
	 */
	public CommonException() {
	}

	/**
	 * Constructs an instance of <code>SecurityServiceException</code> with the
	 * specified detail message.
	 * 
	 * @param msg
	 *            the detail message.
	 */
	public CommonException(String msg) {
		super(msg);
	}

	public CommonException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommonException(Throwable cause) {
		super(cause);
	}

	public CommonException(String msg, LogBean logBean) {
		super(msg);
		this.logBean = logBean;
	}

	public CommonException(String message, LogBean logBean, Throwable cause) {
		super(message, cause);
		this.logBean = logBean;
	}

	public LogBean getLogBean() {
		return logBean;
	}

	public void setLogBean(LogBean logBean) {
		this.logBean = logBean;
	}

}
