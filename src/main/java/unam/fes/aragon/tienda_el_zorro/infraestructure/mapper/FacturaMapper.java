package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unam.fes.aragon.tienda_el_zorro.domain.dto.FacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Factura;

@Mapper(componentModel = "spring")
public interface FacturaMapper {
    FacturaMapper INSTANCE = Mappers.getMapper(FacturaMapper.class);

    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "detallesIds", expression = "java(factura.getDetalles().stream().map(detalle -> detalle.getId()).toList())")
    @Mapping(target = "ventaId", source = "venta.id")
    FacturaDTO toDto(Factura factura);

    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    @Mapping(target = "venta", ignore = true)
    Factura toEntity(FacturaDTO facturaDTO);
} 