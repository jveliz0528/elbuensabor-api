package com.delivery.demo.services.empleado;

import com.delivery.demo.entities.usuarios.Empleado;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.repositories.usuarios.EmpleadoRepository;
import com.delivery.demo.services.base.BaseServiceImpl;
import com.delivery.demo.specifications.SearchSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl extends BaseServiceImpl<Empleado, Long> implements EmpleadoService {

    public EmpleadoServiceImpl(BaseRepository<Empleado, Long> baseRepository) {
        super(baseRepository);
    }

    SearchSpecification<Empleado> spec = new SearchSpecification<Empleado>();

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Override
    public Map<String, Object> findAll(String filter, int page, int size, String sortBy, String direction) throws Exception {
        try {
            Pageable pageable;
            if (direction.equals("desc")) {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
            } else {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
            }

            Specification<Empleado> isNotDeleted = spec.isNotDeleted();

            Page<Empleado> entityPage;

            if(filter == null || filter.equals("")){
                entityPage = baseRepository.findAll(Specification.where(isNotDeleted),pageable);
            } else {
                Specification<Empleado> filterByUID = spec.findByUid(filter);
                Specification<Empleado> filterByNombre = spec.findByProperty("nombre", filter);
                Specification<Empleado> filterByApellido = spec.findByProperty("apellido", filter);
                Specification<Empleado> filterByCuil = spec.findByProperty("cuil", filter);
                Specification<Empleado> filterByRol = spec.findByForeignAttribute("rol", "denominacion", filter);

                entityPage = baseRepository.findAll(Specification.where(isNotDeleted).and(Specification.where(filterByUID)
                        .or(filterByNombre)
                        .or(filterByApellido)
                        .or(filterByRol)
                        .or(filterByCuil)
                ), pageable);
            }

            List<Empleado> entities = entityPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("payload", entities);
            response.put("length", entityPage.getTotalElements());

            return response;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Empleado> findAll(String filter) throws Exception {
        try{

            Specification<Empleado> isNotDeleted = spec.isNotDeleted();

            if(filter == null || filter.equals("")){
                return baseRepository.findAll(Specification.where(isNotDeleted));
            } else {
                Specification<Empleado> filterByUID = spec.findByUid(filter);
                Specification<Empleado> filterByNombre = spec.findByProperty("nombre", filter);
                Specification<Empleado> filterByApellido = spec.findByProperty("apellido", filter);
                Specification<Empleado> filterByCuil = spec.findByProperty("cuil", filter);
                Specification<Empleado> filterByRol = spec.findByForeignAttribute("rol", "denominacion", filter);

                return baseRepository.findAll(Specification.where(isNotDeleted).and(Specification.where(filterByUID)
                        .or(filterByNombre)
                        .or(filterByApellido)
                        .or(filterByCuil)
                        .or(filterByRol)
                        .or(filterByCuil)
                ));
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /*
     * @desc This method gets one employee by its uid
     * @return Empleado empleado or new Exception()
     * */
    @Override
    public Empleado findByUID(String uid) throws Exception {
        try {

            Specification<Empleado> filterByUID = spec.findByUid(uid);
            Optional<Empleado> entityOptional = baseRepository.findOne(Specification.where(filterByUID));

            return entityOptional.get();

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    @Override
    public boolean existByCuil(String cuil) throws Exception {
        try {

            return empleadoRepository.existsByCuil(cuil);

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    @Override
    public Empleado save(Empleado entity) throws Exception {
        try {

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            entity.setUltimaActualizacion(timestamp);
            entity.setFechaAlta(timestamp);

            entity = baseRepository.save(entity);

            return entity;

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }
}
