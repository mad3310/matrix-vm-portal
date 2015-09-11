package com.letv.portal.enumeration;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

public class BillingTypeTypeHandle extends EnumOrdinalTypeHandler<BillingType> {
	public BillingTypeTypeHandle() {
		super(BillingType.class);
	}

}
