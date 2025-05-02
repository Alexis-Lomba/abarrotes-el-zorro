package unam.fes.aragon.tienda_el_zorro.domain.error;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class ErrorNegocio extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;
    private String mensaje;


}
