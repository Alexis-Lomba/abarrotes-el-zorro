package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unam.fes.aragon.tienda_el_zorro.domain.dto.UsuarioDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(target = "ventasIds", expression = "java(usuario.getVentas().stream().map(venta -> venta.getId()).toList())")
    UsuarioDTO toDto(Usuario usuario);

    @Mapping(target = "ventas", ignore = true)
    Usuario toEntity(UsuarioDTO usuarioDTO);
} 