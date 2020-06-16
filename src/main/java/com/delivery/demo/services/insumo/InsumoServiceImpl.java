package com.delivery.demo.services.insumo;

import com.delivery.demo.entities.articulos.Insumo;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class InsumoServiceImpl extends BaseServiceImpl<Insumo, Long> implements InsumoService {

    public InsumoServiceImpl(BaseRepository<Insumo, Long> baseRepository) {
        super(baseRepository);
    }
}
