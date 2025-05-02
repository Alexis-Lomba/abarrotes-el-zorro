package unam.fes.aragon.tienda_el_zorro.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String imagenUrl;
    private Long proveedorId;
    private List<Long> detallesIds;
    private Long inventarioId;
} 