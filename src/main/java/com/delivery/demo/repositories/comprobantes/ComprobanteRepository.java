package com.delivery.demo.repositories.comprobantes;

import com.delivery.demo.entities.comprobantes.Comprobante;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComprobanteRepository extends BaseRepository<Comprobante, Long> {
}
