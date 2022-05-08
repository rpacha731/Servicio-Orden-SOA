package com.pc.orden.services;

import com.pc.orden.model.Orden;
import com.pc.orden.model.OrdenDTO;
import com.pc.orden.model.OrdenDTOFront;

import java.util.List;

public interface OrdenService {

    List<Orden> listado() throws NegocioExcepcion, NotFoundException;

    OrdenDTO ordenDTOPorId(Integer id) throws NegocioExcepcion, NotFoundException;

    void crearOrden(OrdenDTOFront ordenDTOFront) throws NegocioExcepcion, NotFoundException;
}
