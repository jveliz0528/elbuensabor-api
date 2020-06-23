package com.delivery.demo.services.cliente;

import com.delivery.demo.entities.direccion.DireccionDelivery;
import com.delivery.demo.entities.usuarios.Cliente;
import com.delivery.demo.entities.usuarios.Usuario;
import com.delivery.demo.services.base.BaseService;

import java.util.List;

public interface ClienteService extends BaseService<Cliente, Long> {
    public Cliente addDireccion(DireccionDelivery direccion, String uid) throws Exception;
    public boolean removeDireccion(Long direccionId, String uid) throws Exception;
    public List<DireccionDelivery> getDirecciones(String uid) throws Exception;
    public Cliente findByUID(String uid) throws Exception;
}
