package com.delivery.demo.controllers;

import com.delivery.demo.entities.comprobantes.Orden;
import com.delivery.demo.services.orden.OrdenServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/orden")
@Transactional
public class OrdenController extends BaseController<Orden, OrdenServiceImpl> {
}
