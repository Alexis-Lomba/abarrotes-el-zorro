package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.SolicitarProductoService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProductoRequeridoDTO;
import unam.fes.aragon.tienda_el_zorro.domain.dto.ProveedorDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Producto;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Proveedor;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.ProveedorMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ProductoRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SolicitarProductoServiceImpl implements SolicitarProductoService {

    private final ProductoRepository productoRepository;
    private final JavaMailSender mailSender;
    private final ProveedorMapper proveedorMapper;
    @Override
    public void enviarProductosRequeridosPorProveedor(List<ProductoRequeridoDTO> productosRequeridos) throws IOException, MessagingException {
        Map<Proveedor, List<ProductoRequeridoDTO>> agrupados = new HashMap<>();

        for (ProductoRequeridoDTO dto : productosRequeridos) {
            Producto producto = productoRepository.findById(dto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            Proveedor proveedor = producto.getProveedor();

            agrupados.computeIfAbsent(proveedor, k -> new ArrayList<>()).add(dto);
        }

        for (Map.Entry<Proveedor, List<ProductoRequeridoDTO>> entry : agrupados.entrySet()) {
            Proveedor proveedor = entry.getKey();
            ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);
            List<ProductoRequeridoDTO> productos = entry.getValue();
            ByteArrayInputStream excel = generarExcelProductosRequeridos(proveedorDTO, productos);

            enviarCorreoProveedor(proveedor.getCorreo(), excel, "Productos_Requeridos.xlsx");
        }
    }



    @Override
    public ByteArrayInputStream generarExcelProductosRequeridos(ProveedorDTO proveedorDTO, List<ProductoRequeridoDTO> productosRequeridos) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Productos Requeridos");

            // Estilo en negritas
            CellStyle boldStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            boldStyle.setFont(font);

            // Información del proveedor
            Row row0 = sheet.createRow(0);
            row0.createCell(0).setCellValue("Nombre del Proveedor:");
            row0.getCell(0).setCellStyle(boldStyle);
            row0.createCell(1).setCellValue(proveedorDTO.getNombre());

            Row row1 = sheet.createRow(1);
            row1.createCell(0).setCellValue("Correo del Proveedor:");
            row1.getCell(0).setCellStyle(boldStyle);
            row1.createCell(1).setCellValue(proveedorDTO.getCorreo());

            sheet.createRow(2); // fila vacía

            // Encabezado tabla
            Row header = sheet.createRow(3);
            header.createCell(0).setCellValue("Nombre del Producto");
            header.getCell(0).setCellStyle(boldStyle);
            header.createCell(1).setCellValue("Cantidad Requerida");
            header.getCell(1).setCellStyle(boldStyle);

            int rowNum = 4;
            for (ProductoRequeridoDTO dto : productosRequeridos) {
                Producto producto = productoRepository.findById(dto.getProductoId()).orElseThrow();
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(producto.getNombre());
                row.createCell(1).setCellValue(dto.getCantidadRequerida());
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }



    @Override
    public void enviarCorreoProveedor(String correoDestino, ByteArrayInputStream excel, String nombreArchivo) throws MessagingException, IOException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

        helper.setTo(correoDestino);
        helper.setSubject("Productos Requeridos");
        helper.setText("Adjunto encontrarás la lista de productos requeridos con su respectiva cantidad");

        DataSource source = new ByteArrayDataSource(excel, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        helper.addAttachment(nombreArchivo, source);

        mailSender.send(mensaje);
    }
}
