package com.delivery.demo.services.cliente;

import com.delivery.demo.entities.direccion.DireccionDelivery;
import com.delivery.demo.entities.usuarios.Cliente;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.services.base.BaseServiceImpl;
import com.delivery.demo.specifications.SearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClienteServiceImpl extends BaseServiceImpl<Cliente, Long> implements ClienteService {

    public ClienteServiceImpl(BaseRepository<Cliente, Long> baseRepository) {
        super(baseRepository);
    }

    SearchSpecification<Cliente> spec = new SearchSpecification<Cliente>();

    @Override
    public Map<String, Object> findAll(String filter, int page, int size, String sortBy, String direction) throws Exception {
        try {
            Pageable pageable;
            if (direction.equals("desc")) {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
            } else {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
            }

            Specification<Cliente> isNotDeleted = spec.isNotDeleted();

            Page<Cliente> entityPage;

            if(filter == null || filter.equals("")){
                entityPage = baseRepository.findAll(Specification.where(isNotDeleted),pageable);
            } else {
                Specification<Cliente> filterByUID = spec.findByUid(filter);
                Specification<Cliente> filterByNombre = spec.findByProperty("nombre", filter);
                Specification<Cliente> filterByApellido = spec.findByProperty("apellido", filter);
                Specification<Cliente> filterByRol = spec.findByForeignAttribute("rol", "denominacion", filter);

                entityPage = baseRepository.findAll(Specification.where(isNotDeleted).and(Specification.where(filterByUID)
                        .or(filterByNombre)
                        .or(filterByApellido)
                        .or(filterByRol)), pageable);
            }

            List<Cliente> entities = entityPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("payload", entities);
            response.put("length", entityPage.getTotalElements());

            return response;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Cliente> findAll(String filter) throws Exception {
        try{

            Specification<Cliente> isNotDeleted = spec.isNotDeleted();

            if(filter == null || filter.equals("")){
                return baseRepository.findAll(Specification.where(isNotDeleted));
            } else {
                Specification<Cliente> filterByUID = spec.findByUid(filter);
                Specification<Cliente> filterByNombre = spec.findByProperty("nombre", filter);
                Specification<Cliente> filterByApellido = spec.findByProperty("apellido", filter);
                Specification<Cliente> filterByRol = spec.findByForeignAttribute("rol", "denominacion", filter);

                return baseRepository.findAll(Specification.where(isNotDeleted).and(Specification.where(filterByUID)
                        .or(filterByNombre)
                        .or(filterByApellido)
                        .or(filterByRol)));
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Cliente addDireccion(DireccionDelivery direccion, String uid) throws Exception {
        try {

            Specification<Cliente> filterByUID = spec.findByUid(uid);
            Optional<Cliente> entityOptional = baseRepository.findOne(Specification.where(filterByUID));

            Cliente entityUpdated = entityOptional.get();

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            entityUpdated.setUltimaActualizacion(timestamp);
            entityUpdated.getDireccionesEnvio().add(direccion);

            return baseRepository.save(entityUpdated);

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    @Override
    public boolean removeDireccion(Long direccionId, String uid) throws Exception {
        try {

            Specification<Cliente> filterByUID = spec.findByUid(uid);
            Optional<Cliente> entityOptional = baseRepository.findOne(Specification.where(filterByUID));

            Cliente entityUpdated = entityOptional.get();

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            entityUpdated.setUltimaActualizacion(timestamp);

            for (DireccionDelivery direccionAux: entityUpdated.getDireccionesEnvio()){
                if(direccionAux.getId() == direccionId){
                    direccionAux.setEliminado(true);
                    direccionAux.setUltimaActualizacion(timestamp);
                    return true;
                }
            }

            return false;

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    @Override
    public Cliente findByUID(String uid) throws Exception {
        try {

            Specification<Cliente> filterByUID = spec.findByUid(uid);
            Optional<Cliente> entityOptional = baseRepository.findOne(Specification.where(filterByUID));

            return entityOptional.get();

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

}
