package com.api.aggregation.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ValidCountryCodeValidatorTest {

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void countryCodesInvalid() throws Exception {

        List<String> countryCodes = Arrays.asList("CN","NL","TRK");
        ValidCountryCodeValidator validCountryCodeValidator = new ValidCountryCodeValidator();

        boolean isValid = validCountryCodeValidator.isValid(countryCodes, constraintValidatorContext);


        assertThat(isValid).isFalse();

    }

    @Test
    public void countryCodesInvalidNum() throws Exception {

        List<String> countryCodes = Arrays.asList("CN","NL","T1");
        ValidCountryCodeValidator validCountryCodeValidator = new ValidCountryCodeValidator();

        boolean isValid = validCountryCodeValidator.isValid(countryCodes, constraintValidatorContext);


        assertThat(isValid).isFalse();

    }


    @Test
    public void countryCodesValid() throws Exception {

        List<String> countryCodes = Arrays.asList("CN","NL");
        ValidCountryCodeValidator validCountryCodeValidator = new ValidCountryCodeValidator();

        boolean isValid = validCountryCodeValidator.isValid(countryCodes, constraintValidatorContext);


        assertThat(isValid).isTrue();

    }

}