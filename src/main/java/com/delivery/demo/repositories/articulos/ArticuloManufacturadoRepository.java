package com.delivery.demo.repositories.articulos;

import com.delivery.demo.entities.articulos.ArticuloManufacturado;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloManufacturadoRepository extends BaseRepository<ArticuloManufacturado, Long> {
}
