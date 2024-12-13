package br.com.goldearspring.controller;

import br.com.goldearspring.model.usuario.DadosUsuario;
import br.com.goldearspring.model.usuario.Usuario;
import br.com.goldearspring.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuario", description = "Path de todos os usuários da plataforma")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Criar um novo usuário
    @Operation(summary = "Adicionar novo usuario")
    @PostMapping("/criar")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody @Valid Usuario usuario, UriComponentsBuilder uriBuilder) {
        this.usuarioService.salvarUsuario(usuario);
        URI uri = uriBuilder.path("/usuario/{uuid}").buildAndExpand(usuario.getUuid()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    // Buscar um usuário pelo ID
    @Operation(summary = "Buscar usuario pelo ID")
    @GetMapping("/busca/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar um usuário pelo UUID
    @Operation(summary = "Buscar usuario pelo UUID")
    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<Usuario> buscarPorUuid(@PathVariable UUID uuid) {
        Optional<Usuario> usuario = usuarioService.buscarPorUuid(uuid);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Listar todos os usuários
    @Operation(summary = "Listar todos os usuarios")
    @GetMapping("/listar")
    public ResponseEntity<List<DadosUsuario>> buscarTodos() {
        List<DadosUsuario> usuarios = usuarioService.buscarTodos();
        return ResponseEntity.ok(usuarios);
    }

    // Atualizar um usuário existente
    @Operation(summary = "Atualizar usuario pelo ID")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Excluir um usuário
    @Operation(summary = "Excluir usuario pelo id")
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        try {
            usuarioService.excluirUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

