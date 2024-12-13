package br.com.goldearspring.controller;

import br.com.goldearspring.model.artista.Artista;
import br.com.goldearspring.model.disco.Disco;
import br.com.goldearspring.service.ArtistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artistas")
public class ArtistaController {

    private final ArtistaService artistaService;

    @Autowired
    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    @GetMapping("/listar")
    public List<Artista> listarTodos() {
        return this.artistaService.listarTodos();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Artista> buscarPorId(@PathVariable Long id) {
        return artistaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/criar")
    public ResponseEntity<Artista> criarArtista(@RequestBody Artista artista) {
        Artista novoArtista = artistaService.criarArtista(artista);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoArtista);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Artista> atualizarArtista(@PathVariable Long id, @RequestBody Artista artista) {
        try {
            Artista artistaAtualizado = artistaService.atualizarArtista(id, artista);
            return ResponseEntity.ok(artistaAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/atribuir-disco/{idArtista}")
    public ResponseEntity atribuirDisco(@PathVariable Long idArtista, @RequestBody Disco disco){
        return ResponseEntity.ok(this.artistaService.relacaoArtistaDisco(idArtista, disco));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarArtista(@PathVariable Long id) {
        try {
            artistaService.deletarArtista(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
