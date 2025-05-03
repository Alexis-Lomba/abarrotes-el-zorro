package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass;

import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProveedorDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Proveedor;

@Service
public class ProveedorMapper {

    public ProveedorDTO toDto(Proveedor proveedor) {
        return ProveedorDTO.builder()
                .id(proveedor.getId())
                .nombre(proveedor.getNombre())
                .correo(proveedor.getCorreo())
                .build();
    }

    public Proveedor toEntity(ProveedorDTO proveedorDTO) {
        return Proveedor.builder()
                .id(proveedorDTO.getId())
                .nombre(proveedorDTO.getNombre())
                .correo(proveedorDTO.getCorreo())
                .build();
    }
}
