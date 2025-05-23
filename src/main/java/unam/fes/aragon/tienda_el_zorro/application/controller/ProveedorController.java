package unam.fes.aragon.tienda_el_zorro.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unam.fes.aragon.tienda_el_zorro.application.service.ProveedorService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProveedorDTO;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("proveedor-service")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @PostMapping("/proveedors")
    public List<ProveedorDTO> getAll() {
        return proveedorService.findAll();
    }

    @PostMapping("/create")
    public ProveedorDTO create(@RequestBody ProveedorDTO proveedor) {
        log.info("Incia creacion de Proveedor: {}", proveedor);
        return proveedorService.createProveedor(proveedor);
    }
}
