package unam.fes.aragon.tienda_el_zorro.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @DeleteMapping("/delete")
    public void delete(@RequestParam Long id){
        productoService.deleteProducto(id);
    }

    @PostMapping("/productos/{id}/imagen")
    public ResponseEntity<String> subirImagen(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        productoService.uploadImage(id, file);
        return ResponseEntity.ok("Imagen subida correctamente");
    }

}
