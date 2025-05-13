package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.dto.DetalleFacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.DetalleFactura;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Producto;

@AllArgsConstructor
@Service
public class DetalleFacturaMapper {

    private final ProductoMapper productoMapper;
    public DetalleFacturaDTO toDto(DetalleFactura detalle) {
        if (detalle == null) return null;

        return DetalleFacturaDTO.builder()
                .id(detalle.getId())
                .cantidad(detalle.getCantidad())
                .precioUnitario(detalle.getPrecioUnitario() != null
                        ? detalle.getPrecioUnitario().doubleValue()
                        : null)
                .facturaId(detalle.getFactura() != null
                        ? detalle.getFactura().getId()
                        : null)
                .productoDTO(productoMapper.toDto(detalle.getProducto())) // Mapear solo el ID del producto
                .build();
    }

    public DetalleFactura toEntity(DetalleFacturaDTO dto) {
        if (dto == null) return null;

        DetalleFactura detalle = new DetalleFactura();
        detalle.setId(dto.getId());
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario() != null
                ? dto.getPrecioUnitario().floatValue()
                : null);

        if (dto.getProductoDTO() != null) {
            Producto producto = new Producto();
            producto.setId(dto.getProductoDTO().getId());
            detalle.setProducto(producto);
        }

        return detalle;
    }
}

