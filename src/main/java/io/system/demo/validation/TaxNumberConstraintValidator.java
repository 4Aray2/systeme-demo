package io.system.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TaxNumberConstraintValidator implements ConstraintValidator<TaxNumber, String> {

    private static final String GERMANY_REGEX = "^DE\\d{9}$";
    private static final String ITALY_REGEX = "^IT\\d{11}$";
    private static final String GREECE_REGEX = "^GR\\d{9}$";
    private static final String FRANCE_REGEX = "^FR[A-Z]{2}\\d{9}$";

    @Override
    public void initialize(TaxNumber constraintAnnotation) {
        String taxNumber = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String taxNumber, ConstraintValidatorContext constraintValidatorContext) {
        return taxNumber.matches(GERMANY_REGEX) ||
                taxNumber.matches(ITALY_REGEX) ||
                taxNumber.matches(GREECE_REGEX) ||
                taxNumber.matches(FRANCE_REGEX);
    }
}
