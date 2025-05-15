package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import unam.fes.aragon.tienda_el_zorro.application.service.ExportService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.FacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProductoDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

public class ExportServiceImpl implements ExportService {
    @Override
    public void sendFacturaFilesToCliente(String email, byte[] pdf, byte[] excel) {

    }

    @Override
    public void exportarYEnviarFacturasCliente(Long clienteId) {

    }

    @Override
    public ByteArrayInputStream createPDF(List<FacturaDTO> facturas) {
        return null;
    }

    @Override
    public ByteArrayInputStream createExcel(List<ProductoDTO> productos) {
        return null;
    }
}
