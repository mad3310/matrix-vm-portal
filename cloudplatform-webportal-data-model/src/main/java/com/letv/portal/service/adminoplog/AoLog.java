package com.letv.portal.service.adminoplog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.letv.portal.model.adminoplog.AoLogType;
import com.letv.portal.service.adminoplog.impl.DefaultMethodLogFormater;
import com.letv.portal.service.adminoplog.impl.RequestMethodLogFormatter;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD })
public @interface AoLog {
	// /**
	// * class: log template,ex:xxx ${methodLog}<br>
	// * method: log template,ex:xxx ${p0.currentPage}
	// *
	// * @return
	// */
	// public String value() default "";

	public Class<? extends MethodLogFormatter> formatter() default MethodLogFormatter.class;

	/**
	 * class: ignore methods of class<br>
	 * method: ignore method
	 * 
	 * @return
	 */
	public boolean ignore() default false;

	public String desc() default "";

	public AoLogType type() default AoLogType.NULL;
	
	public String module() default "";
}
