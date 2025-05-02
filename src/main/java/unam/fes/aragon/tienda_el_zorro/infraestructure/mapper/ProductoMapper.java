package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProductoDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Producto;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

    @Mapping(target = "proveedorId", source = "proveedor.id")
    @Mapping(target = "detallesIds", expression = "java(producto.getDetalles().stream().map(detalle -> detalle.getId()).toList())")
    @Mapping(target = "inventarioId", source = "inventario.id")
    ProductoDTO toDto(Producto producto);

    @Mapping(target = "proveedor", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    @Mapping(target = "inventario", ignore = true)
    Producto toEntity(ProductoDTO productoDTO);
} 