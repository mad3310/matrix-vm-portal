package com.letv.portal.service.adminoplog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.letv.portal.service.adminoplog.impl.DefaultClassLogFormatter;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public @interface ClassAoLog {
	public Class<? extends ClassLogFormatter> formatter() default ClassLogFormatter.class;

	/**
	 * class: ignore methods of class<br>
	 * method: ignore method
	 * 
	 * @return
	 */
	public boolean ignore() default false;
	
	public String module() default "";
}
