package unam.fes.aragon.tienda_el_zorro.infraestructure.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    @Query("SELECT f FROM Factura f JOIN f.cliente c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Factura> findAllByNombreClienteIgnoreCase(@Param("nombre") String nombre);

    @Query("SELECT f FROM Factura f JOIN f.usuario u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Factura> findAllFacturasByUsuarioNombre(@Param("nombre") String nombre);


    @Query("SELECT f FROM Factura f WHERE DATE(f.fecha) = :fecha")
    List<Factura> findByFecha(@Param("fecha") LocalDate fecha);

} 