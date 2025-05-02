package unam.fes.aragon.tienda_el_zorro.application.service;

import unam.fes.aragon.tienda_el_zorro.domain.dto.InventarioDTO;

import java.util.List;

public interface InventarioService {
    List<InventarioDTO> findAll();
    
    InventarioDTO createInventario(InventarioDTO inventarioDTO);
} 