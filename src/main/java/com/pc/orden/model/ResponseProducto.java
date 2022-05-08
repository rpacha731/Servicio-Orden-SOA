package com.pc.orden.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseProducto {
    private Boolean stock;
    private Double precio;
}
