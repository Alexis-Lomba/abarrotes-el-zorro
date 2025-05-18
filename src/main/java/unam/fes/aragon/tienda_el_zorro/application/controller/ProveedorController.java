package unam.fes.aragon.tienda_el_zorro.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unam.fes.aragon.tienda_el_zorro.application.service.ProveedorService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProveedorDTO;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("supplier-service")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping("/suppliers")
    public List<ProveedorDTO> getAll() {
        return proveedorService.findAll();
    }

    @GetMapping("find-by-name/{name}")
    public List<ProveedorDTO> findByName(@PathVariable String name){
        return proveedorService.findByName(name);
    }

    @GetMapping("find-by-id/{id}")
    public ProveedorDTO findById(@PathVariable Long id){
        return proveedorService.findById(id);
    }

    @PostMapping("/create")
    public ProveedorDTO create(@RequestBody ProveedorDTO proveedor) {
        log.info("Incia creacion de Proveedor: {}", proveedor);
        return proveedorService.createProveedor(proveedor);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        proveedorService.deleteProveedor(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProveedorDTO> update(@PathVariable Long id, @RequestBody ProveedorDTO proveedorDTO) {
        ProveedorDTO actualizado = proveedorService.updateProveedor(id, proveedorDTO);
        return ResponseEntity.ok(actualizado);
    }

}
