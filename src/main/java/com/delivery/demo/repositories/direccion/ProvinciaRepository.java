package com.delivery.demo.repositories.direccion;

import com.delivery.demo.entities.direccion.Provincia;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepository extends BaseRepository<Provincia, Long> {
}
