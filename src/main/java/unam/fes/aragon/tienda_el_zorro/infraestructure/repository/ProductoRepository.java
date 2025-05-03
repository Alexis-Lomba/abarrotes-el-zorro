package unam.fes.aragon.tienda_el_zorro.infraestructure.repository;

import org.springframework.data.jpa.repository.Query;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    @Query(value = "select * " +
            "from producto pro " +
            "where pro.nombre = ?1", nativeQuery = true)
    Producto findByName(String name);


    @Query(value = "select * " +
            "from producto pro " +
            "where pro.nombre = ?1 and pro.proveedor_id = ?2", nativeQuery = true)
    Producto findByNameAndProveedorId(String name, Long id);

    @Query(value = "select * " +
            "from producto pro ", nativeQuery = true)
    List<Producto> findAllProductos();
} 