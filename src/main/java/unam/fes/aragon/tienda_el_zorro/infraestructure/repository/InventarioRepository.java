package unam.fes.aragon.tienda_el_zorro.infraestructure.repository;

import unam.fes.aragon.tienda_el_zorro.domain.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
} 