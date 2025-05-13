package unam.fes.aragon.tienda_el_zorro.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleFactura implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;
    private Float precioUnitario;

    @ManyToOne
    @JoinColumn(name = "factura_id")
    @ToString.Exclude
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @ToString.Exclude
    private Producto producto;
}
