package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.FindIdService;
import unam.fes.aragon.tienda_el_zorro.application.service.ProveedorService;
import unam.fes.aragon.tienda_el_zorro.domain.constants.BussinessConstants;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProveedorDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Proveedor;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.ProveedorMapper;
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
    private final FindIdService findIdService;

    @Override
    public List<ProveedorDTO> findAll() {
        return proveedorRepository.findAll().stream()
                .map(proveedorMapper::toDto)
                .toList();
    }

    @Override
    public ProveedorDTO createProveedor(ProveedorDTO proveedorDTO) {
        log.info("Creating new proveedor");
        Proveedor proveedor = proveedorMapper.toEntity(proveedorDTO);

        proveedorValidator.validate(proveedor);
        proveedorRepository.save(proveedor);

        proveedorDTO.setStatus(BussinessConstants.CREADO_CORRECTAMENTE);
        return proveedorDTO;
    }

    @Override
    public void deleteProveedor(Long id) {
        findIdService.findIdProveedor(id);
        proveedorRepository.deleteById(id);
    }
} 