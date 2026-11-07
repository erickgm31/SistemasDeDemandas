package io.github.sistemademandaszoo.zoodemandas.controller;

import io.github.sistemademandaszoo.zoodemandas.model.Usuario;
import io.github.sistemademandaszoo.zoodemandas.repository.UsuarioRepositorio;
import io.github.sistemademandaszoo.zoodemandas.model.LoginDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioController(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar (@RequestBody Usuario usuario){
        // Campos obrigatórios (evita NullPointerException e erro de banco)
        if (usuario.getNome() == null || usuario.getNome().isBlank()
                || usuario.getEmail() == null || usuario.getEmail().isBlank()
                || usuario.getTelefone() == null || usuario.getTelefone().isBlank()
                || usuario.getCargo() == null || usuario.getCargo().isBlank()
                || usuario.getSenha() == null) {
            return ResponseEntity.badRequest().body("Preencha todos os campos obrigatórios");
        }

        if(usuarioRepositorio.findByEmail(usuario.getEmail()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Já cadastrado");
        }

        if (usuario.getSenha().length() < 8) {
            return ResponseEntity.badRequest().body("A senha deve ter no mínimo 8 caracteres!");
        }

        if (usuario.getIdade() <= 0) {
            return ResponseEntity.badRequest().body("Informe uma idade válida");
        }

        System.out.println("Usuario recebido: " + usuario);

        usuario.setId(UUID.randomUUID().toString());
        usuarioRepositorio.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dados){
        if (dados.getEmail() == null || dados.getSenha() == null) {
            return ResponseEntity.badRequest().body("Informe e-mail e senha");
        }

        Optional<Usuario> usuarioEncontrado =
                usuarioRepositorio.findByEmail(dados.getEmail());

        if (usuarioEncontrado.isEmpty()
                || !usuarioEncontrado.get().getSenha().equals(dados.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou senha incorretos");
        }


        // Metodo de verificação da senha aqui
        // em uma aplicação real, você deve armazenar as senhas criptografadas com BCrypt
        // e utilizar PasswordEncoder.matches() para compará-las.
        // Nunca armazene senhas em texto puro.

        return ResponseEntity.ok("Login Realizado com sucesso");
    }

    
}
