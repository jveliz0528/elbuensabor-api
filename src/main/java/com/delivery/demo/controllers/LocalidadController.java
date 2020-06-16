package com.delivery.demo.controllers;

import com.delivery.demo.entities.direccion.Localidad;
import com.delivery.demo.services.localidad.LocalidadServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/direcciones/localidad")
@Transactional
public class LocalidadController extends BaseController<Localidad, LocalidadServiceImpl> {
}
