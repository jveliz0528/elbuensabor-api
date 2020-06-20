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

    @GetMapping("/orden/{ordenId}")
    @Transactional
    public ResponseEntity<?> getFactura(@PathVariable Long ordenId){
        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.getOneByOrden(ordenId));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }
    }

    @PostMapping("/generar")
    @Transactional
    public ResponseEntity<?> generarFactura(@RequestParam Long ordenId, @RequestParam String cajeroUid) {

        try {

            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(ordenId, cajeroUid));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }

    @PutMapping("/anular/{id}")
    @Transactional
    public ResponseEntity<?> anularFactura(@PathVariable long id) {

        try {

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.anularFactura(id));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }
}
