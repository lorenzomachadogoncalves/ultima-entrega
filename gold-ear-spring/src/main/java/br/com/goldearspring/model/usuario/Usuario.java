package br.com.goldearspring.model.usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "usuario")
@Data
//@NoArgsConstructor
//@AllArgsConstructor
@RequiredArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    @Schema(description = "ID do usuário", example = "5")
    private Long id;

    @UuidGenerator
    private UUID uuid;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, length = 50)
    private String senha;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @ManyToOne
    @JoinColumn(name = "id_permissao", referencedColumnName = "id_permissao")
    private Permissao permissao;

    public Usuario(String nome, String email, String cpf, String senha){
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
    }
}
