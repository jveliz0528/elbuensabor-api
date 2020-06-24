package com.delivery.demo.services.rubro;

import com.delivery.demo.entities.articulos.Rubro;
import com.delivery.demo.services.base.BaseService;

import java.util.List;

public interface RubroService extends BaseService<Rubro, Long> {
    public List<Rubro> getRubrosBebidas() throws Exception;
}
