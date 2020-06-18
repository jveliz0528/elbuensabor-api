package com.delivery.demo.controllers;

import com.delivery.demo.entities.comprobantes.Estado;
import com.delivery.demo.entities.comprobantes.Orden;
import com.delivery.demo.services.orden.OrdenServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/comprobantes/ordenes")
@Transactional
public class OrdenController extends BaseController<Orden, OrdenServiceImpl> {
    @PostMapping("/save")
    public ResponseEntity<?> post(@RequestBody Orden entityForm, @RequestParam String clienteUid) {

        try {


            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(entityForm, clienteUid));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }

    @PutMapping("formulario PPS{ordenId}")
    public ResponseEntity<?> actualizarEstado(@RequestBody Estado estado, @PathVariable Long ordenId) {
        try {

            return ResponseEntity.status(HttpStatus.CREATED).body(service.actualizarEstado(estado, ordenId));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }
    }
}
