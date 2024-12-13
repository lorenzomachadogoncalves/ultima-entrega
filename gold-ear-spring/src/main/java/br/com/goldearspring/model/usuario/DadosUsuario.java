package br.com.goldearspring.model.usuario;

public record DadosUsuario(Long id, String email, String permissao) {

    public DadosUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getEmail(), usuario.getPermissao().getNome());
    }

}
