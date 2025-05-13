package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.RolService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.RolDTO;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.RolMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.RolRepository;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class RolServiceImpl implements RolService {

    private RolRepository rolRepository;
    private RolMapper rolMapper;


    @Override
    public List<RolDTO> findAll() {
        return rolRepository.findAll().stream()
                .map(rolMapper :: toDTO)
                .toList();
    }

    @Override
    public RolDTO createRol(RolDTO rolDTO) {
        rolRepository.save(rolMapper.toEntity(rolDTO));
        return  rolDTO;
    }
}
