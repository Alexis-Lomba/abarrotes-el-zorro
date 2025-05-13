package unam.fes.aragon.tienda_el_zorro.infraestructure.validations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.dto.UsuarioDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Usuario;
import unam.fes.aragon.tienda_el_zorro.domain.error.DinError;
import unam.fes.aragon.tienda_el_zorro.domain.error.ErrorNegocio;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.UsuarioMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.UsuarioRepository;

import java.security.DigestException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public Usuario validateUsuario(UsuarioDTO usuarioDTO){
        /**
         * Cambiar que sea solo por usuario y no por nombre o por ambos
         */
        log.info("Validando usuario en creacion factura {}", usuarioDTO);
        Usuario usuario = usuarioRepository.findByUsername(usuarioDTO.getUsername());

        if (usuario == null) {
            throw new DinError(ErrorNegocio.builder()
                    .mensaje("No existe el usuario para asignar venta")
                    .build());
        }
        return usuario;
    }
}
