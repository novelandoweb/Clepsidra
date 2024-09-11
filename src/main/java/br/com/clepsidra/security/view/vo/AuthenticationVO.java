package br.com.clepsidra.security.view.vo;

import java.io.Serializable;

public record AuthenticationVO(String token) implements Serializable {
}
