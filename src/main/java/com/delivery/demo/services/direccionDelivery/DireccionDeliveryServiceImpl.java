package com.delivery.demo.services.direccionDelivery;

import com.delivery.demo.entities.direccion.DireccionDelivery;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DireccionDeliveryServiceImpl extends BaseServiceImpl<DireccionDelivery, Long> implements DireccionDeliveryService {

    public DireccionDeliveryServiceImpl(BaseRepository<DireccionDelivery, Long> baseRepository) {
        super(baseRepository);
    }

}
