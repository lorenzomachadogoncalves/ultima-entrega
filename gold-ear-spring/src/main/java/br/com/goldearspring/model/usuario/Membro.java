package br.com.goldearspring.model.usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "membro")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Membro {

    @Id
    @Column(name = "id_membro")
    @NonNull
    @Schema(description = "ID do usuário que é membro", example = "5")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_membro")
    private Usuario usuario;

//    @OneToMany(mappedBy = "membro")
//    private Set<Avaliacao> avaliacoes;
}
