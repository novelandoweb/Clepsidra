package br.com.clepsidra.failure.exception;

public class FallbackException extends RuntimeException{
    public FallbackException(Throwable cause) {
        super("The system is currently unavailable, Please try again later!", cause);
    }
}
