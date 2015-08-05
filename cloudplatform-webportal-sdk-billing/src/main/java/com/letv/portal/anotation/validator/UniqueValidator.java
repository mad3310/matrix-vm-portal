package com.letv.portal.anotation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.service.IBaseElementService;

public class UniqueValidator implements ConstraintValidator<Unique, String> {

	@Autowired
	private IBaseElementService baseElementService;
	
	
	@Override
	public void initialize(Unique unique) {
		
	}

	@Override
	public boolean isValid(String name, ConstraintValidatorContext context) {
		return this.baseElementService.isUnique(name);
	}

}
