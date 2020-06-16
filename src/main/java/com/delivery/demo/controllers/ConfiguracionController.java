package com.delivery.demo.controllers;

import com.delivery.demo.entities.Configuracion;
import com.delivery.demo.services.configuracion.ConfiguracionServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/configuracion")
@Transactional
public class ConfiguracionController extends BaseController<Configuracion, ConfiguracionServiceImpl> {
}
