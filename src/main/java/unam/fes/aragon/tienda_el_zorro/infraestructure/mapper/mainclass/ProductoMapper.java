package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.dto.InventarioDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProductoDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProveedorDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Inventario;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Producto;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Proveedor;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.InventarioRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ProveedorRepository;

@Slf4j
@Service
@AllArgsConstructor
public class ProductoMapper {
    private final InventarioMapper inventarioMapper;
    private final InventarioRepository inventarioRepository;
    private final ProveedorMapper proveedorMapper;
    private final ProveedorRepository proveedorRepository;

    public Producto toEntity(ProductoDTO productoDTO) {
        return Producto.builder()
                .nombre(productoDTO.getNombre())
                .descripcion(productoDTO.getDescripcion())
                .precio(productoDTO.getPrecio())
                .imagenUrl(productoDTO.getImagenUrl())
                .inventario(inventarioMapper.toEntity(productoDTO.getInventario()))
                .build();
    }

    public ProductoDTO toDTO(Producto producto) {
        Inventario inventario = inventarioRepository.findByProductoId(producto.getId());
        InventarioDTO inventarioDTO = inventarioMapper.toDto(inventario);

        Proveedor proveedor = proveedorRepository.findByName(producto.getProveedor().getNombre());
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        return ProductoDTO.builder()
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .imagenUrl(producto.getImagenUrl())
                .proveedor(proveedorDTO)
                .inventario(inventarioDTO)
                .build();
    }
}
