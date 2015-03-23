package com.letv.common.exception;

import com.letv.log.bean.LogBean;


/**
 *
 * @author yangjz
 */
public class ValidateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1248271345177273339L;
	private LogBean logBean;
    
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

	public ValidateException(String msg, LogBean logBean) {
		super(msg);
		this.logBean = logBean;
	}

	public ValidateException(String message, LogBean logBean, Throwable cause) {
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
