package com.delivery.demo.services.empleado;

import com.delivery.demo.entities.usuarios.Empleado;
import com.delivery.demo.entities.usuarios.Usuario;
import com.delivery.demo.services.base.BaseService;

public interface EmpleadoService extends BaseService<Empleado, Long> {
    public Empleado findByUID(String uid) throws Exception;
}
