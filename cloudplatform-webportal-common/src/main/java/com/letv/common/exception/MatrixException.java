package com.letv.common.exception;

/**
 *
 * @author yangjz
 * 
 * throw new MatrixException("异常",e);
 * 
 * throw new MatrixException("异常");
 */
public class MatrixException extends RuntimeException {
	
	private static final long serialVersionUID = 5019992532532040690L;

	private String userMessage;
	private Exception e;
	
	public MatrixException() {}
	
	public MatrixException(String userMessage) {
		this.userMessage = userMessage;
	}
	
	public MatrixException(String userMessage,Exception e) {
		this.userMessage = userMessage;
		this.e = e;
	}
	
	public String getUserMessage() {
		return userMessage;
	}
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	public Exception getE() {
		return e;
	}
	public void setE(Exception e) {
		this.e = e;
	}
	
	
}
