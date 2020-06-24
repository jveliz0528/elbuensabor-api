package com.delivery.demo.controllers;

import com.delivery.demo.entities.articulos.ArticuloInsumo;
import com.delivery.demo.services.insumo.ArticuloInsumoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/articulos/insumos")
@Transactional
public class ArticuloInsumoController extends BaseController<ArticuloInsumo, ArticuloInsumoServiceImpl> {
    @PutMapping("/addStock/{id}")
    @Transactional
    public ResponseEntity<?> addStock(@PathVariable long id,
                                      @RequestParam(defaultValue = "0") double cantidad) {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.addStock(id, cantidad));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }

    @PutMapping("/removeStock/{id}")
    @Transactional
    public ResponseEntity<?> removeStock(@PathVariable long id,
                                         @RequestParam(defaultValue = "0") double cantidad) {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.removeStock(id, cantidad));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }

    @GetMapping("/bebidas")
    public ResponseEntity<Map<String, Object>> getBebidas(
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(service.getBebidas(filter, page, size, sortBy, direction));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
