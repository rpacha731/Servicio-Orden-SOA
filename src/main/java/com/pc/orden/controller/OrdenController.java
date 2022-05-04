package com.pc.orden.controller;

import com.pc.orden.model.Orden;
import com.pc.orden.services.NegocioExcepcion;
import com.pc.orden.services.OrdenServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")

public class OrdenController {

    private final OrdenServiceImpl ordenServicio;

    @GetMapping("/ordenes")
    public ResponseEntity<?> getAllOrdenes() {
        List<Orden> ordenes;
        try {
            ordenes = this.ordenServicio.listado();
            return new ResponseEntity<>(ordenes, HttpStatus.OK);
        } catch (NegocioExcepcion e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

//    @GetMapping("/ordenes/{idUser}")
//    public ResponseEntity<?> getOrdenesUser(@PathVariable Integer idUser) throws NegocioExcepcion {
//        return new ResponseEntity<>(this.ordenServicio.listadoPorUser(idUser).size(), HttpStatus.OK);
//    }

}
