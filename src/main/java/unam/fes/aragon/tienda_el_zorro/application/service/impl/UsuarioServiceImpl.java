package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.FindIdService;
import unam.fes.aragon.tienda_el_zorro.application.service.UsuarioService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.UsuarioDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Rol;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Usuario;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.RolMapper;
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
    private final ModelMapper modelMapper;
    private final RolMapper rolMapper;
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

        usuario.setRoles(roles);
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuarioDTO.getPassword()));

        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }

    @Override
    public void deleteUsuarioById(Long id) {
        findIdService.findIdUsuario(id);
        usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioDTO findById(Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            return usuarioMapper.toDTO(optionalUsuario.get());
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
    }

    @Override
    public UsuarioDTO login(String username, String password) {
        Optional<Usuario> optional = usuarioRepository.findByUsernameAndPassword(username, password);
        return optional.map(usuario -> modelMapper.map(usuario, UsuarioDTO.class)).orElse(null);
    }

    @Override
    public UsuarioDTO updateUsuario(UsuarioDTO usuarioDTO, Long id) {
        Usuario usuario = findIdService.findIdUsuario(id);

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setUsername(usuarioDTO.getUsername());

        if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isBlank()) {
            usuario.setPassword(usuarioDTO.getPassword());
        }

        if (usuarioDTO.getRoles() != null) {
            List<Rol> roles = usuarioDTO.getRoles().stream()
                    .map(rolMapper::toEntity)
                    .collect(Collectors.toList());
            usuario.setRoles(roles);
        }

        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }

}