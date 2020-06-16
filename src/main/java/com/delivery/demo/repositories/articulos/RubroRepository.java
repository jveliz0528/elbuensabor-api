package com.delivery.demo.repositories.articulos;

import com.delivery.demo.entities.articulos.Rubro;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RubroRepository extends BaseRepository<Rubro, Long> {
}
