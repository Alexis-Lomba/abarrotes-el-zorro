package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
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
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.ClientMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.FacturaMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.ProductoMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.ProveedorMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.*;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.ClienteValidation;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.ProductoValidation;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.ProveedorValidator;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.UsuarioValidator;

import java.time.LocalDateTime;
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
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final InventarioRepository inventarioRepository;
    private final FacturaMapper facturaMapper;

    @Override
    @Transactional
    public FacturaDTO createFactura(FacturaDTO facturaDTO) {
        Factura factura = facturaMapper.toEntity(facturaDTO);

        //factura.setCliente(clienteRepository.findById(facturaDTO.getUsuarioId()).orElseThrow());
        //factura.setCliente(clienteRepository.findById(facturaDTO.getUsuarioId()).orElseThrow());

        log.info(facturaDTO.getDetalles().toString());

        /**
         * Falta corregir el mapper de la factura
         */

        // Guarda la factura con los detalles

        log.info("Actualizacion del inventario");

        // (Opcional) Actualizar inventario
        for (DetalleFacturaDTO detalle : facturaDTO.getDetalles()) {
            Producto producto = productoRepository.findById(detalle.getProductoId()).orElseThrow();
            if (producto.getInventario().getCantidadActual()< detalle.getCantidad()) throw new RuntimeException("No hay stok");
            Inventario inventario = producto.getInventario();
            inventario.setCantidadActual(inventario.getCantidadActual() - detalle.getCantidad());
            inventarioRepository.save(inventario);
        }

        factura.setTotal(total(facturaDTO));

        factura = facturaRepository.save(factura);

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

    private Float total(FacturaDTO facturaDTO) {
        Float total = 0f;
        for (DetalleFacturaDTO detalle : facturaDTO.getDetalles()) {
            Producto producto = productoRepository.findById(detalle.getProductoId()).orElseThrow();
            total = producto.getPrecio() * detalle.getCantidad();
        }
        return total;
    }

}