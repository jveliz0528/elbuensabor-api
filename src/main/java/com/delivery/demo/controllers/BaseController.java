package com.delivery.demo.controllers;

import com.delivery.demo.entities.Base;
import com.delivery.demo.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Map;

public class BaseController <E extends Base, S extends BaseServiceImpl<E, Long>> {

    @Autowired
    protected S service;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllPaged(
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(service.findAll(filter, page, size, sortBy, direction));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    @Transactional
    public ResponseEntity<?>  getAll(@RequestParam(required = false) String filter) {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.findAll(filter));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<?>  getOne(@PathVariable long id) {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }

    @PostMapping("")
    @Transactional
    public ResponseEntity<?> post(@RequestBody E entityForm) {

        try {


            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(entityForm));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> put(@PathVariable long id, @RequestBody E entityForm) {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, entityForm));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("{\"error\": \""+e.getMessage()+"\"}");
        }

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable long id) {

        try {

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }

    @PutMapping("/undoDelete/{id}")
    @Transactional
    public ResponseEntity<?> undoDelete(@PathVariable long id) {

        try {

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.undoDelete(id));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }

    @PutMapping("/hide/{id}")
    @Transactional
    public ResponseEntity<?> hide(@PathVariable long id) {

        try {

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.hide(id));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }

    @PutMapping("/unhide/{id}")
    @Transactional
    public ResponseEntity<?> undoHide(@PathVariable long id) {

        try {

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.unhide(id));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }
}
