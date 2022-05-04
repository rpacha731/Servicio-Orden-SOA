package com.pc.orden.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrdenDTO {

    private Integer productoId;

    private Integer cantidad;

}
