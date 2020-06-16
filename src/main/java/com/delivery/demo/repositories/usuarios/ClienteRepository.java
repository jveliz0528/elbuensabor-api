package com.delivery.demo.repositories.usuarios;

import com.delivery.demo.entities.usuarios.Cliente;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends BaseRepository<Cliente, Long> {
}
