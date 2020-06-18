package com.delivery.demo.services.rubro;

import com.delivery.demo.entities.articulos.Categoria;
import com.delivery.demo.entities.articulos.Rubro;
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
public class RubroServiceImpl extends BaseServiceImpl<Rubro, Long> implements RubroService {

    public RubroServiceImpl(BaseRepository<Rubro, Long> baseRepository) {
        super(baseRepository);
    }

    SearchSpecification<Rubro> spec = new SearchSpecification<Rubro>();

    @Override
    public Map<String, Object> findAll(String filter, int page, int size, String sortBy, String direction) throws Exception {
        try {
            Pageable pageable;
            if (direction.equals("desc")) {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
            } else {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
            }

            Specification<Rubro> isNotDeleted = spec.isNotDeleted();

            Page<Rubro> entityPage;

            if(filter == null || filter.equals("")){
                entityPage = baseRepository.findAll(Specification.where(isNotDeleted),pageable);
            } else {
                Specification<Rubro> filterByDenominacion = spec.findByProperty("denominacion", filter);
                Specification<Rubro> filterByRubroPadre = spec.findByForeignAttribute("rubroPadre", "denominacion", filter);

                entityPage = baseRepository.findAll(Specification.where(isNotDeleted)
                        .and(Specification.where(filterByDenominacion)
                                .or(filterByRubroPadre))
                        , pageable);
            }

            List<Rubro> entities = entityPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("payload", entities);
            response.put("length", entityPage.getTotalElements());

            return response;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Rubro> findAll(String filter) throws Exception {
        try{

            Specification<Rubro> isNotDeleted = spec.isNotDeleted();

            if(filter == null || filter.equals("")){
                return baseRepository.findAll(Specification.where(isNotDeleted));
            } else {
                Specification<Rubro> filterByDenominacion = spec.findByProperty("denominacion", filter);
                Specification<Rubro> filterByRubroPadre = spec.findByForeignAttribute("rubroPadre", "denominacion", filter);

                return baseRepository.findAll(Specification.where(isNotDeleted)
                        .and(Specification.where(filterByDenominacion)
                                .or(filterByRubroPadre)));
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
