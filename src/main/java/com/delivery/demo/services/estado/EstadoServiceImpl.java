package com.delivery.demo.services.estado;

import com.delivery.demo.entities.comprobantes.Estado;
import com.delivery.demo.entities.usuarios.Empleado;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.services.base.BaseServiceImpl;
import com.delivery.demo.specifications.SearchSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstadoServiceImpl extends BaseServiceImpl<Estado, Long> implements EstadoService {

    public EstadoServiceImpl(BaseRepository<Estado, Long> baseRepository) {
        super(baseRepository);
    }

}
