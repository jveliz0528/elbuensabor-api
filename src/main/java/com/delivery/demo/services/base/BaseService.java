package com.delivery.demo.services.base;

import com.delivery.demo.entities.Base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseService<E extends Base, ID extends Serializable> {

    public Map<String, Object> findAll(String filter, int page, int size, String sortBy, String direction) throws Exception;

    public List<E> findAll(String filtro) throws Exception;

    public E findById(ID entityId) throws Exception;

    public E save(E entity) throws Exception;

    public E update(ID entityId, E entity) throws Exception;

    public boolean delete(ID entityId) throws Exception;

    public boolean undoDelete(ID entityId) throws Exception;

    public boolean hide(ID entityId) throws Exception;

    public boolean unhide(ID entityId) throws Exception;

}
