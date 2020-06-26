package com.delivery.demo.controllers;

import com.delivery.demo.entities.direccion.DireccionDelivery;
import com.delivery.demo.entities.usuarios.Cliente;
import com.delivery.demo.services.cliente.ClienteServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/usuarios/clientes")
@Transactional
public class ClienteController extends BaseController<Cliente, ClienteServiceImpl> {

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

    @PutMapping("/direccion/add")
    public ResponseEntity<?> addDireccion(@RequestBody DireccionDelivery direccion, @RequestParam String clienteUid ) {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.addDireccion(direccion, clienteUid));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("{\"error\": \""+e.getMessage()+"\"}");
        }

    }

    @GetMapping("/direcciones")
    public ResponseEntity<?> getDirecciones(@RequestParam String clienteUid ) {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.getDirecciones(clienteUid));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("{\"error\": \""+e.getMessage()+"\"}");
        }

    }

}
