package unam.fes.aragon.tienda_el_zorro.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unam.fes.aragon.tienda_el_zorro.application.service.ProductoService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProductoDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("product-service")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/products")
    public List<ProductoDTO> getAll() {
        return productoService.findAll();
    }

    @GetMapping("find-by-name/{name}")
    public List<ProductoDTO> findByName(@PathVariable String name){
        return productoService.findByName(name);
    }

    @PostMapping("/create")
    public ProductoDTO create(@RequestBody ProductoDTO request) {
        log.info("Incia creacion de Producto: {}", request);
        return productoService.createProducto(request);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        productoService.deleteProducto(id);
    }

    @PostMapping("/products/{id}/image")
    public ResponseEntity<Map<String, String>> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        productoService.uploadImage(id, file);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Imagen subida correctamente");

        return ResponseEntity.ok(response); // <-- Esto ahora devuelve JSON
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ProductoDTO> update(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        ProductoDTO actualizado = productoService.updateProducto(id, productoDTO);
        return ResponseEntity.ok(actualizado);
    }


}
