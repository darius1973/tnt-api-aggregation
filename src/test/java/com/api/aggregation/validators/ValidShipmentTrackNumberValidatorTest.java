package com.api.aggregation.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ValidShipmentTrackNumberValidatorTest {
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void shipmentTrackNumbersInvalid() throws Exception {

        List<Integer> shipmentTrackNumbers = Arrays.asList(123456789,123456789,12345678);
        ValidShipmentTrackNumberValidator validShipmentTrackNumberValidator = new ValidShipmentTrackNumberValidator();

        boolean isValid = validShipmentTrackNumberValidator.isValid(shipmentTrackNumbers, constraintValidatorContext);


        assertThat(isValid).isFalse();

    }

    @Test
    public void shipmentTrackNumbersValid() throws Exception {

        List<Integer> shipmentTrackNumbers = Arrays.asList(123456789,123456789);
        ValidShipmentTrackNumberValidator validShipmentTrackNumberValidator = new ValidShipmentTrackNumberValidator();

        boolean isValid = validShipmentTrackNumberValidator.isValid(shipmentTrackNumbers, constraintValidatorContext);


        assertThat(isValid).isTrue();

    }

}