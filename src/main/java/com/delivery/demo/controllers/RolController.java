package com.delivery.demo.controllers;

import com.delivery.demo.entities.usuarios.Rol;
import com.delivery.demo.services.rol.RolServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/usuarios/roles")
@Transactional
public class RolController extends BaseController<Rol, RolServiceImpl> {
}
