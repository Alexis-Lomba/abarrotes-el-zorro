package unam.fes.aragon.tienda_el_zorro.infraestructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
}
