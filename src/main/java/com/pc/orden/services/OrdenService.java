package com.pc.orden.services;

import com.pc.orden.model.Orden;
import com.pc.orden.model.OrdenDTO;

import java.util.List;

public interface OrdenService {

    List<Orden> listado() throws NegocioExcepcion;

    OrdenDTO ordenDTOPorId(Integer id) throws NegocioExcepcion;

    Orden crearOrden(OrdenDTO ordenDTO) throws NegocioExcepcion;
}
