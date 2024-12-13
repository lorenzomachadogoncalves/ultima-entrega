package br.com.goldearspring.model.usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "funcionario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Entidade que representa um funcionario no sistema")
public class Funcionario {

    @Id
    @Column(name = "id_funcionario")
    @Schema(description = "ID do usuário que é funcionário", example = "5")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_funcionario")
    private Usuario usuario;
}
