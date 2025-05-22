package unam.fes.aragon.tienda_el_zorro.application.service;

import unam.fes.aragon.tienda_el_zorro.domain.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {

    List<UsuarioDTO> findAll();

    UsuarioDTO createUsuario(UsuarioDTO usuarioDTO);

    void deleteUsuarioById(Long id);

    UsuarioDTO findById(Long id);

    UsuarioDTO login(String username, String password);

    UsuarioDTO updateUsuario(UsuarioDTO usuarioDTO, Long id);

    UsuarioDTO findByUsername(String nombre);

}
