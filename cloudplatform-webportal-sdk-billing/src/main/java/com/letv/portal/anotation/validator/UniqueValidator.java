package com.letv.portal.anotation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;

import com.letv.portal.service.IBaseService;
import com.letv.portal.service.base.IBaseElementService;

public class UniqueValidator extends ApplicationObjectSupport implements ConstraintValidator<Unique, String> {

	@Autowired
	private IBaseElementService baseElementService;
	
	private String service;
	
	@Override
	public void initialize(Unique unique) {
		this.service = unique.service();
	}

	@Override
	public boolean isValid(String name, ConstraintValidatorContext context) {
		return ((IBaseService<?>)getApplicationContext().getBean(this.service)).isUnique(name);
	}

}
