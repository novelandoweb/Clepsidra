package br.com.clepsidra.security.view.vo;

import java.io.Serializable;

public record RegisterVO(String email, String nickname) implements Serializable {
}
