package unam.fes.aragon.tienda_el_zorro.infraestructure.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    @Query(value = "select * " +
            "from proveedor pro " +
            "where pro.nombre = ?1", nativeQuery = true)
    Proveedor findByNameExcato(String name);

    @Query("SELECT p FROM Proveedor p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Proveedor> findByName(@Param("nombre") String nombre);

} 