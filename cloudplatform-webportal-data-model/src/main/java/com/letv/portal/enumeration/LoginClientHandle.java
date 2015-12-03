package com.letv.portal.enumeration;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

public class LoginClientHandle extends EnumOrdinalTypeHandler<LoginClient> {
    public LoginClientHandle() {
        super(LoginClient.class);
    }
}