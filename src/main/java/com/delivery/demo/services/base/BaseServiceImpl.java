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
import java.sql.Timestamp;
import java.util.*;

public abstract class BaseServiceImpl<E extends Base, ID extends Serializable>
        implements BaseService<E, ID> {

    protected BaseRepository<E, ID> baseRepository;
    SearchSpecification<E> spec = new SearchSpecification<E>();

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

            Specification<E> isNotDeleted = spec.isNotDeleted();

            Page<E> entityPage = baseRepository.findAll(Specification.where(isNotDeleted),pageable);
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

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            entity.setUltimaActualizacion(timestamp);

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

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            entity.setUltimaActualizacion(timestamp);

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
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                toDelete.setUltimaActualizacion(timestamp);
                toDelete = baseRepository.save(toDelete);
                return true;
            } else {
                throw new Exception();
            }

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    @Override
    public boolean undoDelete(ID entityId) throws Exception {
        try {

            if (baseRepository.existsById(entityId)) {

                Optional<E> entityOptional = baseRepository.findById(entityId);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                E toDelete = entityOptional.get();
                toDelete.setEliminado(false);
                toDelete.setUltimaActualizacion(timestamp);
                toDelete = baseRepository.save(toDelete);
                return true;
            } else {
                throw new Exception();
            }

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    @Override
    public boolean hide(ID entityId) throws Exception {
        try {

            if (baseRepository.existsById(entityId)) {

                Optional<E> entityOptional = baseRepository.findById(entityId);
                E toHide = entityOptional.get();
                toHide.setOculto(true);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                toHide.setUltimaActualizacion(timestamp);
                toHide = baseRepository.save(toHide);
                return true;

            } else {
                throw new Exception();
            }

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    @Override
    public boolean unhide(ID entityId) throws Exception {
        try {

            if (baseRepository.existsById(entityId)) {

                Optional<E> entityOptional = baseRepository.findById(entityId);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                E toHide = entityOptional.get();
                toHide.setOculto(false);
                toHide.setUltimaActualizacion(timestamp);
                toHide = baseRepository.save(toHide);
                return true;
            } else {
                throw new Exception();
            }

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

}