package com.delivery.demo.services.estado;

import com.delivery.demo.entities.comprobantes.Estado;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EstadoServiceImpl extends BaseServiceImpl<Estado, Long> implements EstadoService {

    public EstadoServiceImpl(BaseRepository<Estado, Long> baseRepository) {
        super(baseRepository);
    }
}
