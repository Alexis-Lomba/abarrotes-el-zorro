package unam.fes.aragon.tienda_el_zorro.infraestructure.validations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.constants.BussinessConstants;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProductoDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Proveedor;
import unam.fes.aragon.tienda_el_zorro.domain.error.DinError;
import unam.fes.aragon.tienda_el_zorro.domain.error.ErrorNegocio;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ProductoRepository;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class ProductoValidation {

    private final ProductoRepository productoRepository;

    public void validate(ProductoDTO proveedor, Long id) {
        if (productoRepository.findByNameAndProveedorId(proveedor.getNombre(), id) != null) {
            throw DinError.builder()
                    .error(ErrorNegocio.builder()
                            .mensaje(BussinessConstants.ERROR_EN_LA_CREACION)
                            .fecha(LocalDate.now().toString())
                            .detalle("EL producto " + proveedor.getNombre() + "ya esta registrado")
                            .origen(BussinessConstants.PROVEEDOR)
                            .codigo("Producto_001")
                            .build())
                    .build();
        }
    }
}
