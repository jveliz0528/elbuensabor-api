package com.delivery.demo.services.factura;

import com.delivery.demo.entities.comprobantes.Factura;
import com.delivery.demo.entities.comprobantes.Orden;
import com.delivery.demo.services.base.BaseService;

import java.sql.Timestamp;

public interface FacturaService extends BaseService<Factura, Long> {

    public Factura save(Orden orden, String cajeroUID) throws Exception;

    public boolean anularFactura(Long id) throws Exception;
}
