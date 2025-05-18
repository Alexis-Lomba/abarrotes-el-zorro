package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unam.fes.aragon.tienda_el_zorro.application.service.FindIdService;
import unam.fes.aragon.tienda_el_zorro.application.service.ProveedorService;
import unam.fes.aragon.tienda_el_zorro.domain.constants.BussinessConstants;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProveedorDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Proveedor;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.ProveedorMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ProveedorRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.ProveedorValidator;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<ProveedorDTO> findByName(String nombre ){
        List<Proveedor> proveedores = proveedorRepository.findByName(nombre);
        if (proveedores.isEmpty()) return (List<ProveedorDTO>) ResponseEntity.noContent().build();
        return proveedores.stream()
                .map(proveedorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProveedorDTO findById(Long id) {
        return proveedorMapper.toDto(findIdService.findIdProveedor(id));
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

    @Transactional
    public ProveedorDTO updateProveedor(Long id, ProveedorDTO dto) {
        Proveedor proveedor = findIdService.findIdProveedor(id);

        proveedor.setNombre(dto.getNombre());
        proveedor.setCorreo(dto.getCorreo());

        proveedor = proveedorRepository.save(proveedor);

        return proveedorMapper.toDto(proveedor);
    }

} 