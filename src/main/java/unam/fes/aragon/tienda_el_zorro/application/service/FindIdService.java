package unam.fes.aragon.tienda_el_zorro.application.service;

import unam.fes.aragon.tienda_el_zorro.domain.dto.InventarioDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.*;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.*;

public interface FindIdService {

    Cliente findIdCliente(Long id);
    DetalleFactura findIdDetalle(Long id);
    Factura findIdFactura(Long id);
    Inventario findIdInventario(Long id);
    Producto findIdProducto(Long id);
    Proveedor findIdProveedor(Long id);
    Rol findIdRol(Long id);
    Usuario findIdUsuario(Long id);
    Venta findIdVenta(Long id);

}
