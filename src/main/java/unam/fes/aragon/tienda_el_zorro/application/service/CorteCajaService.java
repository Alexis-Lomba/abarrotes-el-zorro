package unam.fes.aragon.tienda_el_zorro.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.dto.CorteCajaDTO;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.FacturaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface CorteCajaService {

    List<CorteCajaDTO> obtenerCorteDeCajaPorFecha(LocalDate fecha);

}
