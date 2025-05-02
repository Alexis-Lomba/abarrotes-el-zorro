package unam.fes.aragon.tienda_el_zorro.infraestructure.repository;

import org.springframework.data.jpa.repository.Query;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query(value = "select cli.email " +
            "from cliente cli " +
            "where cli.email = ?1", nativeQuery = true)
    String findByEmail( String email);
} 