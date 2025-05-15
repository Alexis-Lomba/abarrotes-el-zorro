package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.ExportService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.FacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Cliente;
import unam.fes.aragon.tienda_el_zorro.domain.entity.DetalleFactura;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Factura;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Producto;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.FacturaMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ClienteRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.FacturaRepository;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.ProductoRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
@AllArgsConstructor
public class ExportServiceImpl implements ExportService {

    private final FacturaMapper facturaMapper;
    private final JavaMailSender mailSender;
    private final FacturaRepository facturaRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public void generarYEnviarPDF(Long facturaId) throws IOException, MessagingException {
        Factura factura = facturaRepository.findById(facturaId).orElseThrow();
        FacturaDTO facturaDTO = facturaMapper.toDto(factura);
        ByteArrayInputStream pdf = generarFacturaPDF(facturaDTO);
        String nombreArchivo = factura.getCliente().getNombre() + "_" + factura.getFecha().getMonth() + "_Factura";
        enviarFacturaPorCorreo(factura.getCliente().getEmail(), pdf, nombreArchivo);
    }

    @Override
    public ByteArrayInputStream generarFacturaPDF(FacturaDTO facturaDTO) throws IOException {
        Factura factura = facturaMapper.toEntity(facturaDTO);
        Cliente cliente = clienteRepository.findById(facturaDTO.getClienteId()).orElseThrow();
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        LocalDate fecha = factura.getFecha().toLocalDate();
        String fechaFormateada = fecha.getDayOfMonth() + "/" +
                fecha.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES")) + "/" +
                fecha.getYear();

        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Factura #" + factura.getId(), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(10);
        document.add(title);

        document.add(new Paragraph("Cliente: " + cliente.getNombre() + " " + cliente.getApellido()));
        document.add(new Paragraph("Fecha: " + fechaFormateada));

        document.add(new Paragraph("\nDetalles:"));
        for (DetalleFactura detalle : factura.getDetalles()) {
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            String line = String.format("• %-25s  x%-2d    $%.2f", producto.getNombre(), detalle.getCantidad(), detalle.getPrecioUnitario());
            document.add(new Paragraph(line));
        }

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        Paragraph total = new Paragraph("Total: $" + String.format("%.2f", factura.getTotal()), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(total);


        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    public void enviarFacturaPorCorreo(String correoDestino, ByteArrayInputStream pdf, String nombreArchivo) throws MessagingException, IOException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

        helper.setTo(correoDestino);
        helper.setSubject("Tu factura");
        helper.setText("Adjunto encontrarás tu factura.");

        DataSource source = new ByteArrayDataSource(pdf, "application/pdf");
        helper.addAttachment(nombreArchivo + ".pdf", source);

        mailSender.send(mensaje);
    }

}
