package com.delivery.demo.controllers;

import com.delivery.demo.entities.articulos.Rubro;
import com.delivery.demo.services.rubro.RubroServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/articulos/rubros")
@Transactional
public class RubroController extends BaseController<Rubro, RubroServiceImpl> {

    @GetMapping("/bebidas")
    public ResponseEntity<?> getRubrosBebidas() {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.getRubrosBebidas());

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }

}
