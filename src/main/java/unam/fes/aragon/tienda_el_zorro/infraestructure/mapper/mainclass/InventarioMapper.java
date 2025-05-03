package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass;

import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.dto.InventarioDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Inventario;

@Service
public class InventarioMapper {

    public InventarioDTO toDto(Inventario inventario) {
        return InventarioDTO.builder()
                .cantidadActual(inventario.getCantidadActual())
                .cantidadInicial(inventario.getCantidadInicial())
                .minimoRequerido(inventario.getMinimoRequerido())
                .build();
    }

    public Inventario toEntity(InventarioDTO inventarioDTO) {
        return Inventario.builder()
                .cantidadActual(inventarioDTO.getCantidadActual())
                .cantidadInicial(inventarioDTO.getCantidadInicial())
                .minimoRequerido(inventarioDTO.getMinimoRequerido())
                .build();
    }
}
