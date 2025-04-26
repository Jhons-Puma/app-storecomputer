package edu.cibertec.appstorecomputer.repository;

import edu.cibertec.appstorecomputer.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    
    Optional<Marca> findByNombre(String nombre);
    
    List<Marca> findByActivoTrue();
    
    List<Marca> findByPaisOrigen(String paisOrigen);
    
    boolean existsByNombre(String nombre);
}