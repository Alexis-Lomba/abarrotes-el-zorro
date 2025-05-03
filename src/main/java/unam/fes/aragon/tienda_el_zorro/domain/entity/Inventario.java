package unam.fes.aragon.tienda_el_zorro.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidadInicial;
    private Integer cantidadActual;
    private Integer minimoRequerido;

    @OneToOne
    @JoinColumn(name = "producto_id")
    @JsonBackReference
    private Producto producto;

    @Override
    public String toString() {
        return "Inventario{" +
                "id=" + id +
                ", cantidadInicial=" + cantidadInicial +
                ", cantidadActual=" + cantidadActual +
                ", minimoRequerido=" + minimoRequerido +
                '}'; // <--- No incluir producto
    }
}
