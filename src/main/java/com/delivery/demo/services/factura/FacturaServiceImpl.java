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
                Specification<Factura> filterByEstado = spec.findByForeignAttribute("estado", "denominacion", filter);
                Specification<Factura> filterById = spec.findByProperty("descripcion", filter);
                Specification<Factura> filterByFormaPago = spec.findByProperty("formaPago", filter);
                Specification<Factura> filterByNombreCliente = spec.findByForeignAttribute("cliente", "nombre", filter);
                Specification<Factura> filterByApellidoCliente = spec.findByForeignAttribute("cliente", "apellido", filter);
                Specification<Factura> filterByNombreCajero = spec.findByForeignAttribute("cajero", "nombre", filter);
                Specification<Factura> filterByApellidoCajero = spec.findByForeignAttribute("cajero", "apellido", filter);

                entityPage = baseRepository.findAll(Specification.where(isNotDeleted)
                        .and(Specification.where(filterByEstado)
                                .or(filterById)
                                .or(filterByFormaPago)
                                .or(filterByNombreCliente)
                                .or(filterByApellidoCliente)
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
                Specification<Factura> filterById = spec.findByProperty("descripcion", filter);
                Specification<Factura> filterByFormaPago = spec.findByProperty("formaPago", filter);
                Specification<Factura> filterByNombreCliente = spec.findByForeignAttribute("cliente", "nombre", filter);
                Specification<Factura> filterByApellidoCliente = spec.findByForeignAttribute("cliente", "apellido", filter);
                Specification<Factura> filterByTelefonoCliente = spec.findByForeignAttribute("cliente", "telefono", filter);
                Specification<Factura> filterByNombreCajero = spec.findByForeignAttribute("cajero", "nombre", filter);
                Specification<Factura> filterByApellidoCajero = spec.findByForeignAttribute("cajero", "apellido", filter);

                return baseRepository.findAll(Specification.where(isNotDeleted).and(Specification.where(filterByEstado)
                        .or(filterById)
                        .or(filterByFormaPago)
                        .or(filterByNombreCliente)
                        .or(filterByApellidoCliente)
                        .or(filterByNombreCajero)
                        .or(filterByApellidoCajero)
                ));
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


    @Override
    public Factura save(Orden orden, String cajeroUID) throws Exception {
        try {

            Factura factura = new Factura();

            factura.setOrden(orden);
            factura.setDetalles(orden.getDetalles());
            factura.setMontoDescuento(orden.getMontoDescuento());
            factura.setTotal(orden.getTotal());
            factura.setFormaPago(orden.getFormaPago());

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            factura.setFecha(timestamp);
            factura.setUltimaActualizacion(timestamp);

            /* ESTADO */
            SearchSpecification<Estado> specEstado = new SearchSpecification<Estado>();
            Specification<Estado> filterByDenominacion = specEstado.findByProperty("denominacion", "aprobado");
            Optional<Estado> estado = estadoRepository.findOne(Specification.where(filterByDenominacion));
            factura.setEstado(estado.get());

            /* CAJERO */
            SearchSpecification<Empleado> specEmpleado = new SearchSpecification<Empleado>();
            Specification<Empleado> filterByUID = specEmpleado.findByUid(cajeroUID);
            Optional<Empleado> empleado = empleadoRepository.findOne(Specification.where(filterByUID));
            factura.setCajero(empleado.get());

            /* DATOS EMPRESA */
            Optional<DatosEmpresa> datosEmpresa = datosEmpresaRepository.findById(1l);
            factura.setDatosEmpresa(datosEmpresa.get());

            /* ACTUALIZAR ESTADO ORDEN */
            filterByDenominacion = specEstado.findByProperty("denominacion", "terminado");
            Optional<Estado> estadoOrden = estadoRepository.findOne(Specification.where(filterByDenominacion));
            orden.setEstado(estadoOrden.get());
            orden.setUltimaActualizacion(timestamp);

            orden = ordenRepository.save(orden);

            return baseRepository.save(factura);

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    @Override
    public boolean anularFactura(Long id) throws Exception {
        try {

            if (baseRepository.existsById(id)) {

                Optional<Factura> entityOptional = baseRepository.findById(id);

                Factura facturaAnulada = entityOptional.get();

                SearchSpecification<Estado> specEstado = new SearchSpecification<Estado>();
                Specification<Estado> filterByDenominacion = specEstado.findByProperty("denominacion", "pagado");
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
