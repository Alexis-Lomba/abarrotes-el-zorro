package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unam.fes.aragon.tienda_el_zorro.domain.dto.DetalleFacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.DetalleFactura;

@Mapper(componentModel = "spring")
public interface DetalleFacturaMapper {
    DetalleFacturaMapper INSTANCE = Mappers.getMapper(DetalleFacturaMapper.class);

    @Mapping(target = "facturaId", source = "factura.id")
    @Mapping(target = "productoId", source = "producto.id")
    DetalleFacturaDTO toDto(DetalleFactura detalleFactura);

    @Mapping(target = "factura", ignore = true)
    @Mapping(target = "producto", ignore = true)
    DetalleFactura toEntity(DetalleFacturaDTO detalleFacturaDTO);
} 