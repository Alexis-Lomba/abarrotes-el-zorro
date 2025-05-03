package unam.fes.aragon.tienda_el_zorro.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unam.fes.aragon.tienda_el_zorro.application.service.ProductoService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProductoDTO;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("product-service")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/products")
    public List<ProductoDTO> getAll() {
        return productoService.findAll();
    }

    @PostMapping("/create")
    public ProductoDTO create(@RequestBody ProductoDTO request) {
        log.info("Incia creacion de Producto: {}", request);
        return productoService.createProducto(request);
    }
}
