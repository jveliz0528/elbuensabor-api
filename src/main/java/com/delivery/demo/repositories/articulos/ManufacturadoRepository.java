package com.delivery.demo.repositories.articulos;

import com.delivery.demo.entities.articulos.Manufacturado;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturadoRepository extends BaseRepository<Manufacturado, Long> {
}
