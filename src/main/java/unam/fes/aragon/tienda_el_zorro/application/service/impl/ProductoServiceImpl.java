package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import unam.fes.aragon.tienda_el_zorro.application.service.FindIdService;
import unam.fes.aragon.tienda_el_zorro.application.service.ProductoService;
import unam.fes.aragon.tienda_el_zorro.application.service.ProveedorService;
import unam.fes.aragon.tienda_el_zorro.domain.constants.BussinessConstants;
import unam.fes.aragon.tienda_el_zorro.domain.dto.InventarioDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProductoDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProveedorDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Inventario;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Producto;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Proveedor;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.InventarioMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.ProductoMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.ProveedorMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.InventarioRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ProductoRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ProveedorRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.validations.ProductoValidation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final InventarioRepository inventarioRepository;
    private ProductoRepository productoRepository;
    private final ProveedorMapper proveedorMapper;
    private final ProveedorService proveedorService;
    private final ProductoMapper productoMapper;
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
        log.info("Inicia creación de producto");

        Proveedor proveedor = findIdService.findIdProveedor(productoDTO.getProveedorId());

        Producto producto = productoMapper.toEntity(productoDTO);
        producto.setProveedor(proveedor);
        producto.setInventario(null);
        producto = productoRepository.save(producto);

        Inventario inventario = inventarioMapper.toEntity(productoDTO.getInventarioDTO());
        inventario.setProducto(producto);
        inventario = inventarioRepository.save(inventario);

        producto.setInventario(inventario);
        producto = productoRepository.save(producto);

        ProductoDTO resultado = productoMapper.toDto(producto);
        resultado.setStatus("CREADO_CORRECTAMENTE");
        return resultado;
    }

    @Override
    public void deleteProducto(Long id) {
        findIdService.findIdProducto(id);
        productoRepository.deleteById(id);
    }

    @Override
    public void uploadImage(Long productoId, MultipartFile file) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        try {
            String nombreArchivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path ruta = Paths.get("uploads/productos/" + nombreArchivo); //actualizar a la carpeta donde guardaremos las imagenes
            Files.createDirectories(ruta.getParent());
            Files.copy(file.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
            producto.setImagenUrl(nombreArchivo);
            productoRepository.save(producto);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }

    @Override
    @Transactional
    public ProductoDTO updateProducto(Long id, ProductoDTO dto) {
        Producto producto = findIdService.findIdProducto(id);

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setImagenUrl(dto.getImagenUrl());

        if (dto.getProveedorId() != null) {
            Proveedor proveedor = findIdService.findIdProveedor(dto.getProveedorId());
            producto.setProveedor(proveedor);
        }

        if (dto.getInventarioDTO() != null) {
            Inventario inventario = producto.getInventario();
            InventarioDTO invDto = dto.getInventarioDTO();
            inventario.setCantidadActual(invDto.getCantidadActual());
            inventario.setCantidadInicial(invDto.getCantidadInicial());
            inventario.setMinimoRequerido(invDto.getMinimoRequerido());
            inventarioRepository.save(inventario);
        }

        producto = productoRepository.save(producto);
        return productoMapper.toDto(producto);
    }

} 