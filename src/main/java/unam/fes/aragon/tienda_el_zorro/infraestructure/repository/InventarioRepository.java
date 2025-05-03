package unam.fes.aragon.tienda_el_zorro.infraestructure.repository;

import org.springframework.data.jpa.repository.Query;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    @Query(value = "select * " +
            "from inventario pro " +
            "where pro.producto_id = ?1", nativeQuery = true)
    Inventario findByProductoId(Long id);
} 