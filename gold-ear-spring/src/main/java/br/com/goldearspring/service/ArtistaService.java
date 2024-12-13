package br.com.goldearspring.service;

import br.com.goldearspring.model.artista.Artista;
import br.com.goldearspring.model.artista.ArtistaRepository;
import br.com.goldearspring.model.disco.Disco;
import br.com.goldearspring.model.disco.DiscoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ArtistaService {

    private final ArtistaRepository artistaRepository;
    private final DiscoRepository discoRepository;

    @Autowired
    public ArtistaService(ArtistaRepository artistaRepository, DiscoRepository discoRepository) {
        this.artistaRepository = artistaRepository;
        this.discoRepository = discoRepository;
    }

    public List<Artista> listarTodos() {
        return artistaRepository.findAll();
    }

    public Optional<Artista> buscarPorId(Long id) {
        return artistaRepository.findById(id);
    }

    public String relacaoArtistaDisco(Long idArtista, Disco disco){
        Artista a = this.artistaRepository.getReferenceById(idArtista);
        a.getDiscos().add(disco);
        disco.getArtistas().add(a);
        artistaRepository.save(a);
        return "Atribuição realizada com sucesso";
    }

    public Artista criarArtista(Artista artista) {
        return artistaRepository.save(artista);
    }

    public Artista atualizarArtista(Long id, Artista artistaAtualizado) {
        return artistaRepository.findById(id)
                .map(artista -> {
                    artista.setNome(artistaAtualizado.getNome());
                    return artistaRepository.save(artista);
                }).orElseThrow(() -> new RuntimeException("Artista não encontrado"));
    }

    public void deletarArtista(Long id) {
        if (artistaRepository.existsById(id)) {
            artistaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Artista não encontrado");
        }
    }
}
