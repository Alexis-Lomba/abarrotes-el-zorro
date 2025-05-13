package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.FindIdService;
import unam.fes.aragon.tienda_el_zorro.domain.entity.*;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.*;

@Service
@AllArgsConstructor
public class FindIdServiceServiceImpl implements FindIdService {

    private final ClienteRepository clienteRepository;
    private final DetalleFacturaRepository detalleFacturaRepository;
    private final FacturaRepository facturaRepository;
    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final VentaRepository ventaRepository;


    @Override
    public Cliente findIdCliente(Long id) {
        return clienteRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("No se encontro el cliente"));
    }

    @Override
    public DetalleFactura findIdDetalle(Long id) {
        return detalleFacturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el detalle de factura"));
    }

    @Override
    public Factura findIdFactura(Long id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la factura"));
    }

    @Override
    public Inventario findIdInventario(Long id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el inventario"));
    }

    @Override
    public Producto findIdProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el producto"));
    }

    @Override
    public Proveedor findIdProveedor(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el proveedor"));
    }

    @Override
    public Rol findIdRol(Long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el rol"));
    }

    @Override
    public Usuario findIdUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario"));
    }

    @Override
    public Venta findIdVenta(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la venta"));
    }

}
