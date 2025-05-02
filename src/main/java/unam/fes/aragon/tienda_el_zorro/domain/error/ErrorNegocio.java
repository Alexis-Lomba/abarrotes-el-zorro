package unam.fes.aragon.tienda_el_zorro.domain.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorNegocio implements Serializable {
    private String fecha;
    private String origen;
    private String codigo;
    private String codigoErrorProveedor;
    private String mensaje;
    private String detalle;
}
