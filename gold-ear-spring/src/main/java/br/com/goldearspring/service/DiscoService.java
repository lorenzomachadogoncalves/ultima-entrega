package br.com.goldearspring.service;

import br.com.goldearspring.model.disco.Disco;
import br.com.goldearspring.model.disco.DiscoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DiscoService {

    private final DiscoRepository discoRepository;

    @Autowired
    public DiscoService(DiscoRepository discoRepository) {
        this.discoRepository = discoRepository;
    }

    // Método para salvar um novo disco
    @Transactional
    public Disco salvarDisco(Disco disco) {
        // Validação para evitar duplicidade por nome e ano
        if (discoRepository.existsByNomeAndAnoLancamento(disco.getNome(), disco.getAnoLancamento())) {
            throw new IllegalArgumentException("Já existe um disco com esse nome e ano de lançamento.");
        }
        return discoRepository.save(disco);
    }

    // Buscar um disco pelo ID
    public Optional<Disco> buscarPorId(Long id) {
        return discoRepository.findById(id);
    }

    // Buscar um disco pelo UUID
    public Optional<Disco> buscarPorUuid(UUID uuid) {
        return discoRepository.findByUuid(uuid);
    }

    // Buscar todos os discos
    public List<Disco> buscarTodos() {
        return discoRepository.findAll();
    }

    // Buscar discos por ano de lançamento
    public List<Disco> buscarPorAnoLancamento(Integer anoLancamento) {
        return discoRepository.findByAnoLancamento(anoLancamento);
    }

    // Atualizar informações do disco
    @Transactional
    public Disco atualizarDisco(Long id, Disco discoAtualizado) {
        Disco discoExistente = discoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disco não encontrado."));

        discoExistente.setNome(discoAtualizado.getNome());
        discoExistente.setAnoLancamento(discoAtualizado.getAnoLancamento());
        discoExistente.setCapa(discoAtualizado.getCapa());

        // Revalida duplicidade de nome e ano para garantir unicidade
        if (!discoExistente.getNome().equals(discoAtualizado.getNome()) ||
                !discoExistente.getAnoLancamento().equals(discoAtualizado.getAnoLancamento())) {

            if (discoRepository.existsByNomeAndAnoLancamento(discoAtualizado.getNome(), discoAtualizado.getAnoLancamento())) {
                throw new IllegalArgumentException("Já existe um disco com esse nome e ano de lançamento.");
            }
        }

        return discoRepository.save(discoExistente);
    }

    // Excluir um disco
    @Transactional
    public void excluirDisco(Long id) {
        if (!discoRepository.existsById(id)) {
            throw new IllegalArgumentException("Disco não encontrado.");
        }
        discoRepository.deleteById(id);
    }
}