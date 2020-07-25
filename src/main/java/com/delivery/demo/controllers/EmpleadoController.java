package com.delivery.demo.controllers;

import com.delivery.demo.entities.usuarios.Empleado;
import com.delivery.demo.services.empleado.EmpleadoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/usuarios/empleados")
@Transactional
public class EmpleadoController extends BaseController<Empleado, EmpleadoServiceImpl> {
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

    @GetMapping("/cuil/{cuil}")
    @Transactional
    public ResponseEntity<?> existByCuil(@PathVariable String cuil) {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.existByCuil(cuil));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }

    }
}
