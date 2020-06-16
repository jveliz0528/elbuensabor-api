package com.delivery.demo.services.configuracion;

import com.delivery.demo.entities.Configuracion;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracionServiceImpl extends BaseServiceImpl<Configuracion, Long> implements ConfiguracionService {

    public ConfiguracionServiceImpl(BaseRepository<Configuracion, Long> baseRepository) {
        super(baseRepository);
    }
}
