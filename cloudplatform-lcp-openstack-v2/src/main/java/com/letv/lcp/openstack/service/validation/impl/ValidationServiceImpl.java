package com.letv.lcp.openstack.service.validation.impl;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Service;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.service.validation.IValidationService;

/**
 * Created by zhouxianguang on 2015/11/5.
 */
@Service
public class ValidationServiceImpl implements IValidationService {

    private Validator validator;

    @PostConstruct
    public void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    public <T> void validate(T var1, Class... var2) throws OpenStackException {
        throwExceptionIfNecessary(validator.validate(var1, var2));
    }

    private <T> void throwExceptionIfNecessary(Set<ConstraintViolation<T>> violations) throws OpenStackException {
        if (!violations.isEmpty()) {
            StringBuilder messageBuilder = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                if (messageBuilder.length() > 0) {
                    messageBuilder.append(",");
                }
                messageBuilder.append(violation.getPropertyPath().toString());
                messageBuilder.append(violation.getMessage());
            }
            throw new OpenStackException(messageBuilder.toString(), "参数不合法");
        }
    }
}
