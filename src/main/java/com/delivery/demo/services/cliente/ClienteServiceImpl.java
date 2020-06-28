package com.delivery.demo.services.cliente;

import com.delivery.demo.entities.direccion.DireccionDelivery;
import com.delivery.demo.entities.usuarios.Cliente;
import com.delivery.demo.entities.usuarios.Rol;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.repositories.direccion.DireccionDeliveryRepository;
import com.delivery.demo.repositories.usuarios.RolRepository;
import com.delivery.demo.services.base.BaseServiceImpl;
import com.delivery.demo.specifications.SearchSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ClienteServiceImpl extends BaseServiceImpl<Cliente, Long> implements ClienteService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private DireccionDeliveryRepository direccionDeliveryRepository;

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

    /*
     * @desc This method completes a costumer entity with its Rol and saves it into the database
     * @return Cliente cliente or new Exception()
     * */
    @Override
    public Cliente save(Cliente entity) throws Exception {
        try {
            SearchSpecification<Rol> spec = new SearchSpecification<Rol>();
            Specification<Rol> filterByName = spec.findByProperty("denominacion", "cliente");
            Optional<Rol> rolCliente = rolRepository.findOne(Specification.where(filterByName));

            entity.setRol(rolCliente.get());

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            entity.setUltimaActualizacion(timestamp);
            entity.setFechaAlta(timestamp);

            entity = baseRepository.save(entity);

            return entity;

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    /*
     * @desc This method adds a new delivery address to a customer
     * @return Cliente cliente or new Exception()
     * */
    @Override
    public Cliente addDireccion(DireccionDelivery direccion, String uid) throws Exception {
        try {

            Specification<Cliente> filterByUID = spec.findByUid(uid);
            Optional<Cliente> clienteOptional = baseRepository.findOne(Specification.where(filterByUID));

            Cliente cliente = clienteOptional.get();

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            cliente.setUltimaActualizacion(timestamp);
            direccion.setUltimaActualizacion(timestamp);
            cliente.getDireccionesEnvio().add(direccion);

            return baseRepository.save(cliente);

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    /*
     * @desc This method gets all customer's delivery addresses
     * @return List<DireccionDelivery> direcciones or new Exception()
     * */
    @Override
    public List<DireccionDelivery> getDirecciones(String uid) throws Exception {
        try {

            Specification<Cliente> filterByUID = spec.findByUid(uid);
            Optional<Cliente> clienteOptional = baseRepository.findOne(Specification.where(filterByUID));

            Cliente cliente = clienteOptional.get();

            List<DireccionDelivery> direcciones = new ArrayList<>();

            for (DireccionDelivery direccionAux: cliente.getDireccionesEnvio()){
                if(!direccionAux.isEliminado()){
                    direcciones.add(direccionAux);
                }
            }

            return direcciones;

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    /*
     * @desc This method gets one customer by its uid
     * @return Cliente cliente or new Exception()
     * */
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
