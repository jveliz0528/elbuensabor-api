package com.delivery.demo.repositories.articulos;

import com.delivery.demo.entities.articulos.ArticuloInsumo;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloInsumoRepository extends BaseRepository<ArticuloInsumo, Long> {
}
