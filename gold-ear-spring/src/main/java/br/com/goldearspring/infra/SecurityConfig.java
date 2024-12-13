package br.com.goldearspring.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AutenticacaoFilter autenticacaoFilter;

    public SecurityConfig(AutenticacaoFilter autenticacaoFilter){this.autenticacaoFilter = autenticacaoFilter;}
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.csrf (csrf-> csrf.disable())
                .sessionManagement(sm->sm.sessionCreationPolicy (SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests (auth->auth.requestMatchers(HttpMethod.POST,"/login")
                        .permitAll()
                        .requestMatchers (HttpMethod.GET, "/usuarios/listar").hasAuthority("membro")
                        .requestMatchers (HttpMethod.GET, "/discos/listar").hasAuthority("membro")
                        .requestMatchers (HttpMethod.GET, "/artistas/listar").hasAuthority("membro")
                        .requestMatchers (HttpMethod.GET, "/membros/listar").hasAuthority("membro")
                        .requestMatchers (HttpMethod.GET, "/funcionarios/listar").hasAuthority("membro")
                        .requestMatchers (HttpMethod.POST, "/usuarios/criar").hasAuthority("funcionario")
                        .requestMatchers (HttpMethod.POST, "/discos/criar").hasAuthority("funcionario")
                        .requestMatchers (HttpMethod.POST, "/artistas/criar").hasAuthority("funcionario")
                        .requestMatchers (HttpMethod.POST, "/artistas//atribuir-disco/{idArtista}").hasAuthority("funcionario")
                        .anyRequest()
                        .authenticated()
                ).addFilterBefore (this.autenticacaoFilter, UsernamePasswordAuthenticationFilter.class)
                .build ();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder ();
    }
}
