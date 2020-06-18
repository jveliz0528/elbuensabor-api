package com.delivery.demo.services.orden;

import com.delivery.demo.controllers.ArticuloInsumoController;
import com.delivery.demo.entities.articulos.*;
import com.delivery.demo.entities.comprobantes.DetalleOrden;
import com.delivery.demo.entities.comprobantes.Estado;
import com.delivery.demo.entities.comprobantes.Orden;
import com.delivery.demo.entities.usuarios.Cliente;
import com.delivery.demo.entities.usuarios.Usuario;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.repositories.articulos.ArticuloInsumoRepository;
import com.delivery.demo.repositories.comprobantes.EstadoRepository;
import com.delivery.demo.repositories.usuarios.ClienteRepository;
import com.delivery.demo.repositories.usuarios.UsuarioRepository;
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
public class OrdenServiceImpl extends BaseServiceImpl<Orden, Long> implements OrdenService {

    @Autowired
    ArticuloInsumoRepository insumoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EstadoRepository estadoRepository;

    public OrdenServiceImpl(BaseRepository<Orden, Long> baseRepository) {
        super(baseRepository);
    }

    SearchSpecification<Orden> spec = new SearchSpecification<Orden>();
    Specification<Orden> isNotDeleted = spec.isNotDeleted();

