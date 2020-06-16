package com.delivery.demo.controllers;

import com.delivery.demo.entities.direccion.Provincia;
import com.delivery.demo.services.provincia.ProvinciaServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/direcciones/provincias")
@Transactional
public class ProvinciaController extends BaseController<Provincia, ProvinciaServiceImpl> {
}
