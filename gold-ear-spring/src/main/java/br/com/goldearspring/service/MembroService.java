package br.com.goldearspring.service;

import br.com.goldearspring.model.usuario.Membro;
import br.com.goldearspring.model.usuario.MembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembroService {

    private final MembroRepository membroRepository;

    @Autowired
    public MembroService(MembroRepository membroRepository) {
        this.membroRepository = membroRepository;
    }

    public Membro salvarMembro(Membro membro) {
        return membroRepository.save(membro);
    }

    public Optional<Membro> buscarPorId(Long id) {
        return membroRepository.findById(id);
    }

    public List<Membro> buscarTodos() {
        return membroRepository.findAll();
    }

    public Membro atualizarMembro(Long id, Membro membro) {
        if (!membroRepository.existsById(id)) {
            throw new IllegalArgumentException("Membro não encontrado");
        }
        membro.setId(id);
        return membroRepository.save(membro);
    }

    public void excluirMembro(Long id) {
        if (!membroRepository.existsById(id)) {
            throw new IllegalArgumentException("Membro não encontrado");
        }
        membroRepository.deleteById(id);
    }
}
