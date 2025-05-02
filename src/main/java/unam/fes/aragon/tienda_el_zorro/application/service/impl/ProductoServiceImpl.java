package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.ProductoService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProductoDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Producto;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.ProductoMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ProductoRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private ProductoRepository productoRepository;
    private ProductoMapper productoMapper;

    @Override
    public List<ProductoDTO> findAll() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toDto)
                .toList();
    }

    @Override
    public ProductoDTO createProducto(ProductoDTO productoDTO) {
        Producto producto = productoMapper.toEntity(productoDTO);
        producto = productoRepository.save(producto);
        return productoMapper.toDto(producto);
    }
} 