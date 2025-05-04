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

    public static final String NO_EXISTE_PROVEEDOR = "No existe ese proveedor";
    public static final String NO_EXISTE_PRODUCTO_PROVEEDOR = "No existe ese producto del proveedor";
    public static final String NO_HAY_STOCK = "No hay stock suficiente";
    public static final String ERROR_EN_LA_BUSQUEDA = "No existe el registro";
}
