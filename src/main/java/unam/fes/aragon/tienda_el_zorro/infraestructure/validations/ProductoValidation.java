package unam.fes.aragon.tienda_el_zorro.infraestructure.validations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.constants.BussinessConstants;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProductoDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Inventario;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Producto;
import unam.fes.aragon.tienda_el_zorro.domain.error.DinError;
import unam.fes.aragon.tienda_el_zorro.domain.error.ErrorNegocio;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.InventarioRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ProductoRepository;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class ProductoValidation {

    private final ProductoRepository productoRepository;
    private final InventarioRepository inventarioRepository;

    public void validate(ProductoDTO producto, Long id) {
        if (productoRepository.findByNameAndProveedorId(producto.getNombre(), id) != null) {
            throw DinError.builder()
                    .error(ErrorNegocio.builder()
                            .mensaje(BussinessConstants.ERROR_EN_LA_CREACION)
                            .fecha(LocalDate.now().toString())
                            .detalle("EL producto " + producto.getNombre() + "ya esta registrado")
                            .origen(BussinessConstants.PROVEEDOR)
                            .codigo("Producto_001")
                            .build())
                    .build();
        }
    }

    public Producto validateExistence(ProductoDTO productoDTO, Long id, Integer cantidad) {
        Producto producto = productoRepository.findByNameAndProveedorId(productoDTO.getNombre(), id);
        if (producto == null) {
            throw DinError.builder()
                    .error(ErrorNegocio.builder()
                            .mensaje(BussinessConstants.ERROR_EN_LA_CREACION)
                            .fecha(LocalDate.now().toString())
                            .detalle("EL producto " + productoDTO.getNombre() + "del proveedor" + "no existe")
                            .origen(BussinessConstants.NO_EXISTE_PRODUCTO_PROVEEDOR)
                            .codigo("Producto_002")
                            .build())
                    .build();
        }

        validateQuantity(producto, cantidad);

        return producto;
    }

    public void validateQuantity(Producto producto, Integer compra) {
        Inventario inventario = inventarioRepository.findByProductoId(producto.getId());
        Integer stock = inventario.getCantidadActual();
        if(compra > stock) {
            throw DinError.builder()
                    .error(ErrorNegocio.builder()
                            .mensaje(BussinessConstants.ERROR_EN_LA_CREACION)
                            .fecha(LocalDate.now().toString())
                            .detalle("Ya no se tiene stock del producto " + producto.getNombre() +
                                    "para la cantidad de " + compra)
                            .origen(BussinessConstants.NO_EXISTE_PRODUCTO_PROVEEDOR)
                            .codigo("Producto_002")
                            .build())
                    .build();
        }
    }
}
