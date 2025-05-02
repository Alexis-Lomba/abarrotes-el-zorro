package unam.fes.aragon.tienda_el_zorro.domain.constants;

import lombok.Data;

@Data
public class BussinessConstants {

    private BussinessConstants() {
    }

    public static final String CREADO_CORRECTAMENTE = "Se ha creado de manera correcta";
    public static final String ERROR_EN_LA_CREACION = "Ya existe un registro";
    public static final String CLIENTE = "Cliente";
    public static final String PROVEEDOR = "Proveedor";
}
