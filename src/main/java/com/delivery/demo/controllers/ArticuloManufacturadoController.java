package com.delivery.demo.controllers;

import com.delivery.demo.entities.articulos.ArticuloManufacturado;
import com.delivery.demo.services.manufacturado.ArticuloManufacturadoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/articulos/manufacturados")
@Transactional
public class ArticuloManufacturadoController extends BaseController<ArticuloManufacturado, ArticuloManufacturadoServiceImpl> {
}
