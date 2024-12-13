package br.com.goldearspring.model.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MembroRepository extends JpaRepository<Membro, Long> {
}
