package unam.fes.aragon.tienda_el_zorro.application.controller;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unam.fes.aragon.tienda_el_zorro.application.service.CorteCajaService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.CorteCajaDTO;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("audit-service")
public class CorteCajaController {

    private final CorteCajaService corteCajaService;

    public CorteCajaController(CorteCajaService corteCajaService) {
        this.corteCajaService = corteCajaService;
    }

    @GetMapping("/date/{fecha}")
    public ResponseEntity<List<CorteCajaDTO>> getCorteCajaPorFecha(@PathVariable("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<CorteCajaDTO> resultado = corteCajaService.obtenerCorteDeCajaPorFecha(fecha);
        return ResponseEntity.ok(resultado);
    }
}
