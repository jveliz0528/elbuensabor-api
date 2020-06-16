package com.delivery.demo.controllers;

import com.delivery.demo.entities.usuarios.Usuario;
import com.delivery.demo.services.usuario.UsuarioServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/usuario")
@Transactional
public class UsuarioController extends BaseController<Usuario, UsuarioServiceImpl> {
}
