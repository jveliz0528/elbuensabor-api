package com.delivery.demo.controllers;

import com.delivery.demo.entities.comprobantes.Estado;
import com.delivery.demo.services.estado.EstadoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/comprobante/estado")
@Transactional
public class EstadoController extends BaseController<Estado, EstadoServiceImpl> {
}
