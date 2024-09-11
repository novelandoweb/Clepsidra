package br.com.clepsidra.failure.exception;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    public ResourceNotFoundException(Object id) {
        super("Recurso não encontrado: Id= "+id);
    }
}
