package com.delivery.demo.repositories.articulos;

import com.delivery.demo.entities.articulos.Articulo;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloRepository extends BaseRepository<Articulo, Long> {
}
