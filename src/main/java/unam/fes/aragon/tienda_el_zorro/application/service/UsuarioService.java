package unam.fes.aragon.tienda_el_zorro.application.service;

import unam.fes.aragon.tienda_el_zorro.domain.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {

    List<UsuarioDTO> findAll();
    
    UsuarioDTO createUsuario(UsuarioDTO usuarioDTO);

    void deleteUsuarioById(Long id);

    UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO);

}
