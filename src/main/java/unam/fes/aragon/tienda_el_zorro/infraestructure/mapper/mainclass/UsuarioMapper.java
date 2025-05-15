package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass;

import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.dto.UsuarioDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Usuario;

import java.util.stream.Collectors;

@Service
public class UsuarioMapper {

    private final RolMapper rolMapper;

    public UsuarioMapper(RolMapper rolMapper) {
        this.rolMapper = rolMapper;
    }

    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;

        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRoles() != null
                        ? usuario.getRoles().stream()
                        .map(rolMapper::toDTO)
                        .collect(Collectors.toList())
                        : null)
                .build();
    }

    public Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;

        return Usuario.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .roles(dto.getRoles() != null
                        ? dto.getRoles().stream()
                        .map(rolMapper::toEntity)
                        .collect(Collectors.toList())
                        : null)
                .build();
    }
}

