package unam.fes.aragon.tienda_el_zorro.infraestructure.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query(value = "SELECT * FROM cliente WHERE email = ?1", nativeQuery = true)
    Cliente findByEmail(String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM Cliente c WHERE c.nombre = :nombre")
    void deleteByNombre(@Param("nombre") String nombre);

    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Cliente> findByName(@Param("nombre") String nombre);

} 