package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.jdbc.core.metadata.DerbyTableMetaDataProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unam.fes.aragon.tienda_el_zorro.application.service.FacturaService;
import unam.fes.aragon.tienda_el_zorro.application.service.FindIdService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.DetalleFacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.FacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.*;
import unam.fes.aragon.tienda_el_zorro.domain.error.DinError;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.UsuarioMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.*;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.*;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.ClienteValidation;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.ProductoValidation;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.ProveedorValidator;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.UsuarioValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class FacturaServiceImpl implements FacturaService {

    private final ProductoValidation productoValidation;
    private final FacturaRepository facturaRepository;
    private final ClienteValidation clienteValidation;
    private final ClientMapper clientMapper;
    private final ProveedorValidator proveedorValidator;
    private final ProveedorRepository proveedorRepository;
    private final ProveedorMapper proveedorMapper;
    private final ProductoMapper productoMapper;
    private final UsuarioValidator usuarioValidator;
    private final UsuarioMapper usuarioMapper;
    private final VentaRepository ventaRepository;
    private final FindIdService findIdService;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final InventarioRepository inventarioRepository;
    private final FacturaMapper facturaMapper;
    private final DetalleFacturaMapper detalleFacturaMapper;

    @Override
    @Transactional
    public FacturaDTO createFactura(FacturaDTO facturaDTO) {
        Factura factura = facturaMapper.toEntity(facturaDTO);

        log.info("Actualizando inventario...");

        List<DetalleFactura> detalles = new ArrayList<>();

        for (DetalleFacturaDTO detalleDTO : facturaDTO.getDetalles()) {
            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalleDTO.getProductoId()));

            if (producto.getInventario().getCantidadActual() < detalleDTO.getCantidad()) {
                throw new RuntimeException("No hay stock suficiente para el producto ID: " + producto.getId());
            }

            // Actualiza inventario
            Inventario inventario = producto.getInventario();
            inventario.setCantidadActual(inventario.getCantidadActual() - detalleDTO.getCantidad());
            inventarioRepository.save(inventario);

            // Construye el detalle de factura
            DetalleFactura detalle = new DetalleFactura();
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setFactura(factura);

            detalles.add(detalle);
        }
        factura.setFecha(LocalDateTime.now());
        factura.setDetalles(detalles);

        factura.setTotal(total(facturaDTO));

        // Guarda la factura
        factura = facturaRepository.save(factura);

        return facturaMapper.toDto(factura);
    }



    @Override
    public List<FacturaDTO> findAllByClientNombre(String nombre) {
        List<Factura> facturas = facturaRepository.findByNombreClienteIgnoreCase(nombre);
        return facturas.stream()
                .map(facturaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FacturaDTO> findAllByDay(FacturaDTO facturaDTO) {
        return List.of();
    }

    private Float total(FacturaDTO facturaDTO) {
        Float total = 0f;
        for (DetalleFacturaDTO detalle : facturaDTO.getDetalles()) {
            Producto producto = productoRepository.findById(detalle.getProductoId()).orElseThrow();
            total = producto.getPrecio() * detalle.getCantidad();
        }
        return total;
    }

}