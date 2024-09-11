package br.com.clepsidra.core.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {NotSpacesValidator.class })
@Target(FIELD)
@Retention(RUNTIME)
public @interface NotSpaces {
    String message() default "O campo não pode conter espaços em brancos no início ou fim";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
