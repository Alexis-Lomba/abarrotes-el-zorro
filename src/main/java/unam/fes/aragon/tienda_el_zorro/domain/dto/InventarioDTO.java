package unam.fes.aragon.tienda_el_zorro.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDTO {
    private Long id;
    private Integer cantidadActual;
    private Integer cantidadInicial;
    private Integer minimoRequerido;
} 