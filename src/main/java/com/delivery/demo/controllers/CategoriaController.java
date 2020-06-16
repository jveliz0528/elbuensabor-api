package com.delivery.demo.controllers;

import com.delivery.demo.entities.articulos.Categoria;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.services.base.BaseServiceImpl;
import com.delivery.demo.services.categoria.CategoriaServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/articulo/categoria")
@Transactional
public class CategoriaController extends BaseController<Categoria, CategoriaServiceImpl> {
}
