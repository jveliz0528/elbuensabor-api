package com.delivery.demo.controllers;

import com.delivery.demo.entities.DatosEmpresa;
import com.delivery.demo.services.datosEmpresa.DatosEmpresaServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/datosEmpresa")
@Transactional
public class DatosEmpresaController extends BaseController<DatosEmpresa, DatosEmpresaServiceImpl> {
}
