package unam.fes.aragon.tienda_el_zorro.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.UsuarioRepository;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
 @Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Factura implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    private Float total;

    private Boolean activa;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<DetalleFactura> detalles;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


// Getters y Setters

}
