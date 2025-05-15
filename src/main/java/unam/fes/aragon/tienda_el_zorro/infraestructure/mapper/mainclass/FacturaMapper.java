package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.dto.DetalleFacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.FacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Cliente;
import unam.fes.aragon.tienda_el_zorro.domain.entity.DetalleFactura;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Factura;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Primary  // Mark as primary to override any existing implementations
public class FacturaMapper {

    private final DetalleFacturaMapper detalleFacturaMapper;

    // Convertir Entidad a DTO - Safe version that prevents infinite recursion
    public FacturaDTO toDto(Factura factura) {
        if (factura == null) return null;

        FacturaDTO dto = new FacturaDTO();
        dto.setId(factura.getId());
        dto.setFecha(factura.getFecha());
        dto.setTotal(factura.getTotal());
        dto.setClienteId(factura.getCliente().getId());
        dto.setUsuarioId(factura.getUsuario().getId());

        // convierte cada detalle directamente
        List<DetalleFacturaDTO> detallesDto = factura.getDetalles().stream()
                .map(detalleFacturaMapper::toDto)
                .collect(Collectors.toList());
        dto.setDetalles(detallesDto);

        return dto;
    }


    // Convertir DTO a Entidad - Safe version that prevents infinite recursion
    public Factura toEntity(FacturaDTO dto) {
        if (dto == null) return null;

        // Create basic entity without details first
        Factura factura = new Factura();
        factura.setId(dto.getId());
        factura.setFecha(dto.getFecha());
        factura.setTotal(dto.getTotal());
        factura.setActiva(true); // Valor por defecto

        // Set simple references
        if (dto.getClienteId() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(dto.getClienteId());
            factura.setCliente(cliente);
        }

        if (dto.getUsuarioId() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(dto.getUsuarioId());
            factura.setUsuario(usuario);
        }

        // Now handle details
        if (dto.getDetalles() != null && !dto.getDetalles().isEmpty()) {
            List<DetalleFactura> detalles = new ArrayList<>();

            for (DetalleFacturaDTO detalleDto : dto.getDetalles()) {
                DetalleFactura detalle = detalleFacturaMapper.toEntity(detalleDto);
                detalle.setFactura(factura);
                detalles.add(detalle);
            }

            factura.setDetalles(detalles);
        } else {
            factura.setDetalles(Collections.emptyList());
        }

        return factura;
    }
}