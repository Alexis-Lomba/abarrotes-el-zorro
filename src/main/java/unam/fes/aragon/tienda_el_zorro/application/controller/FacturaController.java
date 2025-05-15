package unam.fes.aragon.tienda_el_zorro.application.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import unam.fes.aragon.tienda_el_zorro.application.service.FacturaService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ClienteDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.FacturaDTO;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("invoice-service")
@AllArgsConstructor
public class FacturaController {

    private final FacturaService facturaService;

    @PostMapping("/create")
    public FacturaDTO create(@RequestBody FacturaDTO request) {
        log.info("Incia creacion de Factura: {}", request);
        return facturaService.createFactura(request);
    }

    @GetMapping("/client-invoices")
    public List<FacturaDTO> getAllByClient(@RequestParam String nombre) {
        log.info("Facturas del cliente: {}", nombre);
        return facturaService.findAllByClientNombre(nombre);
    }

    @PostMapping("/products")
    public List<FacturaDTO> getAllByDay(@RequestBody FacturaDTO facturaDTO) {
        return null;
    }

    @GetMapping("/user-invoices")
    public List<FacturaDTO> getAllByUsuario(@RequestParam String nombre){
        return facturaService.findAllByUsuarioNombre(nombre);
    }

}
