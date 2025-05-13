package unam.fes.aragon.tienda_el_zorro.infraestructure.validations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.constants.BussinessConstants;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProveedorDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Proveedor;
import unam.fes.aragon.tienda_el_zorro.domain.error.DinError;
import unam.fes.aragon.tienda_el_zorro.domain.error.ErrorNegocio;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ProductoRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ProveedorRepository;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ProveedorValidator {

    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;

    public void validate(Proveedor proveedor) {
        if (proveedorRepository.findByName(proveedor.getNombre()) != null) {
            throw DinError.builder()
                    .error(ErrorNegocio.builder()
                            .mensaje(BussinessConstants.ERROR_EN_LA_CREACION)
                            .fecha(LocalDate.now().toString())
                            .detalle("EL proveedor " + proveedor.getNombre() + "ya esta registrado")
                            .origen(BussinessConstants.PROVEEDOR)
                            .codigo("Proveedor_001")
                            .build())
                    .build();
        }
    }

    public Proveedor validateExistence(ProveedorDTO proveedorDTO) {
        Proveedor proveedor = proveedorRepository.findByName(proveedorDTO.getNombre());
        if (proveedor == null) {
            throw DinError.builder()
                    .error(ErrorNegocio.builder()
                            .mensaje(BussinessConstants.ERROR_EN_LA_CREACION)
                            .fecha(LocalDate.now().toString())
                            .detalle("EL proveedor " + proveedorDTO.getNombre() + "no existe")
                            .origen(BussinessConstants.NO_EXISTE_PRODUCTO_PROVEEDOR)
                            .codigo("Proveedor_002")
                            .build())
                    .build();
        }
        return proveedor;
    }
}
