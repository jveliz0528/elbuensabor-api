package com.delivery.demo.services.factura;

import com.delivery.demo.entities.DatosEmpresa;
import com.delivery.demo.entities.comprobantes.Estado;
import com.delivery.demo.entities.comprobantes.Factura;
import com.delivery.demo.entities.comprobantes.Orden;
import com.delivery.demo.entities.usuarios.Empleado;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.repositories.DatosEmpresaRepository;
import com.delivery.demo.repositories.comprobantes.EstadoRepository;
import com.delivery.demo.repositories.comprobantes.OrdenRepository;
import com.delivery.demo.repositories.usuarios.EmpleadoRepository;
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
public class FacturaServiceImpl extends BaseServiceImpl<Factura, Long> implements FacturaService {

    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    DatosEmpresaRepository datosEmpresaRepository;

    @Autowired
    OrdenRepository ordenRepository;

    public FacturaServiceImpl(BaseRepository<Factura, Long> baseRepository) {
        super(baseRepository);
    }

    SearchSpecification<Factura> spec = new SearchSpecification<Factura>();

    @Override
    public Map<String, Object> findAll(String filter, int page, int size, String sortBy, String direction) throws Exception {
        try {
            Pageable pageable;
            if (direction.equals("desc")) {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
            } else {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
            }

            Specification<Factura> isNotDeleted = spec.isNotDeleted();

            Page<Factura> entityPage;

            if(filter == null || filter.equals("")){
                entityPage = baseRepository.findAll(Specification.where(isNotDeleted),pageable);
            } else {
                Specification<Factura> filterByEstado = spec.findByEstado(filter);
                Specification<Factura> filterById = spec.findByProperty("id", filter);
                Specification<Factura> filterByFormaPago = spec.findByProperty("formaPago", filter);
                Specification<Factura> filterByNombreCajero = spec.findByForeignAttribute("cajero", "nombre", filter);
                Specification<Factura> filterByApellidoCajero = spec.findByForeignAttribute("cajero", "apellido", filter);

                entityPage = baseRepository.findAll(Specification.where(isNotDeleted)
                        .and(Specification.where(filterByEstado)
                                .or(filterById)
                                .or(filterByFormaPago)
                                .or(filterByNombreCajero)
                                .or(filterByApellidoCajero)
                        ), pageable);
            }

            List<Factura> entities = entityPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("payload", entities);
            response.put("length", entityPage.getTotalElements());

            return response;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Factura> findAll(String filter) throws Exception {
        try{

            Specification<Factura> isNotDeleted = spec.isNotDeleted();

            if(filter == null || filter.equals("")){
                return baseRepository.findAll(Specification.where(isNotDeleted));
            } else {
                Specification<Factura> filterByEstado = spec.findByForeignAttribute("estado", "denominacion", filter);
                Specification<Factura> filterById = spec.findByProperty("id", filter);
                Specification<Factura> filterByFormaPago = spec.findByProperty("formaPago", filter);
                Specification<Factura> filterByNombreCajero = spec.findByForeignAttribute("cajero", "nombre", filter);
                Specification<Factura> filterByApellidoCajero = spec.findByForeignAttribute("cajero", "apellido", filter);

                return baseRepository.findAll(Specification.where(isNotDeleted).and(Specification.where(filterByEstado)
                        .or(filterById)
                        .or(filterByFormaPago)
                        .or(filterByNombreCajero)
                        .or(filterByApellidoCajero)
                ));
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


    /*
     * @desc This method create a new invoice and saves it into the database
     * @return Factura factura or new Exception()
     * */
    @Override
    public Factura save(Long ordenId, String cajeroUid) throws Exception {
        try {

            Factura factura = new Factura();

            Optional<Orden> ordenOptional = ordenRepository.findById(ordenId);
            Orden orden = ordenOptional.get();

            factura.setMontoDescuento(orden.getMontoDescuento());
            factura.setTotal(orden.getTotal());
            factura.setFormaPago(orden.getFormaPago());

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            factura.setFecha(timestamp);
            factura.setUltimaActualizacion(timestamp);

            /* ESTADO */
            SearchSpecification<Estado> specEstado = new SearchSpecification<Estado>();
            Specification<Estado> filterByDenominacion = specEstado.findByProperty("denominacion", "pagado");
            Optional<Estado> estado = estadoRepository.findOne(Specification.where(filterByDenominacion));
            factura.setEstado(estado.get());

            /* CAJERO */
            SearchSpecification<Empleado> specEmpleado = new SearchSpecification<Empleado>();
            Specification<Empleado> filterByUID = specEmpleado.findByUid(cajeroUid);
            Optional<Empleado> empleado = empleadoRepository.findOne(Specification.where(filterByUID));
            factura.setCajero(empleado.get());

            /* DATOS EMPRESA */
            Optional<DatosEmpresa> datosEmpresa = datosEmpresaRepository.findById(1L);
            factura.setDatosEmpresa(datosEmpresa.get());

            /* ACTUALIZAR ESTADO ORDEN */
            filterByDenominacion = specEstado.findByProperty("denominacion", "entregado");
            Optional<Estado> estadoOrden = estadoRepository.findOne(Specification.where(filterByDenominacion));
            orden.setEstado(estadoOrden.get());

            orden = ordenRepository.save(orden);

            factura.setOrden(orden);

            factura = baseRepository.save(factura);

            return factura;

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    /*
     * @desc This method get one invoice by its order id
     * @return Factura factura or new Exception()
     * */
    @Override
    public Factura getOneByOrden(Long ordenId) throws Exception {
        try{
            Specification<Factura> filterByOrden = spec.findByForeignId("orden", ordenId);
            Optional<Factura> factura = baseRepository.findOne(Specification.where(filterByOrden));
            return factura.get();
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /*
     * @desc This method gets one invoice by its id and updates its state to "ANULADO"
     * @return True if successful or new Exception()
     * */
    @Override
    public boolean anularFactura(Long id) throws Exception {
        try {

            if (baseRepository.existsById(id)) {

                Optional<Factura> entityOptional = baseRepository.findById(id);

                Factura facturaAnulada = entityOptional.get();

                SearchSpecification<Estado> specEstado = new SearchSpecification<Estado>();
                Specification<Estado> filterByDenominacion = specEstado.findByProperty("denominacion", "anulado");
                Optional<Estado> estado = estadoRepository.findOne(Specification.where(filterByDenominacion));

                facturaAnulada.setEstado(estado.get());

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                facturaAnulada.setUltimaActualizacion(timestamp);
                facturaAnulada = baseRepository.save(facturaAnulada);

                return true;

            } else {
                throw new Exception();
            }

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }
}
