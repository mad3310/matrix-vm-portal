package com.letv.portal.service.isadmin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.letv.portal.enumeration.IsAdminEnum;


@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD) 
public @interface IsAdminAnnotation { 
	IsAdminEnum isAdmin();
}
