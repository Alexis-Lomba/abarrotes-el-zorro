package unam.fes.aragon.tienda_el_zorro.application.controller;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unam.fes.aragon.tienda_el_zorro.application.service.ExportService;
import unam.fes.aragon.tienda_el_zorro.application.service.SolicitarProductoService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProductoRequeridoDTO;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("export-service")
public class ExportController {

    private final ExportService exportService;
    private final SolicitarProductoService solicitarProductoService;


    public ExportController(ExportService exportService, SolicitarProductoService solicitarProductoService) {
        this.exportService = exportService;
        this.solicitarProductoService = solicitarProductoService;
    }

    @PostMapping("send-invoice/{id}")
    public ResponseEntity<String> enviarFactura(@PathVariable Long id){
        try {
            exportService.generarYEnviarPDF(id);
            return ResponseEntity.ok("Factura enviada correctamente");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar factura");
        }
    }

    @PostMapping("/required-products")
    public ResponseEntity<Void> enviarProductosRequeridos(@RequestBody List<ProductoRequeridoDTO> productosRequeridos) throws MessagingException, IOException {
        solicitarProductoService.enviarProductosRequeridosPorProveedor(productosRequeridos);
        return ResponseEntity.ok().build();
    }


}
