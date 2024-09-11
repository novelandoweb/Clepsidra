package br.com.clepsidra.core.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class NotSpacesValidator implements ConstraintValidator<NotSpaces, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Objects.isNull(value) || value.trim().length() == value.length();
    }
}
