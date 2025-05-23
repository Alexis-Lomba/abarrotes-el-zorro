package unam.fes.aragon.tienda_el_zorro.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDTO {
    @JsonIgnore
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private List<Long> facturasIds;
    private String status;
} 