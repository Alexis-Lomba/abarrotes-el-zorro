package unam.fes.aragon.tienda_el_zorro.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetalleFacturaDTO {
    private Long id;
    private Integer cantidad;
    private Double precioUnitario;

    // Adding JsonIgnoreProperties to break potential circular references
    @JsonIgnoreProperties("productos")
    private Long facturaId;

    private Long productoId;
}