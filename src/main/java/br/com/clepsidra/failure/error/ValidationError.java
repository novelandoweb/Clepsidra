package br.com.clepsidra.failure.error;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ValidationError extends StandardError implements Serializable {
    @Serial
    private static final long serialVersionUID = 6827904165688676483L;

    private final List<FiltroError> errors = new ArrayList<>();
    @AllArgsConstructor
    @Getter
    private static final class FiltroError {
        private String filename;
        private String message;
    }
    public ValidationError(Instant timestamp, String error, Integer status, String message, String path) {
        super(timestamp, status, error, message, path);
    }
    public void addError(FieldError error) {
        errors.add(new FiltroError(error.getField(), error.getDefaultMessage()));
    }
}
