package com.pc.orden.services;

import com.pc.orden.model.Orden;
import com.pc.orden.model.OrdenDTO;
import com.pc.orden.model.OrdenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OrdenServiceImpl implements OrdenService {

    private final OrdenRepository ordenRepository;

    @Override
    public List<Orden> listado() throws NegocioExcepcion {
        try {
            return this.ordenRepository.findAll();
        } catch (Exception e) {
            throw new NegocioExcepcion(e.getMessage());
        }
    }

    // Devuelve el producto ID y la cantidad
    @Override
    public OrdenDTO ordenDTOPorId(Integer id) throws NegocioExcepcion {
        try {
            Orden ordencita = this.ordenRepository.findById(id)
                    .orElseThrow(() -> new NegocioExcepcion("No se encuentra la orden con id = " + id));
            return OrdenDTO.builder()
                    .productoId(ordencita.getProductoId())
                    .cantidad(ordencita.getCantidad()).build();
        } catch (Exception e) {
            throw new NegocioExcepcion(e.getMessage());
        }
    }

    @Override
    public Orden crearOrden(OrdenDTO ordenDTO) throws NegocioExcepcion {
        try {
            return this.ordenRepository.save(Orden.builder()
                    .fecha(new Date())
                    .cantidad(ordenDTO.getCantidad())
                    .productoId(ordenDTO.getProductoId())
                    .total(ordenDTO.getCantidad() * 10D).build());
        } catch (Exception e) {
            throw new NegocioExcepcion(e.getMessage());
        }
    }

}
