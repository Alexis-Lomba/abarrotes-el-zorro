package unam.fes.aragon.tienda_el_zorro.application.service;

import org.hibernate.procedure.ProcedureOutputs;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProveedorDTO;

import java.awt.image.Kernel;
import java.util.List;

public interface ProveedorService {
    List<ProveedorDTO> findAll();
    
    ProveedorDTO createProveedor(ProveedorDTO proveedorDTO);

    void deleteProveedor(Long id);

    ProveedorDTO updateProveedor(Long id, ProveedorDTO proveedorDTO);
} 