package br.com.goldearspring.model.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    public Usuario findByEmail(String email);

    Optional<Usuario> findByUuid(UUID uuid);
}
