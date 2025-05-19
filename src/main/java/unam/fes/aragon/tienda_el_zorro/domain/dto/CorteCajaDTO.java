package unam.fes.aragon.tienda_el_zorro.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CorteCajaDTO {
    private String nombreProducto;
    private int cantidadVendida;
}
