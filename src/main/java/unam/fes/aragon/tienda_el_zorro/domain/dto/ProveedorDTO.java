package unam.fes.aragon.tienda_el_zorro.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorDTO {
    @JsonIgnore
    private Long id;
    private String nombre;
    private String correo;
    private String status;
    @JsonIgnore
    private List<Long> productosIds;
} 