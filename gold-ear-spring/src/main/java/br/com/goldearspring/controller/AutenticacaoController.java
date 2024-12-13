package br.com.goldearspring.controller;

import br.com.goldearspring.infra.TokenServiceJWT;
import br.com.goldearspring.model.usuario.DadosUsuario;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;

    private final TokenServiceJWT tokenServiceJWT;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenServiceJWT tokenServiceJWT) {
        this.authenticationManager = authenticationManager;
        this.tokenServiceJWT = tokenServiceJWT;
    }

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dadosAutenticacao){
        try{
            Authentication autenticado =
                    new UsernamePasswordAuthenticationToken(
                            dadosAutenticacao.email(),
                            dadosAutenticacao.senha()
                    );
            Authentication authentication = authenticationManager.authenticate(autenticado);
            User user = (User) authentication.getPrincipal();
            String token = this.tokenServiceJWT.gerarToken (user);
            return ResponseEntity.ok().body(new DadosTokenTWJ (token));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private record DadosAutenticacao(String email, String senha){ }

    private record DadosTokenTWJ(String token){}
}
