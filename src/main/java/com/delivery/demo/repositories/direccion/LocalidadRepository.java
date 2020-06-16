package com.delivery.demo.repositories.direccion;

import com.delivery.demo.entities.direccion.Localidad;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalidadRepository extends BaseRepository<Localidad, Long> {
}
