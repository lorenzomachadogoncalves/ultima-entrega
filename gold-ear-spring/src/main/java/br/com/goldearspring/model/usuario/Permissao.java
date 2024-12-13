package br.com.goldearspring.model.usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permissao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permissao {

    @Id
    @Column(name = "id_permissao", nullable = false)
    @Schema(description = "ID da permissão", example = "5")
    private Long id;

    @Column(nullable = false, length = 50)
    private String nome;
}