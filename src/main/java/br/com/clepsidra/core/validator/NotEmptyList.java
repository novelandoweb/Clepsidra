package br.com.clepsidra.core.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {NotEmptyListValidator.class})
@Target(FIELD)
@Retention(RUNTIME)
public @interface NotEmptyList {
    String message() default "A lista bão pode ser vazia";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
