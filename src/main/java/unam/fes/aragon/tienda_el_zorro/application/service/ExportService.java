package unam.fes.aragon.tienda_el_zorro.application.service;

import unam.fes.aragon.tienda_el_zorro.domain.dto.FacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProductoDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ExportService {

    void sendFacturaFilesToCliente(String email, byte[] pdf, byte[] excel);

    void exportarYEnviarFacturasCliente(Long clienteId);

    ByteArrayInputStream createPDF(List<FacturaDTO> facturas);

    ByteArrayInputStream createExcel(List<ProductoDTO> productos);


}
