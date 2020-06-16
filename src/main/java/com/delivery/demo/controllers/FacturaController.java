package com.delivery.demo.controllers;

import com.delivery.demo.entities.comprobantes.Factura;
import com.delivery.demo.services.factura.FacturaServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/comprobante/factura")
@Transactional
public class FacturaController extends BaseController<Factura, FacturaServiceImpl> {
}
