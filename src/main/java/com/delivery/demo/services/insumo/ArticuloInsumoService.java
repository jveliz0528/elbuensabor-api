package com.delivery.demo.services.insumo;

import com.delivery.demo.entities.articulos.ArticuloInsumo;
import com.delivery.demo.services.base.BaseService;

import java.util.List;
import java.util.Map;

public interface ArticuloInsumoService extends BaseService<ArticuloInsumo, Long> {
    public ArticuloInsumo addStock(Long articuloId, double cantidad) throws Exception;
    public ArticuloInsumo removeStock(Long articuloId, double cantidad) throws Exception;
    public List<ArticuloInsumo> getBebidas(String filter) throws Exception;

}
