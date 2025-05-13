package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.FindIdService;
import unam.fes.aragon.tienda_el_zorro.application.service.ProductoService;
import unam.fes.aragon.tienda_el_zorro.application.service.ProveedorService;
import unam.fes.aragon.tienda_el_zorro.domain.constants.BussinessConstants;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProductoDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProveedorDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Inventario;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Producto;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Proveedor;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.IProductoMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.IProveedorMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.InventarioMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.ProductoMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.ProveedorMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.InventarioRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ProductoRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ProveedorRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.ProductoValidation;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final InventarioRepository inventarioRepository;
    private ProductoRepository productoRepository;
    private final ProveedorMapper proveedorMapper;
    private final ProveedorService proveedorService;
    private final ProductoMapper productoMapper;
    private IProductoMapper productoMapperInterface;
    private ProductoValidation productoValidation;
    private InventarioMapper inventarioMapper;

    private ProveedorRepository proveedorRepository;

    private final FindIdService findIdService;

    @Override
    public List<ProductoDTO> findAll() {
        log.info("Find all productos");
        return productoRepository.findAllProductos().stream()
                .map(productoMapper :: toDto)
                .toList();
    }

    @Override
    public ProductoDTO createProducto(ProductoDTO productoDTO) {
        log.info("Se busca proveedor para el producto");
        Proveedor proveedor = proveedorRepository.findByName(findIdService.findIdProveedor(productoDTO.getProveedorId()).getNombre());
        log.info("REspuesta de Proveedor {}", proveedor);
        ProveedorDTO proveedorDTO;

        if (proveedor == null) {
            log.info("Se crea proveedor");
            proveedorDTO =  proveedorService.createProveedor(proveedorMapper.toDto(findIdService.findIdProveedor(productoDTO.getProveedorId())));
            proveedor = proveedorRepository.findByName(productoMapper.toEntity(productoDTO).getProveedor().getNombre());
            log.info("Nuevo Proveedor {}", proveedorDTO);
        }

        log.info("Se valida existencia del producto y proveedor");
        productoValidation.validate(productoDTO, proveedor.getId());
        Inventario inventario = inventarioMapper.toEntity(inventarioMapper.toDto(productoMapper.toEntity(productoDTO).getInventario()));


        Producto producto = productoMapper.toEntity(productoDTO);
        producto.setProveedor(proveedor);
        inventario.setProducto(producto);
        producto.setInventario(inventario);

        producto = productoRepository.save(producto);

        log.info("Se crea producto ");
        productoDTO = productoMapper.toDto(producto);
        productoDTO.setStatus(BussinessConstants.CREADO_CORRECTAMENTE);

        return productoDTO;
    }
} 