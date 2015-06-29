package com.letv.portal.service.openstack.resource;

public class Region {
	private String id;
	private String country;
	private String city;
	private int number;

	public Region(String id, String country, String city, int number) {
		this.id = id;
		this.country = country;
		this.city = city;
		this.number = number;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
