package com.delivery.demo.controllers;

import com.delivery.demo.entities.articulos.Manufacturado;
import com.delivery.demo.services.manufacturado.ManufacturadoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/articulo/manufacturado")
@Transactional
public class ManufacturadoController extends BaseController<Manufacturado, ManufacturadoServiceImpl> {
}
