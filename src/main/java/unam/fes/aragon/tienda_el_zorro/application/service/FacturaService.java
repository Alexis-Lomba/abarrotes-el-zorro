package unam.fes.aragon.tienda_el_zorro.application.service;

import unam.fes.aragon.tienda_el_zorro.domain.dto.FacturaDTO;

import java.util.List;

public interface FacturaService {
    List<FacturaDTO> findAll();
    
    FacturaDTO createFactura(FacturaDTO facturaDTO);
} 