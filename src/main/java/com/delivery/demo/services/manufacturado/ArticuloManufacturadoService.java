package com.delivery.demo.services.manufacturado;

import com.delivery.demo.entities.articulos.ArticuloManufacturado;
import com.delivery.demo.services.base.BaseService;

import java.util.List;

public interface ArticuloManufacturadoService extends BaseService<ArticuloManufacturado, Long> {
    public List<ArticuloManufacturado> findAllPublic(String filter) throws Exception;
}
