package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unam.fes.aragon.tienda_el_zorro.application.service.FacturaService;
import unam.fes.aragon.tienda_el_zorro.application.service.FindIdService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.DetalleFacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.FacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.*;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.UsuarioMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.ClientMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.ProductoMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.ProveedorMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.FacturaRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ProveedorRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.VentaRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.ClienteValidation;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.ProductoValidation;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.ProveedorValidator;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.UsuarioValidator;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    @Transactional
    public FacturaDTO createFactura(FacturaDTO facturaDTO) {
        log.info("Starting creation of new invoice");

        try {
            // Create the base Factura entity
            Factura factura = new Factura();

            // Get and validate client
            Cliente cliente = clienteValidation.validateCliente(
                    clientMapper.toDTO(findIdService.findIdCliente(facturaDTO.getClienteId()))
            );
            factura.setCliente(cliente);

            // Set invoice date
            factura.setFecha(facturaDTO.getFecha());

            // Get and validate user
            Usuario usuario = usuarioValidator.validateUsuario(
                    usuarioMapper.toDto(findIdService.findIdUsuario(facturaDTO.getUsuarioId()))
            );
            factura.setUsuario(usuario);

            // Initialize details list
            List<DetalleFactura> detalles = new ArrayList<>();

            // Temporary total calculation
            float total = 0.0f;

            // Process all products
            if (facturaDTO.getProductos() != null) {
                for (DetalleFacturaDTO detalleDTO : facturaDTO.getProductos()) {
                    // Find the product
                    Producto producto = findIdService.findIdProducto(detalleDTO.getProductoId());
                    log.debug("Processing product: {}", producto.getNombre());

                    // Get and validate provider
                    Proveedor proveedor = proveedorValidator.validateExistence(
                            proveedorMapper.toDto(findIdService.findIdProveedor(producto.getProveedor().getId()))
                    );

                    // Validate product (checks stock, etc.)
                    producto = productoValidation.validateExistence(
                            productoMapper.toDto(producto),
                            proveedor.getId(),
                            detalleDTO.getCantidad()
                    );

                    // Create detail entity
                    DetalleFactura detalle = new DetalleFactura();
                    detalle.setProducto(producto);
                    detalle.setCantidad(detalleDTO.getCantidad());
                    detalle.setPrecioUnitario(producto.getPrecio());

                    // Add to total
                    total += (producto.getPrecio() * detalleDTO.getCantidad());

                    // Add to list (don't set factura reference yet)
                    detalles.add(detalle);
                }
            }

            // Set total
            log.info("Invoice total: {}", total);
            factura.setTotal(total);
            factura.setActiva(true);

            // Save the factura first to get an ID
            Factura savedFactura = facturaRepository.save(factura);
            log.info("Created invoice with ID: {}", savedFactura.getId());

            // Now create the bidirectional relationship and save details
            for (DetalleFactura detalle : detalles) {
                detalle.setFactura(savedFactura);
            }
            savedFactura.setDetalles(detalles);

            // Save again with details
            savedFactura = facturaRepository.save(savedFactura);

            // Create response DTO manually to avoid mapper issues
            FacturaDTO responseDTO = new FacturaDTO();
            responseDTO.setId(savedFactura.getId());
            responseDTO.setFecha(savedFactura.getFecha());
            responseDTO.setTotal(savedFactura.getTotal());
            responseDTO.setClienteId(savedFactura.getCliente().getId());
            responseDTO.setUsuarioId(savedFactura.getUsuario().getId());

            List<DetalleFacturaDTO> detallesDTO = new ArrayList<>();
            for (DetalleFactura detalle : savedFactura.getDetalles()) {
                DetalleFacturaDTO detalleDTO = new DetalleFacturaDTO();
                detalleDTO.setId(detalle.getId());
                detalleDTO.setCantidad(detalle.getCantidad());
                detalleDTO.setPrecioUnitario(detalle.getPrecioUnitario().doubleValue());
                detalleDTO.setProductoId(detalle.getProducto().getId());
                detalleDTO.setFacturaId(savedFactura.getId());
                detallesDTO.add(detalleDTO);
            }
            responseDTO.setProductos(detallesDTO);

            return responseDTO;

        } catch (Exception e) {
            log.error("Error creating invoice: ", e);
            throw e;
        }
    }

    @Override
    public List<FacturaDTO> findAllByClient(ClienteDTO clienteDTO) {
        return List.of();
    }

    @Override
    public List<FacturaDTO> findAllByDay(FacturaDTO facturaDTO) {
        return List.of();
    }
}