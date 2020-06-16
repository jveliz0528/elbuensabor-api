package com.delivery.demo.services.datosEmpresas;

import com.delivery.demo.entities.DatosEmpresa;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DatosEmpresaServiceImpl extends BaseServiceImpl<DatosEmpresa, Long> implements DatosEmpresaService {

    public DatosEmpresaServiceImpl(BaseRepository<DatosEmpresa, Long> baseRepository) {
        super(baseRepository);
    }
}
