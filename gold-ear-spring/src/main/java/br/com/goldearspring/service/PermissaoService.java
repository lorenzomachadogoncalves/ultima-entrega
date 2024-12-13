package br.com.goldearspring.service;

import br.com.goldearspring.model.usuario.Permissao;
import br.com.goldearspring.model.usuario.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PermissaoService {

    private final PermissaoRepository permissaoRepository;

    @Autowired
    public PermissaoService(PermissaoRepository permissaoRepository) {
        this.permissaoRepository = permissaoRepository;
    }

    // Salvar uma nova permissão
    public Permissao salvarPermissao(Permissao permissao) {
        return permissaoRepository.save(permissao);
    }

    // Buscar permissão pelo ID
    public Optional<Permissao> buscarPorId(Long id) {
        return permissaoRepository.findById(id);
    }

    // Listar todas as permissões
    public List<Permissao> buscarTodas() {
        return permissaoRepository.findAll();
    }

    // Atualizar uma permissão existente
    public Permissao atualizarPermissao(Long id, Permissao permissao) {
        if (!permissaoRepository.existsById(id)) {
            throw new IllegalArgumentException("Permissão não encontrada");
        }
        permissao.setId(id);
        return permissaoRepository.save(permissao);
    }

    // Excluir permissão pelo ID
    public void excluirPermissao(Long id) {
        if (!permissaoRepository.existsById(id)) {
            throw new IllegalArgumentException("Permissão não encontrada");
        }
        permissaoRepository.deleteById(id);
    }
}
