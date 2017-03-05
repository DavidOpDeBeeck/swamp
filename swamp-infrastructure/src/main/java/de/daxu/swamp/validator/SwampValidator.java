package de.daxu.swamp.validator;

import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;

public class SwampValidator extends LocalValidatorFactoryBean {

    @Override
    protected Object getRejectedValue(String field, ConstraintViolation<Object> violation, BindingResult bindingResult) {
        try {
            return super.getRejectedValue(field, violation, bindingResult);
        } catch (Exception e) {
            return null;
        }
    }
}
