package com.delivery.demo.repositories.comprobantes;

import com.delivery.demo.entities.comprobantes.Estado;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends BaseRepository<Estado, Long> {
}
