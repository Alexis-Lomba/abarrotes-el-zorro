package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.UsuarioService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.UsuarioDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Usuario;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.UsuarioMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.UsuarioRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository usuarioRepository;
    private UsuarioMapper usuarioMapper;

    @Override
    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDto)
                .toList();
    }

    @Override
    public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(usuario);
    }
}
