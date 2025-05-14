package unam.fes.aragon.tienda_el_zorro.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleFacturaDTO {
    private Long id;
    private Integer cantidad;
    private Double precioUnitario;
    private Long facturaId;
    private Long productoId;

}