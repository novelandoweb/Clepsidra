package br.com.clepsidra.failure.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ParamInputNullException extends ResponseStatusException {
    public ParamInputNullException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
    public String getReasonMessage(){
        return super.getReason();
    }
}
