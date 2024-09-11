package br.com.clepsidra.domain.repository;

import br.com.clepsidra.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByEmailOrNickname(String email, String nickname);
  Optional<UserDetails> findByEmail(String email);
  @Query("SELECT u FROM Usuario u WHERE u.id = :id AND u.status = 'ATIVO'")
  Optional<Usuario> buscaUsuarioAtivo(@Param("id") Long id);
  @Query("SELECT u FROM Usuario u WHERE u.status = 'ATIVO'")
  Optional<List<Usuario>> buscaUsuariosAtivos();

}
