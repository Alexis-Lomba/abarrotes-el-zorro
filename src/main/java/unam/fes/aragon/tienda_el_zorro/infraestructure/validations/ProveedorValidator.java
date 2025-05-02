package unam.fes.aragon.tienda_el_zorro.infraestructure.validations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.constants.BussinessConstants;
import unam.fes.aragon.tienda_el_zorro.domain.error.DinError;
import unam.fes.aragon.tienda_el_zorro.domain.error.ErrorNegocio;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ProveedorRepository;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ProveedorValidator {

    private ProveedorRepository proveedorRepository;

    public void validate(String nombre) {
        if (proveedorRepository.findByName(nombre) != null) {
            throw DinError.builder()
                    .error(ErrorNegocio.builder()
                            .mensaje(BussinessConstants.ERROR_EN_LA_CREACION)
                            .fecha(LocalDate.now().toString())
                            .detalle("EL proveedor " + nombre + "ya esta registrado")
                            .origen(BussinessConstants.PROVEEDOR)
                            .codigo("Proveedor_001")
                            .build())
                    .build();
        }
    }
}
