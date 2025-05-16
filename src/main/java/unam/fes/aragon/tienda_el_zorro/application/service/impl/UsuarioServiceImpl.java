package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unam.fes.aragon.tienda_el_zorro.application.service.FindIdService;
import unam.fes.aragon.tienda_el_zorro.application.service.UsuarioService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.UsuarioDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Rol;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Usuario;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.UsuarioMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.RolRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final RolRepository rolRepository;
    private final FindIdService findIdService;

    @Override
    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDTO)
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
        return usuarioMapper.toDTO(usuario);
    }

    @Override
    public void deleteUsuarioById(Long id) {
        findIdService.findIdUsuario(id);
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public UsuarioDTO updateUsuario(Long id, UsuarioDTO dto) {
        Usuario usuario =findIdService.findIdUsuario(id);

        usuario.setNombre(dto.getNombre());
        usuario.setUsername(dto.getUsername());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuario.setPassword(dto.getPassword());
        }

        if (dto.getRoles() != null) {
            List<Rol> roles = dto.getRoles().stream()
                    .map(rolDTO -> {
                        Rol rol = new Rol();
                        rol.setId(rolDTO.getId());
                        rol.setNombre(rolDTO.getNombre());
                        return rol;
                    }).collect(Collectors.toList());
            usuario.setRoles(roles);
        }

        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }

}
