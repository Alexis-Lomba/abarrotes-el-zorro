package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.ProveedorService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProveedorDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Proveedor;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.ProveedorMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ProveedorRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    private ProveedorRepository proveedorRepository;
    private ProveedorMapper proveedorMapper;

    @Override
    public List<ProveedorDTO> findAll() {
        return proveedorRepository.findAll().stream()
                .map(proveedorMapper::toDto)
                .toList();
    }

    @Override
    public ProveedorDTO createProveedor(ProveedorDTO proveedorDTO) {
        Proveedor proveedor = proveedorMapper.toEntity(proveedorDTO);
        proveedor = proveedorRepository.save(proveedor);
        return proveedorMapper.toDto(proveedor);
    }
} 