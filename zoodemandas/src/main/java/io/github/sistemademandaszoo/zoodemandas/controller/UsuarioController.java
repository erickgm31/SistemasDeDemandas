package io.github.sistemademandaszoo.zoodemandas.controller;

import io.github.sistemademandaszoo.zoodemandas.model.Usuario;
import io.github.sistemademandaszoo.zoodemandas.model.LoginDTO;
import io.github.sistemademandaszoo.zoodemandas.repository.UsuarioRepositorio;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextRepository securityContextRepository;

    public UsuarioController(
            UsuarioRepositorio usuarioRepositorio,
            PasswordEncoder passwordEncoder,
            SecurityContextRepository securityContextRepository) {

        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
        this.securityContextRepository = securityContextRepository;
    }

    @GetMapping("/csrf")
    public ResponseEntity<?> csrf(CsrfToken csrfToken) {

        return ResponseEntity.ok().body(
                java.util.Map.of(
                        "token", csrfToken.getToken(),
                        "headerName", csrfToken.getHeaderName()
                )
        );
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {

        // Verifica os campos obrigatórios
        if (usuario.getNome() == null || usuario.getNome().isBlank()
                || usuario.getEmail() == null || usuario.getEmail().isBlank()
                || usuario.getTelefone() == null || usuario.getTelefone().isBlank()
                || usuario.getCargo() == null || usuario.getCargo().isBlank()
                || usuario.getSenha() == null) {

            return ResponseEntity.badRequest()
                    .body("Preencha todos os campos obrigatórios");
        }

        // Verifica se o e-mail já está cadastrado
        if (usuarioRepositorio.findByEmail(usuario.getEmail()).isPresent()) {

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email já cadastrado");
        }

        // Verifica o tamanho mínimo da senha
        if (usuario.getSenha().length() < 8) {

            return ResponseEntity.badRequest()
                    .body("A senha deve ter no mínimo 8 caracteres!");
        }

        // Verifica a idade
        if (usuario.getIdade() <= 0) {

            return ResponseEntity.badRequest()
                    .body("Informe uma idade válida");
        }

        // Gera um identificador para o usuário
        usuario.setId(UUID.randomUUID().toString());

        // Transforma a senha em hash utilizando BCrypt
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        // Salva o usuário no banco de dados
        usuarioRepositorio.save(usuario);

        // Retorna apenas uma mensagem, sem expor a senha ou seu hash
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Cadastro realizado com sucesso");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginDTO dados,
            HttpServletRequest request,
            HttpServletResponse response) {

        // Verifica se os dados foram informados
        if (dados.getEmail() == null || dados.getEmail().isBlank()
                || dados.getSenha() == null || dados.getSenha().isBlank()) {

            return ResponseEntity.badRequest()
                    .body("Informe e-mail e senha");
        }

        // Procura o usuário pelo e-mail
        Usuario usuario = usuarioRepositorio
                .findByEmail(dados.getEmail())
                .orElse(null);

        // Verifica se o usuário existe e se a senha está correta
        if (usuario == null
                || !passwordEncoder.matches(
                        dados.getSenha(),
                        usuario.getSenha())) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou senha incorretos");
        }

        // Cria uma sessão e renova seu identificador após o login
        request.getSession(true);
        request.changeSessionId();

        // Cria a autenticação do usuário
        UsernamePasswordAuthenticationToken autenticacao =
                new UsernamePasswordAuthenticationToken(
                        usuario.getEmail(),
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                );

        // Cria o contexto de segurança
        SecurityContext contexto = SecurityContextHolder.createEmptyContext();

        contexto.setAuthentication(autenticacao);

        SecurityContextHolder.setContext(contexto);

        // Salva a autenticação na sessão
        securityContextRepository.saveContext(
                contexto,
                request,
                response
        );

        return ResponseEntity.ok("Login realizado com sucesso");
    }
}