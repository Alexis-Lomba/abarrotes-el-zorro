package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.dto.DetalleFacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.DetalleFactura;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Producto;

@AllArgsConstructor
@Service
@Primary  // Mark as primary to override any existing implementations
public class DetalleFacturaMapper {

    // No dependency on ProductoMapper to avoid potential circular references

    public DetalleFacturaDTO toDto(DetalleFactura detalle) {
        if (detalle == null) return null;

        DetalleFacturaDTO dto = new DetalleFacturaDTO();
        dto.setId(detalle.getId());
        dto.setCantidad(detalle.getCantidad());

        if (detalle.getPrecioUnitario() != null) {
            dto.setPrecioUnitario(detalle.getPrecioUnitario().doubleValue());
        }

        // Only set the IDs, not the full objects
        if (detalle.getFactura() != null) {
            dto.setFacturaId(detalle.getFactura().getId());
        }

        if (detalle.getProducto() != null) {
            dto.setProductoId(detalle.getProducto().getId());
        }

        return dto;
    }

    public DetalleFactura toEntity(DetalleFacturaDTO dto) {
        if (dto == null) return null;

        DetalleFactura detalle = new DetalleFactura();
        detalle.setId(dto.getId());
        detalle.setCantidad(dto.getCantidad());

        if (dto.getPrecioUnitario() != null) {
            detalle.setPrecioUnitario(dto.getPrecioUnitario().floatValue());
        }

        // Only set the product ID, not the full Factura object
        if (dto.getProductoId() != null) {
            Producto producto = new Producto();
            producto.setId(dto.getProductoId());
            detalle.setProducto(producto);
        }

        // Do NOT set the Factura here - it will be set by the FacturaMapper

        return detalle;
    }
}