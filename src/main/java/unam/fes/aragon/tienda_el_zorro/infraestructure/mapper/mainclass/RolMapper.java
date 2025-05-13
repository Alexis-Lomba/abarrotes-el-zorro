package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass;

import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.dto.RolDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Rol;

@Service
public class RolMapper {
    public Rol toEntity(RolDTO rolDTO) {
        if (rolDTO == null) {
            return null;
        }
        return Rol.builder()
                .id(rolDTO.getId())
                .nombre(rolDTO.getNombre())
                .build();
    }
    public RolDTO toDTO(Rol rol) {
        if (rol == null) {
            return null;
        }
        return RolDTO.builder()
                .id(rol.getId())
                .nombre(rol.getNombre())
                .build();
    }
}
