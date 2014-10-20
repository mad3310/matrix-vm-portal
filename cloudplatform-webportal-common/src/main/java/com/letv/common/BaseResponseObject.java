package com.letv.common;


import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "responseMessage")
public class BaseResponseObject {
    protected String path;
    protected String status;
    protected int httpStatusCode;
    protected String message;
    
	public BaseResponseObject() {
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
