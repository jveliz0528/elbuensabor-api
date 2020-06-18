package com.delivery.demo.services.manufacturado;

import com.delivery.demo.entities.articulos.ArticuloManufacturado;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.services.base.BaseServiceImpl;
import com.delivery.demo.specifications.SearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticuloManufacturadoServiceImpl extends BaseServiceImpl<ArticuloManufacturado, Long> implements ArticuloManufacturadoService {

    public ArticuloManufacturadoServiceImpl(BaseRepository<ArticuloManufacturado, Long> baseRepository) {
        super(baseRepository);
    }

    SearchSpecification<ArticuloManufacturado> spec = new SearchSpecification<ArticuloManufacturado>();

    @Override
    public Map<String, Object> findAll(String filter, int page, int size, String sortBy, String direction) throws Exception {
        try {
            Pageable pageable;
            if (direction.equals("desc")) {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
            } else {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
            }

            Specification<ArticuloManufacturado> isNotDeleted = spec.isNotDeleted();

            Page<ArticuloManufacturado> entityPage;

            if(filter == null || filter.equals("")){
                entityPage = baseRepository.findAll(Specification.where(isNotDeleted),pageable);
            } else {
                Specification<ArticuloManufacturado> filterByDenominacion = spec.findByProperty("denominacion", filter);
                Specification<ArticuloManufacturado> filterByDescripcion = spec.findByProperty("descripcion", filter);
                Specification<ArticuloManufacturado> filterByCategoria = spec.findByForeignAttribute("categoria", "denominacion", filter);

                entityPage = baseRepository.findAll(Specification.where(isNotDeleted).and(Specification.where(filterByDenominacion).or(filterByDescripcion).or(filterByCategoria)), pageable);
            }

            List<ArticuloManufacturado> entities = entityPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("payload", entities);
            response.put("length", entityPage.getTotalElements());

            return response;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<ArticuloManufacturado> findAll(String filter) throws Exception {
        try{

            Specification<ArticuloManufacturado> isNotDeleted = spec.isNotDeleted();

            if(filter == null || filter.equals("")){
                return baseRepository.findAll(Specification.where(isNotDeleted));
            } else {
                Specification<ArticuloManufacturado> filterByCategoria = spec.findByForeignAttribute("categoria", "denominacion", filter);
                Specification<ArticuloManufacturado> filterByDenominacion = spec.findByProperty("denominacion", filter);
                Specification<ArticuloManufacturado> filterByDescripcion = spec.findByProperty("descripcion", filter);

                return baseRepository.findAll(Specification.where(isNotDeleted).and(Specification.where(filterByDenominacion).or(filterByDescripcion).or(filterByCategoria)));
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
