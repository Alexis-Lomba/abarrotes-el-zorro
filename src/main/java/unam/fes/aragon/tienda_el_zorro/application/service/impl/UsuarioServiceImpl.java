package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.UsuarioService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.UsuarioDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Rol;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Usuario;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.UsuarioMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.RolRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository usuarioRepository;
    private UsuarioMapper usuarioMapper;
    private RolRepository rolRepository;

    @Override
    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDto)
                .toList();
    }

    @Override
    public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);

        // Obtener roles desde la base de datos usando los IDs del DTO
        List<Rol> roles = usuarioDTO.getRoles().stream()
                .map(rolDTO -> rolRepository.findById(rolDTO.getId())) // Retorna Optional<Rol>
                .filter(Optional::isPresent) // Filtra los Optional que tienen contenido
                .map(Optional::get) // Obtiene el objeto Rol del Optional
                .collect(Collectors.toList());

        usuario.setRoles(roles); // Asignar roles recuperados

        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(usuario);
    }
}
