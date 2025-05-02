package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper;

import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.constants.BussinessConstants;
import unam.fes.aragon.tienda_el_zorro.domain.error.DinError;
import unam.fes.aragon.tienda_el_zorro.domain.error.ErrorNegocio;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ClienteRepository;

import java.time.LocalDate;

@Service
public class ValidateEmail {

    private ClienteRepository clienteRepository;

    public void validate(String email) {
        if (clienteRepository.findByEmail(email) != null) {
            throw DinError.builder()
                    .error(ErrorNegocio.builder()
                            .mensaje(BussinessConstants.ERROR_EN_LA_CREACION)
                            .fecha(LocalDate.now().toString())
                            .detalle("EL correo " + email + "ya esta registrado")
                            .origen(BussinessConstants.CLIENTE)
                            .codigo("CLIENTE_001")
                            .build())
                    .build();
        }
    }
}
