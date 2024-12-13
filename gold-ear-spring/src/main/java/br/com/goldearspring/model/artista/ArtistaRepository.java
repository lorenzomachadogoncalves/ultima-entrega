package br.com.goldearspring.model.artista;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    Artista findByUuid(UUID uuid);
}
