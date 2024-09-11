package br.com.clepsidra.security.service;

import br.com.clepsidra.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static br.com.clepsidra.util.Messages.NAO_ENCONTRADO;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {
    private final UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(NAO_ENCONTRADO
                        + "Usuário: %s".formatted(username)));
    }
}
