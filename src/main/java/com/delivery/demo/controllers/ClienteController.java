package com.delivery.demo.controllers;

import com.delivery.demo.entities.usuarios.Cliente;
import com.delivery.demo.services.cliente.ClienteServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/usuarios/clientes")
@Transactional
public class ClienteController extends BaseController<Cliente, ClienteServiceImpl> {
}
