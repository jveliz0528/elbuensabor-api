package com.delivery.demo.controllers;

import com.delivery.demo.entities.articulos.ArticuloManufacturado;
import com.delivery.demo.services.manufacturado.ArticuloManufacturadoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/articulos/manufacturados")
@Transactional
public class ArticuloManufacturadoController extends BaseController<ArticuloManufacturado, ArticuloManufacturadoServiceImpl> {

    @GetMapping("/allPublic")
    public ResponseEntity<?> findAllPublic(@RequestParam(required = false) String filter) {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.findAllPublic(filter));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }
}
