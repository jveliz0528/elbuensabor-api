package com.delivery.demo.controllers;

import com.delivery.demo.entities.articulos.Rubro;
import com.delivery.demo.services.rubro.RubroServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/articulos/rubros")
@Transactional
public class RubroController extends BaseController<Rubro, RubroServiceImpl> {
}
