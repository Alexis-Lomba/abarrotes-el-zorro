package unam.fes.aragon.tienda_el_zorro.application.service;

import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.FacturaDTO;

import java.util.List;

public interface FacturaService {
    FacturaDTO createFactura(FacturaDTO facturaDTO);

    List<FacturaDTO> findAllByClient(ClienteDTO clienteDTO);

    List<FacturaDTO> findAllByDay(FacturaDTO facturaDTO);
}