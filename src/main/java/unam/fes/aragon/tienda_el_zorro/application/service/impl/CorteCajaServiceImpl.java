package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.CorteCajaService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.CorteCajaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.DetalleFactura;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Factura;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.FacturaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CorteCajaServiceImpl implements CorteCajaService {

    private final FacturaRepository facturaRepository;
    @Override
    public List<CorteCajaDTO> obtenerCorteDeCajaPorFecha(LocalDate fecha) {
        List<Factura> facturas = facturaRepository.findByFecha(fecha);
        Map<String, Integer> conteoProductos = new HashMap<>();

        for (Factura factura : facturas) {
            for (DetalleFactura detalle : factura.getDetalles()) {
                String nombre = detalle.getProducto().getNombre();
                int cantidad = detalle.getCantidad();

                conteoProductos.put(nombre, conteoProductos.getOrDefault(nombre, 0) + cantidad);
            }
        }

        return conteoProductos.entrySet().stream()
                .map(entry -> new CorteCajaDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
