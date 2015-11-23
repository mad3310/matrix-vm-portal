package com.letv.portal.service.openstack.validation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by zhouxianguang on 2015/11/5.
 */
@Documented
@Constraint(validatedBy = {DivisibleValidator.class})
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Divisible {
    String message() default "{0}不是{1}的倍数";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value();
}
