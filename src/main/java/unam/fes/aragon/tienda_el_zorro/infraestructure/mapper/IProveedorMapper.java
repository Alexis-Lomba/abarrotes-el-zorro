package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProveedorDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Proveedor;

@Mapper(componentModel = "spring")
public interface IProveedorMapper {
    IProveedorMapper INSTANCE = Mappers.getMapper(IProveedorMapper.class);
/*
    @Mapping(target = "productosIds", expression = "java(proveedor.getProductos().stream().map(producto -> producto.getId()).toList())")
    ProveedorDTO toDto(Proveedor proveedor);

    @Mapping(target = "productos", ignore = true)
    Proveedor toEntity(ProveedorDTO proveedorDTO);

 */
} 