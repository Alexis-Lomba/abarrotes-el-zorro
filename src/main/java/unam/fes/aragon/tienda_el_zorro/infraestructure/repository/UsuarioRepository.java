package unam.fes.aragon.tienda_el_zorro.infraestructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByNombre(String nombre);

    @Query(value = "select * from usuario where username = ?1", nativeQuery = true)
    Usuario findByUsername(String username);
}
