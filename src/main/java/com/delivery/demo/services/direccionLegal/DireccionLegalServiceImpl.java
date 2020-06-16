package com.delivery.demo.services.direccionLegal;

import com.delivery.demo.entities.direccion.DireccionLegal;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DireccionLegalServiceImpl extends BaseServiceImpl<DireccionLegal, Long> implements DireccionLegalService {

    public DireccionLegalServiceImpl(BaseRepository<DireccionLegal, Long> baseRepository) {
        super(baseRepository);
    }
}
