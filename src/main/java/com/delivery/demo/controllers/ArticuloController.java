package com.delivery.demo.controllers;

import com.delivery.demo.entities.articulos.Articulo;
import com.delivery.demo.services.articulo.ArticuloServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/articulos")
@Transactional
public class ArticuloController extends BaseController<Articulo, ArticuloServiceImpl> {
}
