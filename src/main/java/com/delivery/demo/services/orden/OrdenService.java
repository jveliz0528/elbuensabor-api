package com.delivery.demo.services.orden;

import com.delivery.demo.entities.comprobantes.DetalleOrden;
import com.delivery.demo.entities.comprobantes.Estado;
import com.delivery.demo.entities.comprobantes.Orden;
import com.delivery.demo.entities.usuarios.Empleado;
import com.delivery.demo.services.base.BaseService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrdenService extends BaseService<Orden, Long> {
    public Orden save(Orden orden, String clienteUid) throws Exception;
    public int calcularTiempoTotalPreparacion (List<DetalleOrden> detalleOrden);
    public Date calcularHorarioEntrega(Date fechaEntrada, int tiempoOrdenActual, boolean delivery) throws Exception;
    public Orden addRepartidor(Empleado repartidor, Long ordenId) throws Exception;
    public Orden actualizarEstado(Estado estado, Long ordenId) throws Exception;
    public boolean controlStock(List<DetalleOrden> detalles);
    public List<DetalleOrden> removeStock(List<DetalleOrden> detalles) throws Exception;
    public Map<String, Object> ordenesEnCocina(String filter, int page, int size, String sortBy, String direction) throws Exception;
    public List<Orden> getOrdenesPendientes(String clienteUid) throws Exception;
    public List<Orden> getOrdenesPasadas(String clienteUid) throws Exception;
}
