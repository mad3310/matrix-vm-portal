package com.letv.portal.model;

import java.sql.Date;

import com.letv.common.model.BaseModel;

public class ContactModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -312138777885254435L;

	private Long id;
	private String firstName;
	private String lastName;
	private Date birthDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	// Getter and setter method omitted
	public String toString() {
		return "Contact - Id: " + id + ", First name: " + firstName
				+ ", Last name: " + lastName + ", Birthday: " + birthDate;
	}
}
