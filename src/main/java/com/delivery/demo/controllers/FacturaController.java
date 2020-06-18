package com.delivery.demo.controllers;

import com.delivery.demo.entities.comprobantes.Factura;
import com.delivery.demo.entities.comprobantes.Orden;
import com.delivery.demo.services.factura.FacturaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/comprobantes/facturas")
@Transactional
public class FacturaController extends BaseController<Factura, FacturaServiceImpl> {

    @PostMapping("/generar")
    @Transactional
    public ResponseEntity<?> generarFactura(@RequestBody Orden orden, @RequestParam String cajeroUid) {

        try {


            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(orden, cajeroUid));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }

    @PutMapping("/anular/{id}")
    public ResponseEntity<?> anularFactura(@PathVariable long id) {

        try {

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.anularFactura(id));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }
}
