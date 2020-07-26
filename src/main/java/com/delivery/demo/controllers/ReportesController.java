package com.delivery.demo.controllers;

import com.delivery.demo.services.reportes.ReportesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/reportes")
@Transactional
public class ReportesController {
    @Autowired
    ReportesService reportesService;

    @GetMapping("/stock")
    @Transactional
    public ResponseEntity<?> getInsumosOutOfStock() {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(reportesService.getOutOfStock());

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }

    @GetMapping("/ordenes")
    public ResponseEntity<?> getOrdenesPorPeriodo(
            @RequestParam String clienteUid,
            @RequestParam Date fechaInicio,
            @RequestParam Date fechaFin) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(reportesService.getOrdenesPorPeriodo(clienteUid, fechaInicio, fechaFin));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ingresos")
    public ResponseEntity<?> getIngresosPorPeriodo(
            @RequestParam Date fechaInicio,
            @RequestParam Date fechaFin) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(reportesService.getIngresosPorPeriodo(fechaInicio, fechaFin));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/topInsumos")
    public ResponseEntity<?> getTopInsumos(
            @RequestParam Date fechaInicio,
            @RequestParam Date fechaFin) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(reportesService.getInsumoMasVendido(fechaInicio, fechaFin));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/topManufacturados")
    public ResponseEntity<?> getTopManufacturados(
            @RequestParam Date fechaInicio,
            @RequestParam Date fechaFin) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(reportesService.getManufacturadoMasVendido(fechaInicio, fechaFin));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cantidadOrdenes")
    public ResponseEntity<?> getOrdenes(
            @RequestParam Date fechaInicio,
            @RequestParam Date fechaFin) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(reportesService.getOrdenesPorCliente(fechaInicio, fechaFin));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
