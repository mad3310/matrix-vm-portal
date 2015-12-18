package com.letv.portal.model.common;

import com.letv.common.model.BaseModel;
import com.letv.portal.enumeration.LoginClient;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Alias("LoginRecord")
public class LoginRecordModel extends BaseModel{

    private static final long serialVersionUID = 2955890158317393649L;

    private Long userId;

    private Date loginTime;
    private String loginIp;

    private LoginClient loginClient;

    private boolean success;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public LoginClient getLoginClient() {
        return loginClient;
    }

    public void setLoginClient(LoginClient loginClient) {
        this.loginClient = loginClient;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
