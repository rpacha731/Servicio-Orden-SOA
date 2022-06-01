package com.pc.orden.services;

import com.pc.orden.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class OrdenServiceImpl implements OrdenService {

    private final OrdenRepository ordenRepository;
    private final RestTemplate restTemplate;
    private final AmqpTemplate queueSender;

    @Override
    public List<Orden> listado() throws NegocioExcepcion, NotFoundException {
        try {
            List<Orden> ordenes = this.ordenRepository.findAll();
            if (ordenes.isEmpty()) throw new NotFoundException("La base de datos está vacía");
            return ordenes;
        } catch (Exception e) {
            throw new NegocioExcepcion(e.getMessage());
        }
    }

    // Devuelve el producto ID y la cantidad
    @Override
    public OrdenDTO ordenDTOPorId(Integer id) throws NegocioExcepcion, NotFoundException {
        try {
            Orden ordencita = this.ordenRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("No se encuentra la orden con id = " + id));
            return OrdenDTO.builder()
                    .productoId(ordencita.getProductoId())
                    .cantidad(ordencita.getCantidad()).build();
        } catch (Exception e) {
            throw new NegocioExcepcion(e.getMessage());
        }
    }

    @Override
    public void crearOrden(OrdenDTOFront ordenDTOFront) throws NegocioExcepcion, NotFoundException {
        try {
            log.warn(ordenDTOFront.toString());

            ResponseEntity<ResponseProducto> responseProducto = this.restTemplate.getForEntity("http://ec2-18-215-154-77.compute-1.amazonaws.com:8081/productos/" + ordenDTOFront.getProductoId() + "/" + ordenDTOFront.getCantidad(),
                    ResponseProducto.class);
            if (!responseProducto.getStatusCode().is2xxSuccessful())
                throw new NegocioExcepcion("No se pudo completar la compra, no se conecta con productos");

            if (!Objects.requireNonNull(responseProducto.getBody()).getStock())
                throw new NotFoundException("No hay stock del producto");

            Orden orden = this.ordenRepository.save(Orden.builder()
                    .fecha(new Date())
                    .cantidad(ordenDTOFront.getCantidad())
                    .productoId(ordenDTOFront.getProductoId())
                    .total(ordenDTOFront.getCantidad() * responseProducto.getBody().getPrecio()).build());

            this.queueSender.convertAndSend("rabbitQueue", orden.getId().toString());

        } catch (Exception e) {
            throw new NegocioExcepcion(e.getMessage());
        }
    }

}
