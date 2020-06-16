package com.delivery.demo.controllers;

import com.delivery.demo.entities.direccion.Pais;
import com.delivery.demo.services.pais.PaisServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/direcciones/paises")
@Transactional
public class PaisController extends BaseController<Pais, PaisServiceImpl> {
}
