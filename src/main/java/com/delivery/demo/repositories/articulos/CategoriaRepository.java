package com.delivery.demo.repositories.articulos;

import com.delivery.demo.entities.articulos.Categoria;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends BaseRepository<Categoria, Long> {
}
