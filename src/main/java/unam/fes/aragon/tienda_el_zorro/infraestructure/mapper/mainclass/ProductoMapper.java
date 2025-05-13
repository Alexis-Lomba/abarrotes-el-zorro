package unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProductoDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Inventario;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Producto;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Proveedor;

@AllArgsConstructor
@Service
public class ProductoMapper {

     private  final InventarioMapper inventarioMapper;
    // Convertir Entidad a DTO
    public ProductoDTO toDto(Producto producto) {
        if (producto == null) return null;

        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .imagenUrl(producto.getImagenUrl())
                .proveedorId(producto.getProveedor() != null
                        ? producto.getProveedor().getId()
                        : null)
                .inventarioDTO(inventarioMapper.toDto(producto.getInventario()))
                .build();
    }

    public Producto toEntity(ProductoDTO dto) {
        if (dto == null) return null;

        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setImagenUrl(dto.getImagenUrl());

        if (dto.getProveedorId() != null) {
            Proveedor proveedor = new Proveedor();
            proveedor.setId(dto.getProveedorId());
            producto.setProveedor(proveedor);
        }

        // Mapear Inventario (solo ID)
        if (dto.getInventarioDTO() != null) {
            Inventario inventario = new Inventario();
            inventario.setId(dto.getInventarioDTO().getId());
            producto.setInventario(inventario);
        }

        return producto;
    }
}
