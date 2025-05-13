package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.dto.DetalleFacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.FacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Cliente;
import unam.fes.aragon.tienda_el_zorro.domain.entity.DetalleFactura;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Factura;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Usuario;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Venta;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class FacturaMapper {

    private final DetalleFacturaMapper detalleFacturaMapper;

    // Convertir Entidad a DTO
    public FacturaDTO toDto(Factura factura) {
        if (factura == null) return null;

        return FacturaDTO.builder()
                .id(factura.getId())
                .fecha(factura.getFecha())
                .total(factura.getTotal())
                .clienteId(factura.getCliente() != null ? factura.getCliente().getId() : null)
                .usuarioId(factura.getUsuario() != null ? factura.getUsuario().getId() : null)
                .ventaId(factura.getVenta() != null ? factura.getVenta().getId() : null)
                .productos(toDetalleFacturaDtoList(factura.getDetalles()))
                .build();
    }

    // Convertir DTO a Entidad
    public Factura toEntity(FacturaDTO dto) {
        if (dto == null) return null;

        Factura factura = new Factura();
        factura.setId(dto.getId());
        factura.setFecha(dto.getFecha());
        factura.setTotal(dto.getTotal());
        factura.setActiva(true); // Valor por defecto

        // Mapear Cliente (solo ID)
        if (dto.getClienteId() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(dto.getClienteId());
            factura.setCliente(cliente);
        }

        // Mapear Usuario (solo ID)
        if (dto.getUsuarioId() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(dto.getUsuarioId());
            factura.setUsuario(usuario);
        }

        // Mapear Venta (solo ID)
        if (dto.getVentaId() != null) {
            Venta venta = new Venta();
            venta.setId(dto.getVentaId());
            factura.setVenta(venta);
        }

        // Mapear Detalles y establecer relación bidireccional
        List<DetalleFactura> detalles = toDetalleFacturaEntityList(dto.getProductos());
        detalles.forEach(detalle -> detalle.setFactura(factura));
        factura.setDetalles(detalles);

        return factura;
    }

    // Métodos auxiliares para listas
    private List<DetalleFacturaDTO> toDetalleFacturaDtoList(List<DetalleFactura> detalles) {
        if (detalles == null) return Collections.emptyList();
        return detalles.stream()
                .map(detalleFacturaMapper::toDto)
                .collect(Collectors.toList());
    }

    private List<DetalleFactura> toDetalleFacturaEntityList(List<DetalleFacturaDTO> detallesDTO) {
        if (detallesDTO == null) return Collections.emptyList();
        return detallesDTO.stream()
                .map(detalleFacturaMapper::toEntity)
                .collect(Collectors.toList());
    }
}

