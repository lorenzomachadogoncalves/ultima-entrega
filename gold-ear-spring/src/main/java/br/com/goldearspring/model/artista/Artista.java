package br.com.goldearspring.model.artista;

import br.com.goldearspring.model.disco.Disco;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Artista")
@Data
//@NoArgsConstructor
//@AllArgsConstructor
@RequiredArgsConstructor
public class Artista {
    public Artista(String nome) {
        this.nome = nome;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artista")
    private Long id;

    @UuidGenerator
    private UUID uuid;

    @Column(nullable = false, length = 80)
    private String nome;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "artista_disco",
            joinColumns = @JoinColumn(name = "artista_id"),
            inverseJoinColumns = @JoinColumn(name = "disco_id")
    )
    private List<Disco> discos;

}
