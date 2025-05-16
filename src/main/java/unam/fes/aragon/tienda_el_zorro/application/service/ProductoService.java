package unam.fes.aragon.tienda_el_zorro.application.service;

import org.springframework.web.multipart.MultipartFile;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProductoDTO;

import java.util.List;

public interface ProductoService {
    List<ProductoDTO> findAll();
    
    ProductoDTO createProducto(ProductoDTO productoDTO);

    void deleteProducto(Long id);

    void uploadImage(Long prodcutoId, MultipartFile file);

    ProductoDTO updateProducto(Long id, ProductoDTO productoDTO);

    List<ProductoDTO> findByName(String nombre);

} 