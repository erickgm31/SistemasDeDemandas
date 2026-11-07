package io.github.sistemademandaszoo.zoodemandas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.sistemademandaszoo.zoodemandas.model.Usuario;

import java.util.Optional;

public interface UsuarioRepositorio extends  JpaRepository<Usuario, String>{
    Optional<Usuario> findByEmail(String email);
}
