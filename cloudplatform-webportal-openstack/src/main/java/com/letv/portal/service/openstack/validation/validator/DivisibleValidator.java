package com.letv.portal.service.openstack.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;

/**
 * Created by zhouxianguang on 2015/11/5.
 */
public class DivisibleValidator implements ConstraintValidator<Divisible, Number> {
    private int value;
    private String message;

    @Override
    public void initialize(Divisible divisible) {
        this.value = divisible.value();
        this.message = divisible.message();
    }

    @Override
    public boolean isValid(Number number, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = false;
        if (number instanceof Long) {
            Long numberValue = (Long) number;
            if (numberValue % value == 0) {
                isValid = true;
            }
        } else if (number instanceof Integer) {
            Integer numberValue = (Integer) number;
            if (numberValue % value == 0) {
                isValid = true;
            }
        } else if (number instanceof Short) {
            Short numberValue = (Short) number;
            if (numberValue % value == 0) {
                isValid = true;
            }
        } else if (number instanceof Byte) {
            Byte numberValue = (Byte) number;
            if (numberValue % value == 0) {
                isValid = true;
            }
        }

        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    MessageFormat.format(this.message, number, this.value)).addConstraintViolation();
        }

        return isValid;
    }
}
