package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.InventarioService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.InventarioDTO;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.InventarioMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.InventarioRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class InventarioServiceImpl implements InventarioService {

    private InventarioRepository inventarioRepository;
    private InventarioMapper inventarioMapper;

    @Override
    public List<InventarioDTO> findAll() {
        return  null; /*inventarioRepository.findAll().stream()
                .map(inventarioMapper::toDto)
                .toList();*/
    }

    @Override
    public InventarioDTO createInventario(InventarioDTO inventarioDTO) {
        //Inventario inventario = inventarioMapper.toEntity(inventarioDTO);
        //inventario = inventarioRepository.save(inventario);
        return null; //inventarioMapper.toDto(inventario);
    }
} 