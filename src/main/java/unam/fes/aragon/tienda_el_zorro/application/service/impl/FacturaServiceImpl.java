package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.FacturaService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.FacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Cliente;
import unam.fes.aragon.tienda_el_zorro.domain.entity.DetalleFactura;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Factura;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Producto;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Proveedor;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.FacturaMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.FacturaRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ProveedorRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.ClienteValidation;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.ProductoValidation;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.ProveedorValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FacturaServiceImpl implements FacturaService {

    private final ProductoValidation productoValidation;
    private FacturaRepository facturaRepository;
    private FacturaMapper facturaMapper;
    private ClienteValidation clienteValidation;
    private ProveedorValidator proveedorValidator;
    private ProveedorRepository proveedorRepository;

    @Override
    public FacturaDTO createInvoice(FacturaDTO facturaDTO) {
        Factura factura =  new Factura();

        Cliente cliente = clienteValidation.validateCliente(facturaDTO.getCliente());

        List<DetalleFactura> detalles = facturaDTO.getProductos().stream()
                .map(detalleFacturaDTO -> {
                    // Validar existencia del proveedor usando su ID desde el DTO
                    Proveedor proveedor = proveedorValidator.validateExistence(
                            detalleFacturaDTO.getProducto().getProveedor()
                    );

                    // Validar existencia del producto usando su ID y el ID del proveedor
                    Producto producto = productoValidation.validateExistence(
                            detalleFacturaDTO.getProducto(), // ID del producto DTO
                            proveedor.getId(),
                            detalleFacturaDTO.getCantidad()
                    );

                    // Construir el detalle de la factura
                    DetalleFactura detalle = new DetalleFactura();
                    detalle.setProducto(producto);
                    detalle.setCantidad(detalleFacturaDTO.getCantidad());
                    detalle.setPrecioUnitario(producto.getPrecio());

                    return detalle;
                }).collect(Collectors.toList()); // Versiones Java <16: Usar Collectors.toList()

        factura.setCliente(cliente);
        factura.setDetalles(detalles);



        return null;
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