package com.pc.orden.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrdenDTOFront {

    private Integer productoId;

    private Integer cantidad;

    private Double precio;

}
