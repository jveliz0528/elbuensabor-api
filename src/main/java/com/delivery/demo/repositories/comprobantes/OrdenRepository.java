package com.delivery.demo.repositories.comprobantes;

import com.delivery.demo.entities.comprobantes.Orden;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepository extends BaseRepository<Orden, Long> {
}
