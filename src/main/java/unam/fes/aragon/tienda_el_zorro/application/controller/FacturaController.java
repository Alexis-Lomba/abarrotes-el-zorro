package unam.fes.aragon.tienda_el_zorro.application.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/client/")
    public List<FacturaDTO> getAllByClient(@RequestBody ClienteDTO cliente) {
        log.info("Facturas del usuario: {}", cliente);
        return null;
    }

    @PostMapping("/products")
    public List<FacturaDTO> getAllByDay(@RequestBody FacturaDTO facturaDTO) {
        return null;
    }
}
