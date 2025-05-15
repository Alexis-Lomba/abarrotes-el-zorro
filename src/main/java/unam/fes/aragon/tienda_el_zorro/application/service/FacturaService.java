package unam.fes.aragon.tienda_el_zorro.application.service;

import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.FacturaDTO;

import java.util.List;

public interface FacturaService {
    FacturaDTO createFactura(FacturaDTO facturaDTO);

    List<FacturaDTO> findAllByClientNombre(String nombre);

    List<FacturaDTO> findAllByDay(FacturaDTO facturaDTO);

    List<FacturaDTO> findAllByUsuarioNombre(String nombre);


}