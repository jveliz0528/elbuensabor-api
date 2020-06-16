package com.delivery.demo.controllers;

import com.delivery.demo.entities.usuarios.Empleado;
import com.delivery.demo.services.empleado.EmpleadoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/usuarios/empleados")
@Transactional
public class EmpleadoController extends BaseController<Empleado, EmpleadoServiceImpl> {
}
