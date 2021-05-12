package com.api.aggregation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidShipmentTrackNumberValidator implements ConstraintValidator<ValidShipmentTrackNumber,List<Integer>> {

    @Override
    public void initialize(ValidShipmentTrackNumber validShipmentTrackNumber) {

    }


    @Override
    public boolean isValid(List<Integer> shipmentNumbers, ConstraintValidatorContext constraintValidatorContext) {
        return shipmentNumbers.stream().allMatch(n-> n.toString().matches("\\d{9}"));
    }

}
