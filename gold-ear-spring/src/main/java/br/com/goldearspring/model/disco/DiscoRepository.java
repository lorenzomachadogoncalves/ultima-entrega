package br.com.goldearspring.model.disco;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiscoRepository extends JpaRepository<Disco, Long> {
    List<Disco> findByAnoLancamento(Integer anoLancamento);

    boolean existsByNomeAndAnoLancamento(String nome, Integer anoLancamento);

    Optional<Disco> findByUuid(UUID uuid);
}
