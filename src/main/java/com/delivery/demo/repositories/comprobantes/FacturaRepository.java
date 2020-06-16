package com.delivery.demo.repositories.comprobantes;

import com.delivery.demo.entities.comprobantes.Factura;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends BaseRepository<Factura, Long> {
}
