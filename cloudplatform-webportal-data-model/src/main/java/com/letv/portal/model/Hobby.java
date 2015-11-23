/**
 * Created on Oct 24, 2011
 */
package com.letv.portal.model;

import java.io.Serializable;

/**
 * @author liyunhui
 *
 */
public class Hobby implements Serializable {

	private String hobbyId;

	public String getHobbyId() {
		return hobbyId;
	}

	public void setHobbyId(String hobbyId) {
		this.hobbyId = hobbyId;
	}
	
	public String toString() {
		return "Hobby :" + getHobbyId();
	}
	
}
