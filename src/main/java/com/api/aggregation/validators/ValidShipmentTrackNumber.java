package com.api.aggregation.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidShipmentTrackNumberValidator.class)
public @interface ValidShipmentTrackNumber {

    String message() default "List cannot contain invalid shipment numbers";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
