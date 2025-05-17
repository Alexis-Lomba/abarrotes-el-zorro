package unam.fes.aragon.tienda_el_zorro.application.service;

import jakarta.mail.MessagingException;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProductoRequeridoDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProveedorDTO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface SolicitarProductoService {

    void enviarProductosRequeridosPorProveedor(List<ProductoRequeridoDTO> productosRequeridos) throws IOException, MessagingException;

    ByteArrayInputStream generarExcelProductosRequeridos(ProveedorDTO proveedorDTO, List<ProductoRequeridoDTO> productosRequeridos) throws IOException;

    void enviarCorreoProveedor(String correoDestino, ByteArrayInputStream excel, String nombreArchivo) throws MessagingException, IOException;
}
