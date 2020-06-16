package com.delivery.demo.services.manufacturado;

import com.delivery.demo.entities.articulos.Manufacturado;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ManufacturadoServiceImpl extends BaseServiceImpl<Manufacturado, Long> implements ManufacturadoService {

    public ManufacturadoServiceImpl(BaseRepository<Manufacturado, Long> baseRepository) {
        super(baseRepository);
    }
}
