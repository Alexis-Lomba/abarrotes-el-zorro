package unam.fes.aragon.tienda_el_zorro.application.service;

import jakarta.mail.MessagingException;
import unam.fes.aragon.tienda_el_zorro.domain.dto.FacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProductoDTO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface ExportService {

    void generarYEnviarPDF(Long facturaId) throws IOException, MessagingException;

    ByteArrayInputStream generarFacturaPDF(FacturaDTO factura) throws IOException;

}
