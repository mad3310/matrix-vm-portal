package com.letv.portal.enumeration;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

public class LoginClientHandler extends EnumOrdinalTypeHandler<LoginClient> {
    public LoginClientHandler() {
        super(LoginClient.class);
    }
}