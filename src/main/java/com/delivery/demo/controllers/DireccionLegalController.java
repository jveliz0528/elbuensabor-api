package com.delivery.demo.controllers;

import com.delivery.demo.entities.direccion.DireccionLegal;
import com.delivery.demo.services.direccionLegal.DireccionLegalServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/direcciones/legal")
@Transactional
public class DireccionLegalController extends BaseController<DireccionLegal, DireccionLegalServiceImpl> {
}
