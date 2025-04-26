package edu.cibertec.appstorecomputer.service;

import edu.cibertec.appstorecomputer.dto.ProductoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for managing products
 */
public interface ProductoService {

    /**
     * Get all products
     * @return List of all products
     */
    List<ProductoDTO> findAll();

    /**
     * Get all products with pagination
     * @param pageable Pagination information
     * @return Page of products
     */
    Page<ProductoDTO> findAllPaginated(Pageable pageable);

    /**
     * Get all active products
     * @return List of active products
     */
    List<ProductoDTO> findAllActive();

    /**
     * Get all active products with pagination
     * @param pageable Pagination information
     * @return Page of active products
     */
    Page<ProductoDTO> findAllActivePaginated(Pageable pageable);

    /**
     * Get a product by ID
     * @param id Product ID
     * @return Product with the given ID
     */
    ProductoDTO findById(Long id);

    /**
     * Get a product by code
     * @param codigo Product code
     * @return Product with the given code
     */
    ProductoDTO findByCodigo(String codigo);

    /**
     * Get products by category ID
     * @param categoriaId Category ID
     * @return List of products in the given category
     */
    List<ProductoDTO> findByCategoria(Long categoriaId);

    /**
     * Get products by brand ID
     * @param marcaId Brand ID
     * @return List of products of the given brand
     */
    List<ProductoDTO> findByMarca(Long marcaId);

    /**
     * Get products by category and brand
     * @param categoriaId Category ID
     * @param marcaId Brand ID
     * @return List of products in the given category and brand
     */
    List<ProductoDTO> findByCategoriaAndMarca(Long categoriaId, Long marcaId);

    /**
     * Get products by name (partial match, case-insensitive)
     * @param nombre Product name
     * @return List of products matching the given name
     */
    List<ProductoDTO> findByNombreContaining(String nombre);

    /**
     * Get products within a price range
     * @param precioMin Minimum price
     * @param precioMax Maximum price
     * @return List of products within the given price range
     */
    List<ProductoDTO> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);

    /**
     * Get products with stock below a threshold
     * @param stockMinimo Minimum stock threshold
     * @return List of products with stock below the threshold
     */
    List<ProductoDTO> findByStockLessThan(Integer stockMinimo);

    /**
     * Get available products (active and in stock)
     * @return List of available products
     */
    List<ProductoDTO> findAvailableProducts();

    /**
     * Create a new product
     * @param productoDTO Product data
     * @return Created product
     */
    ProductoDTO create(ProductoDTO productoDTO);

    /**
     * Update an existing product
     * @param id Product ID
     * @param productoDTO Product data
     * @return Updated product
     */
    ProductoDTO update(Long id, ProductoDTO productoDTO);

    /**
     * Delete a product by ID
     * @param id Product ID
     */
    void delete(Long id);

    /**
     * Check if a product exists by code
     * @param codigo Product code
     * @return true if exists, false otherwise
     */
    boolean existsByCodigo(String codigo);
}