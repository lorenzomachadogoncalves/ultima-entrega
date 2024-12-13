package br.com.goldearspring.service;

import br.com.goldearspring.model.usuario.Usuario;
import br.com.goldearspring.model.usuario.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public AutenticacaoService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Usuario u = this.usuarioRepository.findByEmail(email);
        if(u==null){
            throw new UsernameNotFoundException ("Usuario inexistente ou credenciais incorretas");
        }
        else{
            UserDetails userDetails = User.withUsername (u.getEmail())
                    .password(u.getSenha())
                    .authorities(u.getPermissao().getNome())
                    .build();
            return userDetails;
        }
    }

}
