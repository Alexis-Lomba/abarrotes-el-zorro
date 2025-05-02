package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.ProveedorService;
import unam.fes.aragon.tienda_el_zorro.domain.constants.BussinessConstants;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProveedorDTO;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.ProveedorMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ProveedorRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.ProveedorValidator;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ProveedorMapper proveedorMapper;
    private final ProveedorValidator proveedorValidator;

    @Override
    public List<ProveedorDTO> findAll() {
        return proveedorRepository.findAll().stream()
                .map(proveedorMapper::toDto)
                .toList();
    }

    @Override
    public ProveedorDTO createProveedor(ProveedorDTO proveedorDTO) {
        log.info("Creating new proveedor");
        proveedorValidator.validate(proveedorDTO.getNombre());

        proveedorRepository.save(proveedorMapper.toEntity(proveedorDTO));

        proveedorDTO.setStatus(BussinessConstants.CREADO_CORRECTAMENTE);
        return proveedorDTO;
    }
} 