package br.com.clepsidra.security.controller;

import br.com.clepsidra.domain.entity.Usuario;
import br.com.clepsidra.domain.repository.UsuarioRepository;
import br.com.clepsidra.failure.exception.ParamInputNullException;
import br.com.clepsidra.security.mapper.SecurityMapper;
import br.com.clepsidra.security.service.TokenService;
import br.com.clepsidra.security.view.dto.AuthenticationDto;
import br.com.clepsidra.security.view.dto.RegisterDto;
import br.com.clepsidra.security.view.vo.AuthenticationVO;
import br.com.clepsidra.security.view.vo.RegisterVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthenticationContoller {
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository repository;
    private final SecurityMapper mapper;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationVO> login(@RequestBody @Valid AuthenticationDto login) {
        log.info("Login do usuário= {}", login.email());
        var usernamePassword = new UsernamePasswordAuthenticationToken(login.email(), login.senha());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok().body(new AuthenticationVO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterVO> register(@RequestBody @Valid RegisterDto register) {
        log.info("Iniciando a inclusão do usuário = {}", register.getEmail());
        if(repository.findByEmail(register.getEmail()).isPresent())
            throw new ParamInputNullException(BAD_REQUEST, "Usuário já cadastrado");
        register.setSenha(new BCryptPasswordEncoder().encode(register.getSenha()));
        var user = new Usuario(register.getEmail(), register.getNickname(), register.getSenha());
        repository.save(user);
        log.info("Usuário adicionado ID = {}", user.getId());
        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(mapper.toVO(user));
    }

}
