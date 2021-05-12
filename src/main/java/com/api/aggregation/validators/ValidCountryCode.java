package com.api.aggregation.validators;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidCountryCodeValidator.class)
public @interface ValidCountryCode {

    String message() default "List cannot contain invalid country codes";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
