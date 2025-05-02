package unam.fes.aragon.tienda_el_zorro.application.service;

import unam.fes.aragon.tienda_el_zorro.domain.dto.ProductoDTO;

import java.util.List;

public interface ProductoService {
    List<ProductoDTO> findAll();
    
    ProductoDTO createProducto(ProductoDTO productoDTO);
} 