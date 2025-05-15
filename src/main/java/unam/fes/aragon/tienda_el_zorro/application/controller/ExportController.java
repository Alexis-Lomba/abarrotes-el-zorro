package unam.fes.aragon.tienda_el_zorro.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unam.fes.aragon.tienda_el_zorro.application.service.ExportService;

@Slf4j
@RestController
@RequestMapping("export-service")
public class ExportController {

    private final ExportService exportService;


    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @PostMapping("enviar-factura/{id}")
    public ResponseEntity<String> enviarFactura(@PathVariable Long id){
        try {
            exportService.generarYEnviarPDF(id);
            return ResponseEntity.ok("Factura enviada correctamente");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar factura");
        }
    }

}
