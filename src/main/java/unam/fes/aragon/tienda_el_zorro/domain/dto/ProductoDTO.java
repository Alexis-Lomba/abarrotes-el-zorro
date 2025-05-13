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
public class ProductoDTO {
    @JsonIgnore
    private Long id;
    private String nombre;
    private String descripcion;
    private Float precio;
    private String imagenUrl;
    private String status;
    private Long proveedorId;
    private InventarioDTO inventarioDTO;
} 