package com.delivery.demo.services.insumo;

import com.delivery.demo.entities.articulos.ArticuloInsumo;
import com.delivery.demo.services.base.BaseService;

public interface ArticuloInsumoService extends BaseService<ArticuloInsumo, Long> {
    public ArticuloInsumo addStock(Long articuloId, double cantidad) throws Exception;
    public ArticuloInsumo removeStock(Long articuloId, double cantidad) throws Exception;
}
