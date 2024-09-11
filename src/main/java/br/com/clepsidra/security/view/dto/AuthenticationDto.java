package br.com.clepsidra.security.view.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record AuthenticationDto(@NotBlank(message = "{campo.email.obrigatorio}")
                       String email,
                       @NotBlank(message = "{campo.senha.obrigatorio}")
                       @Size(min = 6, max = 16, message = "{campo.senha.invalido}")
                       String senha) implements Serializable {
}
