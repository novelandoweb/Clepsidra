package br.com.clepsidra.failure;

import br.com.clepsidra.failure.error.StandardError;
import br.com.clepsidra.failure.error.ValidationError;
import br.com.clepsidra.failure.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class ResourceExceptionHandler {
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(
            ObjectNotFoundException e, HttpServletRequest request) {
        var error = StandardError.builder().timestamp(Instant.now())
                .status(NOT_FOUND.value())
                .error(NOT_FOUND.getReasonPhrase())
                .message(e.getMessage())
                .path(request.getRequestId())
                .build();
        log.error("[ERROR] payload={}", error, e);
        return ResponseEntity.status(NOT_FOUND).body(error);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<StandardError> usernameNotFound(
            UsernameNotFoundException e, HttpServletRequest request) {
        var error = StandardError.builder().timestamp(Instant.now())
                .status(NOT_FOUND.value())
                .error(NOT_FOUND.getReasonPhrase())
                .message(e.getMessage())
                .path(request.getRequestId())
                .build();
        log.error("[ERROR] payload={}", error, e);
        return ResponseEntity.status(NOT_FOUND).body(error);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<StandardError> respondeStatusException(
            ResponseStatusException e, HttpServletRequest request) {
        final int STATUS = e.getStatusCode().value();
        var REASON =  Objects.requireNonNull(resolve(STATUS)).getReasonPhrase();
        var error = StandardError.builder().timestamp(Instant.now())
                .status(STATUS)
                .error(REASON)
                .message(e.getMessage())
                .path(request.getRequestId())
                .build();
        log.error("[ERROR] payload={}", error, e);
        return ResponseEntity.status(STATUS).body(error);
    }

    @ExceptionHandler(ParamInputNullException.class)
    public ResponseEntity<StandardError> resourceNotFound(
            ParamInputNullException e, HttpServletRequest request) {
        var error = StandardError.builder().timestamp(Instant.now())
                .status(NOT_FOUND.value())
                .error(NOT_FOUND.getReasonPhrase())
                .message(e.getReasonMessage())
                .path(request.getRequestId())
                .build();
        log.error("[ERROR] payload={}", error, e);
        return ResponseEntity.status(NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<StandardError>> handleMethodNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request){
        List<StandardError> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(erro -> {
                    return StandardError.builder().timestamp(Instant.now())
                            .status(BAD_REQUEST.value())
                            .error(erro.getDefaultMessage())
                            .message("Erro de validação do campo")
                            .path(request.getRequestId())
                            .build();
                }).toList();
        log.error("[ERROR] payload={}", errors, ex);
        return ResponseEntity.status(BAD_REQUEST).body(errors);
    }
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(
            ResourceNotFoundException e, HttpServletRequest request) {
        var error = StandardError.builder().timestamp(Instant.now())
                .status(BAD_REQUEST.value())
                .error("Erro no banco de dados")
                .message(e.getMessage())
                .path(request.getRequestId())
                .build();
        log.error("[ERROR] payload={}", error, e);
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    @ExceptionHandler(FileReadException.class)
    public ResponseEntity<StandardError> internalError(
            ResourceNotFoundException e, HttpServletRequest request) {
        var error = StandardError.builder().timestamp(Instant.now())
                .status(INTERNAL_SERVER_ERROR.value())
                .error("Erro no servidor ao ler arquivo de configuração")
                .message(e.getMessage())
                .path(request.getRequestId())
                .build();
        log.error("[ERROR] payload={}", error, e);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(UtilException.class)
    public ResponseEntity<StandardError> nonAuthorized(
            ResourceNotFoundException e, HttpServletRequest request) {
        var error = StandardError.builder().timestamp(Instant.now())
                .status(NON_AUTHORITATIVE_INFORMATION.value())
                .error("Usuário não autorizado")
                .message(e.getMessage())
                .path(request.getRequestId())
                .build();
        log.error("[ERROR] payload={}", error, e);
        return ResponseEntity.status(NON_AUTHORITATIVE_INFORMATION).body(error);
    }

    @ExceptionHandler(TokenGenerationException.class)
    public ResponseEntity<StandardError> tokenGeneration(
            TokenGenerationException e, HttpServletRequest request) {
        var error = StandardError.builder().timestamp(Instant.now())
                .status(NON_AUTHORITATIVE_INFORMATION.value())
                .error("Erro ao gerar token")
                .message(e.getMessage())
                .path(request.getRequestId())
                .build();
        log.error("[ERROR] payload={}", error, e);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> objectNotUniqueReactive(DataIntegrityViolationException e,
                                                                 HttpServletRequest request){
        var error = StandardError.builder().timestamp(Instant.now())
                .status(BAD_REQUEST.value())
                .error(BAD_REQUEST.getReasonPhrase())
                .message(verrifica(e.getMessage()))
                .path(request.getRequestId())
                .build();
        log.error("[ERROR] payload={}", error, e);
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<StandardError> obejctNotUniqueReactive(WebExchangeBindException ex,
                                                                 HttpServletRequest request){
        var error = new ValidationError(Instant.now(), request.getRequestId(),
                BAD_REQUEST.value(), "Validation Error", "Erros de validação dos atributos do usuário");
        ex.getBindingResult().getFieldErrors().stream().forEach(error::addError);
        log.error("[ERROR] payload={}", error, ex);
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    public String verrifica(String message) {
        if (message.contains("categoria")) {
            return "Categoria já cadastrada";
        } else if (message.contains("usuario")) {
            return "Usuário com email ou nickname já cadastrado";
        } else{
            return BAD_REQUEST.getReasonPhrase();
        }

    }
}
