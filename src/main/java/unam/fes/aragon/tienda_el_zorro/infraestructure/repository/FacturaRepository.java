package unam.fes.aragon.tienda_el_zorro.infraestructure.repository;

import unam.fes.aragon.tienda_el_zorro.domain.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
} 