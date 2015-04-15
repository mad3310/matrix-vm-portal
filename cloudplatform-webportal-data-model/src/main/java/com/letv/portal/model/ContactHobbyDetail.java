/**
 * Created on Oct 24, 2011
 */
package com.letv.portal.model;

/**
 * @author liyunhui
 *
 */
public class ContactHobbyDetail {

	private Long contactId;
	private String hobbyId;
	
	public Long getContactId() {
		return contactId;
	}
	
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	
	public String getHobbyId() {
		return hobbyId;
	}
	
	public void setHobbyId(String hobbyId) {
		this.hobbyId = hobbyId;
	}
	
	public String toString() {
		return "Contact Hobby Detail - Contact id: " + contactId
				+ ", Hobby: " + hobbyId;
	}		
	
}
