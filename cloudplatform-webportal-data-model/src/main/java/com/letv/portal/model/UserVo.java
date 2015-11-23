package com.letv.portal.model;

import java.io.Serializable;

public class UserVo implements Serializable {

	private static final long serialVersionUID = -7403421757715876594L;
	/*{
		"contacts":"liuhao",
		"countryCode":"86",
		"createdTime":"2015-09-07 15:08:44",
		"email":"liuhao1@letv.com",
		"id":400054,
		"isOld":1,
		"lastUpdateTime":"2015-09-07 15:08:44",
		"mobile":"18510086398",
		"mobileStatus":1,
		"siteDomain":"",
		"siteName":"",
		"siteType":18,
		"userKey":"41edba4711b7619632ba2fc6f6298af6",
		"userStatus":1,
		"userType":2,
		"userUnique":"l7w7u8yt77"
	}*/
	private Long userId;
	private String username;
	private String email;
	private String mobile;
	private Integer mobileStatus;
	
	public UserVo() {
	}
	
	public UserVo(Long userId, String username, String email,String mobile, Integer mobileStatus) {
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.mobile = mobile;
		this.mobileStatus = mobileStatus;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getMobileStatus() {
		return mobileStatus;
	}
	public void setMobileStatus(Integer mobileStatus) {
		this.mobileStatus = mobileStatus;
	}
	
}
