package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.FacturaService;
import unam.fes.aragon.tienda_el_zorro.application.service.FindIdService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.FacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.*;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.FacturaMapper;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class FacturaServiceImpl implements FacturaService {

    private final ProductoValidation productoValidation;
    private final FacturaRepository facturaRepository;
    private final FacturaMapper facturaMapper;
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
    public FacturaDTO createFactura(FacturaDTO facturaDTO) {
        Factura factura =  new Factura();

        Cliente cliente = clienteValidation.validateCliente(clientMapper.toDTO(findIdService.findIdCliente(facturaDTO.getClienteId())));

        List<DetalleFactura> detalles = facturaDTO.getProductos().stream()
                .map(detalleFacturaDTO -> {
                    // Validar existencia del proveedor usando su ID desde el DTO
                    Proveedor proveedor = proveedorValidator.validateExistence(
                          proveedorMapper.toDto(findIdService.findIdProveedor(
                                  detalleFacturaDTO.getProductoDTO().getProveedorId()
                    )));

                    // Validar existencia del producto usando su ID y el ID del proveedor
                    Producto producto = productoValidation.validateExistence(
                            detalleFacturaDTO.getProductoDTO(),
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
        detalles.forEach(detalle -> detalle.setFactura(factura));
        factura.setFecha(facturaDTO.getFecha());

        /*factura.setVenta(ventaRepository.findById(
                facturaDTO.getVentaId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No hay una venta con el id: " + facturaDTO.getVentaId())));

         */

        Usuario usuario = usuarioValidator.validateUsuario(usuarioMapper.toDto(findIdService.findIdUsuario(facturaDTO.getUsuarioId())));

        factura.setUsuario(usuario);

        log.info("Validando el total de la factura");
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            log.info("Lo que se tiene de la factura es: {}", objectMapper.writeValueAsString(factura));
        }catch (Exception e){
            e.printStackTrace();
        }


        float total = detalles.stream()
                .map(d -> d.getPrecioUnitario() * d.getCantidad())
                .reduce(0.0f, Float::sum);

        log.info("Lo que se tiene de la total es: {}", total);

        factura.setTotal(total);

        facturaRepository.save(factura);

        return facturaMapper.toDto(factura);
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