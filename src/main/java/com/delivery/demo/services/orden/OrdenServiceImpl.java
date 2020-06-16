package com.delivery.demo.services.orden;

import com.delivery.demo.entities.comprobantes.Orden;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrdenServiceImpl extends BaseServiceImpl<Orden, Long> implements OrdenService {

    public OrdenServiceImpl(BaseRepository<Orden, Long> baseRepository) {
        super(baseRepository);
    }
}
