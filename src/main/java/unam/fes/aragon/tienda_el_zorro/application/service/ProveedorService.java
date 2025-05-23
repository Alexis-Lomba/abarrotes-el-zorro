package unam.fes.aragon.tienda_el_zorro.application.service;

import unam.fes.aragon.tienda_el_zorro.domain.dto.ProveedorDTO;

import java.util.List;

public interface ProveedorService {
    List<ProveedorDTO> findAll();
    
    ProveedorDTO createProveedor(ProveedorDTO proveedorDTO);
} 