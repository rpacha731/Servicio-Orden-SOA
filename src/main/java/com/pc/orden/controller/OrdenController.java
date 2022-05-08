package com.pc.orden.controller;

import com.pc.orden.model.Orden;
import com.pc.orden.model.OrdenDTO;
import com.pc.orden.model.OrdenDTOFront;
import com.pc.orden.services.NegocioExcepcion;
import com.pc.orden.services.NotFoundException;
import com.pc.orden.services.OrdenServiceImpl;
import lombok.AllArgsConstructor;
//import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")

public class OrdenController {

    private final OrdenServiceImpl ordenServicio;
//    private final AmqpTemplate queueSender;

    @GetMapping("/ordenes")
    public ResponseEntity<?> getAllOrdenes() {
        List<Orden> ordenes;
        try {
            ordenes = this.ordenServicio.listado();
            return new ResponseEntity<>(ordenes, HttpStatus.OK);
        } catch (NegocioExcepcion e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ordenes/{id}")
    public ResponseEntity<?> getOrdenesUser(@PathVariable Integer id) {
        OrdenDTO ordenDTO;
        try {
            ordenDTO = this.ordenServicio.ordenDTOPorId(id);
            return new ResponseEntity<>(ordenDTO, HttpStatus.OK);
        } catch (NegocioExcepcion e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/ordenes")
    public ResponseEntity<?> nuevaOrden (@RequestBody OrdenDTOFront ordenDTOFront) {
        try {
            this.ordenServicio.crearOrden(ordenDTOFront);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NegocioExcepcion e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/test")
//    public String send(){
//
//        String mensage = "test message";
//
//        queueSender.convertAndSend("rabbitQueue", mensage); //exc -
//        return "ok. done";
//    }
}
