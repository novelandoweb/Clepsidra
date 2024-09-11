package br.com.clepsidra.security.view.dto;

import br.com.clepsidra.core.interfaces.Dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class RegisterDto implements Serializable,
        Dto<RegisterDto> {
    @Serial
    private static final long serialVersionUID = 8174238270193997738L;

    @NotBlank(message = "{campo.senha.obrigatorio}")
    @Size(min = 6, max = 16, message = "{campo.senha.invalido}")
    private String email;
    @NotBlank(message = "{campo.senha.obrigatorio}")
    @Size(min = 6, max = 16, message = "{campo.senha.invalido}")
    private String senha;
    @NotBlank(message = "{campo.nickname.obrigatorio}")
    private String nickname;
}
