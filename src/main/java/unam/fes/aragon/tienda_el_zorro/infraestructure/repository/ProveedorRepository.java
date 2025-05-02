package unam.fes.aragon.tienda_el_zorro.infraestructure.repository;

import org.springframework.data.jpa.repository.Query;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    @Query(value = "select pro.nombre " +
            "from proveedor pro " +
            "where pro.nombre = ?1", nativeQuery = true)
    String findByName( String name);
} 