package unam.fes.aragon.tienda_el_zorro.domain.error;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class DinError extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1L;
    private final ErrorNegocio error;

    public DinError(ErrorNegocio error) {
        this.error = error;
    }
}