    @Override
    public Map<String, Object> findAll(String filter, int page, int size, String sortBy, String direction) throws Exception {
        try {
            Pageable pageable;
            if (direction.equals("desc")) {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
            } else {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
            }

            Page<Orden> entityPage;

            if(filter == null || filter.equals("")){
                entityPage = baseRepository.findAll(Specification.where(isNotDeleted),pageable);
            } else {
                Specification<Orden> filterByEstado = spec.findByForeignAttribute("estado", "denominacion", filter);
                Specification<Orden> filterById = spec.findByProperty("descripcion", filter);
                Specification<Orden> filterByFormaPago = spec.findByProperty("formaPago", filter);
                Specification<Orden> filterByNombreCliente = spec.findByForeignAttribute("cliente", "nombre", filter);
                Specification<Orden> filterByApellidoCliente = spec.findByForeignAttribute("cliente", "apellido", filter);
                Specification<Orden> filterByNombreCajero = spec.findByForeignAttribute("cajero", "nombre", filter);
                Specification<Orden> filterByApellidoCajero = spec.findByForeignAttribute("cajero", "apellido", filter);

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

            List<Orden> entities = entityPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("payload", entities);
            response.put("length", entityPage.getTotalElements());

            return response;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Orden> findAll(String filter) throws Exception {
        try{

            if(filter == null || filter.equals("")){
                return baseRepository.findAll(Specification.where(isNotDeleted));
            } else {
                Specification<Orden> filterByEstado = spec.findByForeignAttribute("estado", "denominacion", filter);
                Specification<Orden> filterById = spec.findByProperty("descripcion", filter);
                Specification<Orden> filterByFormaPago = spec.findByProperty("formaPago", filter);
                Specification<Orden> filterByNombreCliente = spec.findByForeignAttribute("cliente", "nombre", filter);
                Specification<Orden> filterByApellidoCliente = spec.findByForeignAttribute("cliente", "apellido", filter);
                Specification<Orden> filterByTelefonoCliente = spec.findByForeignAttribute("cliente", "telefono", filter);
                Specification<Orden> filterByNombreCajero = spec.findByForeignAttribute("cajero", "nombre", filter);
                Specification<Orden> filterByApellidoCajero = spec.findByForeignAttribute("cajero", "apellido", filter);

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
    public Orden save(Orden orden, String clienteUid) throws Exception {
        try {

            SearchSpecification<Cliente> specCliente = new SearchSpecification<Cliente>();
            Specification<Cliente> filterByUid = specCliente.findByUid(clienteUid);
            Optional<Cliente> cliente = clienteRepository.findOne(Specification.where(filterByUid));
            orden.setCliente(cliente.get());

            /* FECHA */
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            orden.setFecha(timestamp);
            orden.setUltimaActualizacion(timestamp);

            /* ESTADO */
            SearchSpecification<Estado> specEstado = new SearchSpecification<Estado>();
            Specification<Estado> filterByDenominacion = specEstado.findByProperty("denominacion", "pendiente");
            Optional<Estado> estado = estadoRepository.findOne(Specification.where(filterByDenominacion));
            orden.setEstado(estado.get());

            /* TIEMPO PREPARACION */
            orden.setTiempoTotalPreparacion(this.calcularTiempoTotalPreparacion(orden.getDetalles()));

            /* HORARIO ENTREGA */
            orden.setHorarioEntrega(this.calcularHorarioEntrega(orden.getFecha(), orden.getTiempoTotalPreparacion(), orden.isDelivery()));

            if(this.controlStock(orden.getDetalles())){
                return baseRepository.save(orden);
            } else {
                throw new Exception("Uno o más productos están fuera de stock");
            }



        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    @Override
    public Date calcularHorarioEntrega(Date fechaEntrada, int tiempoOrdenActual, boolean delivery) throws Exception {
        try{
            Specification<Orden> filterByEstado = spec.findByForeignAttribute("estado", "denominacion", "en proceso");
            List<Orden> ordenesEnCocina = baseRepository.findAll(Specification.where(isNotDeleted).and(Specification.where(filterByEstado)));

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaEntrada);

            if(ordenesEnCocina.size() > 0) {
                int tiempoTotalOrdenes = 0;

                for (Orden ordenAux: ordenesEnCocina){
                    tiempoTotalOrdenes += ordenAux.getTiempoTotalPreparacion();
                }

                SearchSpecification<Usuario> userSpec = new SearchSpecification<Usuario>();
                Specification<Usuario> filterByCocinero = userSpec.findByForeignAttribute("rol", "denominacion", "cocinero");
                long cantidadCocinero = usuarioRepository.count(Specification.where(filterByCocinero));

                int tiempoEspera = (int) (tiempoTotalOrdenes / cantidadCocinero);

                calendar.add(Calendar.MINUTE, tiempoEspera);
            }

            calendar.add(Calendar.MINUTE, tiempoOrdenActual);

            if(delivery){
                calendar.add(Calendar.MINUTE, 10);
            }

            return calendar.getTime();
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public int calcularTiempoTotalPreparacion(List<DetalleOrden> detallesOrden) throws Exception {
        if (detallesOrden.size() > 0){
            int tiempoTotal = 0;

            for (DetalleOrden detalle: detallesOrden){
                if(detalle.getArticuloManufacturado() != null){
                    tiempoTotal += detalle.getArticuloManufacturado().getTiempoEstimadoCocina();
                }
            }
            return tiempoTotal;
        } else {
            return 0;
        }
    }


    @Override
    public Orden actualizarEstado(Estado estado, Long ordenId) throws Exception {
        try {
            Optional<Orden> ordenOpcional = baseRepository.findById(ordenId);
            Orden orden = ordenOpcional.get();

            orden.setEstado(estado);

            if(estado.getDenominacion().equals("demorado")){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(orden.getHorarioEntrega());
                calendar.add(Calendar.MINUTE, 10);
                orden.setHorarioEntrega(calendar.getTime());
            } else if (estado.getDenominacion().equals("terminado")){
                if(this.controlStock(orden.getDetalles())){
                    orden.setDetalles(this.removeStock(orden.getDetalles()));
                } else {
                    throw new Exception("Uno o más artículos están fuera de stock");
                }
            }

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            orden.setUltimaActualizacion(timestamp);

            return baseRepository.save(orden);

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    @Override
    public boolean controlStock(List<DetalleOrden> detalles) {
        for (DetalleOrden detalleOrdenAux : detalles){
            if(detalleOrdenAux.getArticuloManufacturado() != null){
                for (DetalleReceta detalleReceta: detalleOrdenAux.getArticuloManufacturado().getDetallesReceta()){
                    if (detalleReceta.getInsumo().getStockActual() < (detalleReceta.getCantidad() * detalleOrdenAux.getCantidad())){
                        return false;
                    }
                }
            }

            if(detalleOrdenAux.getInsumo() != null){
                if (detalleOrdenAux.getInsumo().getStockActual() < detalleOrdenAux.getCantidad()){
                    return false;
                }

            }
        }

        return true;
    }

    @Override
    public List<DetalleOrden> removeStock(List<DetalleOrden> detalles) throws Exception {

        try{
            Optional<ArticuloInsumo> insumoOptional;
            ArticuloInsumo articuloInsumo;

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            for (DetalleOrden detalleOrdenAux : detalles){
                if (detalleOrdenAux.getArticuloManufacturado() != null){

                    for (DetalleReceta detalleReceta: detalleOrdenAux.getArticuloManufacturado().getDetallesReceta()){

                        insumoOptional = insumoRepository.findById(detalleReceta.getInsumo().getId());
                        articuloInsumo = insumoOptional.get();
                        articuloInsumo.setStockActual(articuloInsumo.getStockActual() - (detalleReceta.getCantidad() * detalleOrdenAux.getCantidad()));
                        articuloInsumo.getHistorialStock().add(new HistorialStock((detalleReceta.getCantidad() * detalleOrdenAux.getCantidad()), timestamp, false));
                        articuloInsumo.setUltimaActualizacion(timestamp);
                        articuloInsumo = insumoRepository.save(articuloInsumo);
                        detalleReceta.setInsumo(articuloInsumo);
                    }
                }

                if(detalleOrdenAux.getInsumo() != null){
                    insumoOptional = insumoRepository.findById(detalleOrdenAux.getInsumo().getId());
                    articuloInsumo = insumoOptional.get();
                    articuloInsumo.setStockActual(articuloInsumo.getStockActual() - detalleOrdenAux.getCantidad());
                    articuloInsumo.getHistorialStock().add(new HistorialStock(detalleOrdenAux.getCantidad(), timestamp, false));
                    articuloInsumo.setUltimaActualizacion(timestamp);
                    articuloInsumo = insumoRepository.save(articuloInsumo);
                    detalleOrdenAux.setInsumo(articuloInsumo);
                }
            }

            return detalles;

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
