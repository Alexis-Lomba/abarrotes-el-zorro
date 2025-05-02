package unam.fes.aragon.tienda_el_zorro.infraestructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
