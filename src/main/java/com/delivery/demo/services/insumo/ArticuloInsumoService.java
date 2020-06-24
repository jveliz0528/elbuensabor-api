package com.delivery.demo.services.insumo;

import com.delivery.demo.entities.articulos.ArticuloInsumo;
import com.delivery.demo.services.base.BaseService;

import java.util.Map;

public interface ArticuloInsumoService extends BaseService<ArticuloInsumo, Long> {
    public ArticuloInsumo addStock(Long articuloId, double cantidad) throws Exception;
    public ArticuloInsumo removeStock(Long articuloId, double cantidad) throws Exception;
    public Map<String, Object> getBebidas(String filter, int page, int size, String sortBy, String direction) throws Exception;
}
