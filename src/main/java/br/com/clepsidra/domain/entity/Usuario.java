package br.com.clepsidra.domain.entity;

import br.com.clepsidra.domain.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static br.com.clepsidra.domain.enums.Status.ATIVO;
import static br.com.clepsidra.domain.enums.Status.INATIVO;

@Entity
@Table(name = "usuario_login")
@NoArgsConstructor
@Getter
@Setter
@ToString(of = {"nickname", "email"})
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String nickname;
    private String password;
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;
    @Column(name = "data_desativacao")
    private LocalDateTime dataDesativacao;
    @Enumerated(EnumType.STRING)
    private Status status;
    private boolean moderador;

    public Usuario(@NonNull String email, @NonNull String nickname, @NonNull String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.status = ATIVO;
        moderador = false;
        dataCadastro = LocalDateTime.now();
    }

    public void desativarUsurio(){
        status = INATIVO;
        dataDesativacao = LocalDateTime.now();
    }

    public void ativarUsuario(){
        status = ATIVO;
    }

    public void tonarModerador(){
        moderador = true;
        status = ATIVO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(moderador) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
