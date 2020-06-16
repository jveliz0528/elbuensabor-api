package com.delivery.demo.services.base;

import com.delivery.demo.entities.Base;
import com.delivery.demo.specifications.SearchSpecification;
import com.delivery.demo.repositories.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.*;

public abstract class BaseServiceImpl<E extends Base, ID extends Serializable>
        implements BaseService<E, ID> {

    protected BaseRepository<E, ID> baseRepository;

    @Autowired
    public BaseServiceImpl(BaseRepository<E,ID> baseRepository){
        this.baseRepository = baseRepository;
    }

    @Override
    public Map<String, Object> findAll(String filter, int page, int size, String sortBy, String direction) throws Exception {
        try {
            Pageable pageable;
            if (direction.equals("desc")) {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
            } else {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
            }

            SearchSpecification<E> spec = new SearchSpecification<E>();
            Specification<E> spec1 = spec.isNotDeleted();

            Page<E> entityPage = baseRepository.findAll(Specification.where(spec1),pageable);
            List<E> entities = entityPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("payload", entities);
            response.put("length", entityPage.getTotalElements());

            return response;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<E> findAll(String filter) throws Exception {
        try{

            SearchSpecification<E> spec = new SearchSpecification<E>();
            Specification<E> isNotDeleted = spec.isNotDeleted();
            return baseRepository.findAll(Specification.where(isNotDeleted));

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public E findById(ID entityId) throws Exception {
        try {

            Optional<E> varOptional = baseRepository.findById(entityId);

            E entity = varOptional.get();

            return entity;

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    @Override
    public E save(E entity) throws Exception {
        try {

            entity = baseRepository.save(entity);

            return entity;

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    @Override
    public E update(ID entityId, E entity) throws Exception {
        try {
            Optional<E> entityOptional = baseRepository.findById(entityId);

            E entityUpdated = entityOptional.get();

            entityUpdated = baseRepository.save(entity);

            return entityUpdated;

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    @Override
    public boolean delete(ID entityId) throws Exception {
        try {

            if (baseRepository.existsById(entityId)) {

                Optional<E> entityOptional = baseRepository.findById(entityId);
                E toDelete = entityOptional.get();
                toDelete.setEliminado(true);
                toDelete = baseRepository.save(toDelete);
                return true;
            } else {
                throw new Exception();
            }

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

}