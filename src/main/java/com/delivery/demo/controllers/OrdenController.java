package com.delivery.demo.controllers;

import com.delivery.demo.entities.comprobantes.Estado;
import com.delivery.demo.entities.comprobantes.Orden;
import com.delivery.demo.entities.usuarios.Empleado;
import com.delivery.demo.services.orden.OrdenServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

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

    @PutMapping("/estado/{ordenId}")
    public ResponseEntity<?> actualizarEstado(@RequestBody Estado estado, @PathVariable Long ordenId) {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.actualizarEstado(estado, ordenId));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }
    }

    @PutMapping("/repartidor/{ordenId}")
    public ResponseEntity<?> agregarRepartidor(@RequestBody Empleado repartidor, @PathVariable Long ordenId){
        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.addRepartidor(repartidor, ordenId));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }
    }

    @GetMapping("/cocina")
    public ResponseEntity<Map<String, Object>> getOrdenesEnCocina(
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(service.ordenesEnCocina(filter, page, size, sortBy, direction));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pendientes")
    public ResponseEntity<?> getOrdenesPendientes(@RequestParam String clienteUid) {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.getOrdenesPendientes(clienteUid));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }
    }

    @GetMapping("/pasadas")
    public ResponseEntity<?> getOrdenesPasadas(@RequestParam String clienteUid) {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.getOrdenesPasadas(clienteUid));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                    ("{\"error\": \""+e.getMessage()+"\"}");

        }
    }

    @GetMapping("/time")
    public ResponseEntity<?> getLocalTime(){
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            return ResponseEntity.status(HttpStatus.OK).body("{\"date\": \""+df.format(now)+"\", \"time\": \""+tf.format(now)+"\"}");
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
