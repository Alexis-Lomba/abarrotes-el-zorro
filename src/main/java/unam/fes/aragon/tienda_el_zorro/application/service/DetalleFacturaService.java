package unam.fes.aragon.tienda_el_zorro.application.service;

import unam.fes.aragon.tienda_el_zorro.domain.dto.DetalleFacturaDTO;

import java.util.List;

public interface DetalleFacturaService {
    List<DetalleFacturaDTO> findAll();
    
    DetalleFacturaDTO createDetalleFactura(DetalleFacturaDTO detalleFacturaDTO);
} 