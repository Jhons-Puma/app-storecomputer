package edu.cibertec.appstorecomputer.service;

import edu.cibertec.appstorecomputer.dto.CategoriaDTO;

import java.util.List;

/**
 * Service interface for managing categories
 */
public interface CategoriaService {

    /**
     * Get all categories
     * @return List of all categories
     */
    List<CategoriaDTO> findAll();

    /**
     * Get all active categories
     * @return List of active categories
     */
    List<CategoriaDTO> findAllActive();

    /**
     * Get a category by ID
     * @param id Category ID
     * @return Category with the given ID
     */
    CategoriaDTO findById(Long id);

    /**
     * Get a category by name
     * @param nombre Category name
     * @return Category with the given name
     */
    CategoriaDTO findByNombre(String nombre);

    /**
     * Create a new category
     * @param categoriaDTO Category data
     * @return Created category
     */
    CategoriaDTO create(CategoriaDTO categoriaDTO);

    /**
     * Update an existing category
     * @param id Category ID
     * @param categoriaDTO Category data
     * @return Updated category
     */
    CategoriaDTO update(Long id, CategoriaDTO categoriaDTO);

    /**
     * Delete a category by ID
     * @param id Category ID
     */
    void delete(Long id);

    /**
     * Check if a category exists by name
     * @param nombre Category name
     * @return true if exists, false otherwise
     */
    boolean existsByNombre(String nombre);
}