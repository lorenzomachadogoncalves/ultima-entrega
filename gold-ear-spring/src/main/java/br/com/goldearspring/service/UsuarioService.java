package br.com.goldearspring.service;

import br.com.goldearspring.model.usuario.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final MembroRepository membroRepository;
    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, MembroRepository membroRepository, FuncionarioRepository funcionarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.membroRepository = membroRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    // Método para salvar um novo usuário
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        // Validação de unicidade de CPF e Email
        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new IllegalArgumentException("Já existe um usuário com este CPF.");
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário com este email.");
        }
        if(usuario.getPermissao().getNome().equals("membro")){
            Membro membro = new Membro();
//            membro.setId(usuario.getId());
            membro.setUsuario(usuario);  // Associa o usuário
            membroRepository.save(membro);
        }
        else{
            Funcionario funcionario = new Funcionario();
            funcionario.setId(usuario.getId());
            funcionario.setUsuario(usuario);  // Associa o usuário
            funcionarioRepository.save(funcionario);
        }
        usuario.setSenha(new BCryptPasswordEncoder ().encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public DadosUsuario buscaUsuario(Long id){
        Usuario u = this.usuarioRepository.getReferenceById(id);
        return new DadosUsuario(u);
    }

    // Buscar um usuário pelo ID
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Buscar um usuário pelo UUID
    public Optional<Usuario> buscarPorUuid(UUID uuid) {
        return usuarioRepository.findByUuid(uuid);
    }

    // Buscar todos os usuários
    public List<DadosUsuario> buscarTodos() {
        return this.usuarioRepository.findAll().stream().map(DadosUsuario::new).toList();
    }

    // Atualizar informações do usuário
    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        usuarioExistente.setCpf(usuarioAtualizado.getCpf());
        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setSenha(usuarioAtualizado.getSenha());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setPermissao(usuarioAtualizado.getPermissao());
        System.out.println(usuarioExistente.getPermissao());
        // Revalida CPF e email para garantir que sejam únicos
        if (!usuarioExistente.getCpf().equals(usuarioAtualizado.getCpf()) &&
                usuarioRepository.existsByCpf(usuarioAtualizado.getCpf())) {
            throw new IllegalArgumentException("Já existe um usuário com este CPF.");
        }
        if (!usuarioExistente.getEmail().equals(usuarioAtualizado.getEmail()) &&
                usuarioRepository.existsByEmail(usuarioAtualizado.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário com este email.");
        }
        if(usuarioExistente.getPermissao().getNome().equals("membro")){
            Membro membro = new Membro();
//            membro.setId(usuarioAtualizado.getPermissao().getId());
            membro.setUsuario(usuarioExistente);  // Associa o usuário
            new MembroService(membroRepository).salvarMembro(membro);
            new FuncionarioService(funcionarioRepository).excluirFuncionario(usuarioExistente.getId());
        }
        else{
            Funcionario funcionario = new Funcionario();
            funcionario.setId(usuarioExistente.getId());
            funcionario.setUsuario(usuarioExistente);  // Associa o usuário
            funcionarioRepository.save(funcionario);
            new MembroService(membroRepository).excluirMembro(usuarioExistente.getId());
        }
        return usuarioRepository.save(usuarioExistente);
    }

    // Excluir um usuário
    @Transactional
    public void excluirUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }
}
