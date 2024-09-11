package br.com.clepsidra.failure.exception;

public class TokenGenerationException extends RuntimeException{
    public TokenGenerationException(Throwable cause) {
        super("The system is currently unavailable, Please try again later!", cause);
    }
}
