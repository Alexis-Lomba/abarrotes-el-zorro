package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unam.fes.aragon.tienda_el_zorro.domain.dto.InventarioDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Inventario;

@Mapper(componentModel = "spring")
public interface IInventarioMapper {
    IInventarioMapper INSTANCE = Mappers.getMapper(IInventarioMapper.class);
    /*
    @Mapping(target = "productoId", source = "producto.id")
    InventarioDTO toDto(Inventario inventario);

    @Mapping(target = "producto", ignore = true)
    Inventario toEntity(InventarioDTO inventarioDTO);
     */
} 