package br.com.goldearspring.controller;

import br.com.goldearspring.model.usuario.Permissao;
import br.com.goldearspring.service.PermissaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/permissao")
@Tag(name = "Permissoes", description = "Path de todos os tipos de permissão dentro da plataforma")
public class PermissaoController {

    private final PermissaoService permissaoService;

    @Autowired
    public PermissaoController(PermissaoService permissaoService) {
        this.permissaoService = permissaoService;
    }

    // Criar uma nova permissão
    @Operation(summary = "Adicionar nova permissao")
    @PostMapping("/criar")
    public void criarPermissao(@RequestBody Permissao permissao) {
        this.permissaoService.salvarPermissao(permissao);
    }

    // Buscar uma permissão pelo ID
    @Operation(summary = "Buscar permissao pelo ID")
    @GetMapping("/busca/{id}")
    public ResponseEntity<Permissao> buscarPorId(@PathVariable Long id) {
        Optional<Permissao> permissao = permissaoService.buscarPorId(id);
        return permissao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Listar todas as permissões
    @Operation(summary = "Listar todas as permissoes")
    @GetMapping("/listar")
    public ResponseEntity<List<Permissao>> buscarTodas() {
        List<Permissao> permissoes = permissaoService.buscarTodas();
        return ResponseEntity.ok(permissoes);
    }

    // Atualizar uma permissão existente
    @Operation(summary = "Atualizar permissao pelo ID")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Permissao> atualizarPermissao(@PathVariable Long id, @RequestBody Permissao permissao) {
        try {
            Permissao permissaoAtualizada = permissaoService.atualizarPermissao(id, permissao);
            return ResponseEntity.ok(permissaoAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Excluir uma permissão
    @Operation(summary = "Excluir permissao pelo ID")
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluirPermissao(@PathVariable Long id) {
        try {
            permissaoService.excluirPermissao(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
