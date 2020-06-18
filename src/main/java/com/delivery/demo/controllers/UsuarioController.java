package com.delivery.demo.controllers;

import com.delivery.demo.entities.usuarios.Usuario;
import com.delivery.demo.services.usuario.UsuarioServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/usuarios")
@Transactional
public class UsuarioController extends BaseController<Usuario, UsuarioServiceImpl> {

    @GetMapping("/current/{uid}")
    @Transactional
    public ResponseEntity<?> getOneByUID(@PathVariable String uid) {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.findByUID(uid));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }
}
