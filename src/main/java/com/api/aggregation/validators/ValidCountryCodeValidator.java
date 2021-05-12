package com.api.aggregation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidCountryCodeValidator implements ConstraintValidator<ValidCountryCode,List<String>> {

    @Override
    public void initialize(ValidCountryCode validCountryCode) {

    }


    @Override
    public boolean isValid(List<String> strings, ConstraintValidatorContext constraintValidatorContext) {
        return strings.stream().allMatch(s-> (s.length() == 2 && s.matches("[A-Z]+")));
    }

}
