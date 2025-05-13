package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unam.fes.aragon.tienda_el_zorro.domain.dto.FacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Factura;

@Mapper(componentModel = "spring")
public interface FacturaMapper {

    FacturaDTO toDto(Factura factura);

    Factura toEntity(FacturaDTO facturaDTO);
} 