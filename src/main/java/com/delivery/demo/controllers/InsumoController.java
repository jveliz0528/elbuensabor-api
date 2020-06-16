package com.delivery.demo.controllers;

import com.delivery.demo.entities.articulos.Insumo;
import com.delivery.demo.services.insumo.InsumoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/articulo/insumo")
@Transactional
public class InsumoController extends BaseController<Insumo, InsumoServiceImpl> {
}
