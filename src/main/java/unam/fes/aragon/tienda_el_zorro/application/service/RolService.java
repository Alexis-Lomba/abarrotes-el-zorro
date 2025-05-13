package unam.fes.aragon.tienda_el_zorro.application.service;

import unam.fes.aragon.tienda_el_zorro.domain.dto.RolDTO;

import java.util.List;

public interface RolService {

    List<RolDTO> findAll();

    RolDTO createRol(RolDTO rolDTO);
}
