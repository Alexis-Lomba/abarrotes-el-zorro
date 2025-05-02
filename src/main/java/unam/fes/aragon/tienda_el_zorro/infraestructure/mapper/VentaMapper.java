package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unam.fes.aragon.tienda_el_zorro.domain.dto.VentaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Venta;

@Mapper(componentModel = "spring")
public interface VentaMapper {
    VentaMapper INSTANCE = Mappers.getMapper(VentaMapper.class);

    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "facturaId", source = "factura.id")
    VentaDTO toDto(Venta venta);

    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "factura", ignore = true)
    Venta toEntity(VentaDTO ventaDTO);
} 