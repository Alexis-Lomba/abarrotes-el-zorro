package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(target = "facturasIds", expression = "java(cliente.getFacturas().stream().map(factura -> factura.getId()).toList())")
    ClienteDTO toDto(Cliente cliente);

    @Mapping(target = "facturas", ignore = true)
    Cliente toEntity(ClienteDTO clienteDTO);
} 