package com.letv.portal.service.openstack.resource;

public class Region {
	private String id;
	private String name;
	private String countryId;
	private String country;
	private String cityId;
	private String city;
	private int number;

	public Region() {
	}
	
	public Region(String id, String name, String countryId, String country,
			String cityId, String city, int number) {
		this();
		this.id = id;
		this.name = name;
		this.countryId = countryId;
		this.country = country;
		this.cityId = cityId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

}
