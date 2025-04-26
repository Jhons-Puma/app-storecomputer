package edu.cibertec.appstorecomputer.repository;

import edu.cibertec.appstorecomputer.model.Categoria;
import edu.cibertec.appstorecomputer.model.Marca;
import edu.cibertec.appstorecomputer.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    /*
    * ESTE ES EL CODIGO DE JORGITO EL EUNUCO
    * */

    Optional<Producto> findByCodigo(String codigo);
    
    List<Producto> findByActivoTrue();
    
    List<Producto> findByCategoria(Categoria categoria);
    
    List<Producto> findByMarca(Marca marca);
    
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    List<Producto> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);
    
    List<Producto> findByStockLessThan(Integer stockMinimo);
    
    @Query("SELECT p FROM Producto p WHERE p.activo = true AND p.stock > 0")
    List<Producto> findAvailableProducts();
    
    @Query("SELECT p FROM Producto p WHERE p.categoria.id = :categoriaId AND p.marca.id = :marcaId")
    List<Producto> findByCategoriaAndMarca(@Param("categoriaId") Long categoriaId, @Param("marcaId") Long marcaId);
    
    Page<Producto> findByActivoTrue(Pageable pageable);
    
    boolean existsByCodigo(String codigo);
}