package com.delivery.demo.repositories.articulos;

import com.delivery.demo.entities.articulos.Insumo;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsumoRepository extends BaseRepository<Insumo, Long> {
}
