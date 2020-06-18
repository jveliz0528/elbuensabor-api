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

    SearchSpecification<Estado> spec = new SearchSpecification<Estado>();

    @Override
    public Estado getOneByDenominacion(String denominacion) throws Exception {
        try {

            Specification<Estado> filterByDenominacion = spec.findByProperty("denominacion", denominacion);
            Optional<Estado> entityOptional = baseRepository.findOne(Specification.where(filterByDenominacion));

            return entityOptional.get();

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }
}
