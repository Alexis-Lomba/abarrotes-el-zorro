package unam.fes.aragon.tienda_el_zorro.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacturaDTO {
    private Long id;
    private LocalDateTime fecha;
    private Long clienteId;

    // Adding JsonIgnoreProperties to break potential circular references
    @JsonIgnoreProperties("facturaId")
    private List<DetalleFacturaDTO> productos;

    private Long ventaId;
    private Float total;
    private Long usuarioId;
}