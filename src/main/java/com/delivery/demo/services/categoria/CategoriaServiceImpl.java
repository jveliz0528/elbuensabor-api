package com.delivery.demo.services.categoria;

import com.delivery.demo.entities.articulos.Categoria;
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
public class CategoriaServiceImpl extends BaseServiceImpl<Categoria, Long> implements CategoriaService{

    public CategoriaServiceImpl(BaseRepository<Categoria, Long> baseRepository) {
        super(baseRepository);
    }

    SearchSpecification<Categoria> spec = new SearchSpecification<Categoria>();

    @Override
    public Map<String, Object> findAll(String filter, int page, int size, String sortBy, String direction) throws Exception {
        try {
            Pageable pageable;
            if (direction.equals("desc")) {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
            } else {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
            }

            Specification<Categoria> isNotDeleted = spec.isNotDeleted();

            Page<Categoria> entityPage;

            if(filter == null || filter.equals("")){
                entityPage = baseRepository.findAll(Specification.where(isNotDeleted),pageable);
            } else {
                Specification<Categoria> filterByDenominacion = spec.findByProperty("denominacion", filter);

                entityPage = baseRepository.findAll(Specification.where(isNotDeleted).and(Specification.where(filterByDenominacion)), pageable);
            }

            List<Categoria> entities = entityPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("payload", entities);
            response.put("length", entityPage.getTotalElements());

            return response;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Categoria> findAll(String filter) throws Exception {
        try{

            Specification<Categoria> isNotDeleted = spec.isNotDeleted();

            if(filter == null || filter.equals("")){
                return baseRepository.findAll(Specification.where(isNotDeleted));
            } else {
                Specification<Categoria> filterByDenominacion = spec.findByProperty("denominacion", filter);

                return baseRepository.findAll(Specification.where(isNotDeleted).and(Specification.where(filterByDenominacion)));
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
