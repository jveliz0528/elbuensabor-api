package com.delivery.demo.repositories.usuarios;

import com.delivery.demo.entities.usuarios.Usuario;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {
}
