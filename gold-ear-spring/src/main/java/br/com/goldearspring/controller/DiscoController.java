package br.com.goldearspring.controller;

import br.com.goldearspring.model.disco.Disco;
import br.com.goldearspring.service.DiscoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/discos")
@Tag(name = "Disco", description = "Path de todos os discos na plataforma")
public class DiscoController {

    private final DiscoService discoService;

    @Autowired
    public DiscoController(DiscoService discoService) {
        this.discoService = discoService;
    }

    @Operation(summary = "Adicionar novo disco")
    @PostMapping("/criar")
    public ResponseEntity<Disco> criarDisco(@RequestBody Disco disco) {
        Disco discoSalvo = discoService.salvarDisco(disco);
        return new ResponseEntity<>(discoSalvo, HttpStatus.CREATED);
    }

    // Buscar um disco pelo ID
    @Operation(summary = "Buscar disco pelo ID")
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Disco> buscarPorId(@PathVariable Long id) {
        Optional<Disco> disco = discoService.buscarPorId(id);
        return disco.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar um disco pelo UUID
    @Operation(summary = "Buscar disco pelo UUID")
    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<Disco> buscarPorUuid(@PathVariable UUID uuid) {
        Optional<Disco> disco = discoService.buscarPorUuid(uuid);
        return disco.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Listar todos os discos
    @Operation(summary = "Listar todos os discos")
    @GetMapping("/listar")
    public ResponseEntity<List<Disco>> buscarTodos() {
        List<Disco> discos = discoService.buscarTodos();
        return ResponseEntity.ok(discos);
    }

    // Buscar discos por ano de lançamento
    @Operation(summary = "Listar discos pelo ano de lançamento")
    @GetMapping("/buscar/ano")
    public ResponseEntity<List<Disco>> buscarPorAnoLancamento(@RequestParam Integer anoLancamento) {
        List<Disco> discos = discoService.buscarPorAnoLancamento(anoLancamento);
        return ResponseEntity.ok(discos);
    }

    // Atualizar um disco existente
    @Operation(summary = "Atualizar disco pelo ID")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Disco> atualizarDisco(@PathVariable Long id, @RequestBody Disco disco) {
        try {
            Disco discoAtualizado = discoService.atualizarDisco(id, disco);
            return ResponseEntity.ok(discoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Excluir um disco
    @Operation(summary = "Excluir um disco pelo ID")
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluirDisco(@PathVariable Long id) {
        try {
            discoService.excluirDisco(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
