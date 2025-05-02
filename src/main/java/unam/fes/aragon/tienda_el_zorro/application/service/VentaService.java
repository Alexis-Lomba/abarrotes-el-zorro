package unam.fes.aragon.tienda_el_zorro.application.service;

import unam.fes.aragon.tienda_el_zorro.domain.dto.VentaDTO;

import java.util.List;

public interface VentaService {
    List<VentaDTO> findAll();
    
    VentaDTO createVenta(VentaDTO ventaDTO);
} 