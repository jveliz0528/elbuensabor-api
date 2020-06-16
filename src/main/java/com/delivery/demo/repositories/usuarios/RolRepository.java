package com.delivery.demo.repositories.usuarios;

import com.delivery.demo.entities.usuarios.Rol;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends BaseRepository<Rol, Long> {
}
